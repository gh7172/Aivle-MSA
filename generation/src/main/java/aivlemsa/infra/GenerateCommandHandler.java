package aivlemsa.infra;

import aivlemsa.domain.Generate;
import aivlemsa.domain.GenerateRepository;
import aivlemsa.domain.RequestBookPublicationCommand;
import aivlemsa.domain.GenerationSucceeded;
import aivlemsa.domain.GenerationFailed;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import aivlemsa.domain.*;
import aivlemsa.config.kafka.KafkaProcessor;
import org.springframework.messaging.Message;

@Component
@Transactional
public class GenerateCommandHandler {

    private static final Logger log = LoggerFactory.getLogger(GenerateCommandHandler.class);

    private final GenerateRepository generateRepository;
    private final OpenAIService openAIService;
    private final ApplicationEventPublisher publisher;

    public GenerateCommandHandler(
        GenerateRepository generateRepository,
        OpenAIService openAIService,
        ApplicationEventPublisher publisher
    ) {
        this.generateRepository = generateRepository;
        this.openAIService = openAIService;
        this.publisher = publisher;
    }

    @Autowired
    @Qualifier(KafkaProcessor.OUTPUT)
    private MessageChannel outboundTopic;

    public void handle(RequestBookPublicationCommand cmd) {
        log.info("Handling RequestBookPublicationCommand for bookId={}", cmd.getBookId());

        Generate generate = generateRepository.findById(cmd.getBookId())
            .orElseThrow(() -> new IllegalStateException("Generate not found: " + cmd.getBookId()));

        try {
            // 도서 내용 요약
            String aiSummary = openAIService.generateSummary(cmd.getSummary());
            generate.setSummary(aiSummary);

            // 도서 표지 생성
            String coverUrl = openAIService.generateCover(cmd.getTitle(), aiSummary);
            generate.setImageUrl(coverUrl);

            generate.markSucceeded(aiSummary);
            GenerationSucceeded successEvt = new GenerationSucceeded(generate);
            successEvt.setSummary(aiSummary);
            successEvt.setImageUrl(coverUrl);
            publisher.publishEvent(successEvt);
            
            Message<GenerationSucceeded> message = MessageBuilder
                .withPayload(successEvt)
                .setHeader("contentType", "application/json")
                .build();

            outboundTopic.send(message);

            log.info("Published GenerationSucceeded for bookId={}", cmd.getBookId());

        } catch (Exception e) {
            String error = e.getMessage();
            generate.markFailed(error);
            GenerationFailed failEvt = new GenerationFailed(generate);
            failEvt.setState(error);
            publisher.publishEvent(failEvt);
            log.error("Published GenerationFailed for bookId={}: {}", cmd.getBookId(), error, e);
        }

        generateRepository.save(generate);
    }
}
