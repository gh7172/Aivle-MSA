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

@Component
@Transactional
public class GenerateCommandHandler implements ApplicationListener<RequestBookPublicationCommand> {

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

    @Override
    public void onApplicationEvent(RequestBookPublicationCommand cmd) {
        log.info("Handling RequestBookPublicationCommand for bookId={}", cmd.getBookId());

        Generate gen = generateRepository.findById(cmd.getBookId())
            .orElseThrow(() -> new IllegalStateException("Generate not found: " + cmd.getBookId()));

        try {
            String generated = openAIService.generateBook(cmd.getSummary());
            gen.markSucceeded(generated);
            publisher.publishEvent(new GenerationSucceeded(gen));
            log.info("Published GenerationSucceeded for bookId={}", cmd.getBookId());
        } catch (Exception ex) {
            gen.markFailed(ex.getMessage());
            publisher.publishEvent(new GenerationFailed(gen));
            log.error("Published GenerationFailed for bookId={}: {}", cmd.getBookId(), ex.getMessage());
        }

        generateRepository.save(gen);
    }
}
