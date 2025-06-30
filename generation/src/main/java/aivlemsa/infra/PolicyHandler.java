package aivlemsa.infra;

import aivlemsa.config.kafka.KafkaProcessor;
import aivlemsa.domain.*;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationEventPublisher;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {
    private static final Logger log = LoggerFactory.getLogger(PolicyHandler.class);

    @Autowired
    GenerateRepository generateRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowiredprivate
    GenerateCommandHandler generateCommandHandler;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='BookPublicationRequested'"
    )
    public void onBookPublicationRequested(
        @Payload BookPublicationRequested event
    ) {
        log.info("Received BookPublicationRequested 이벤트 수신신 : {}", event);
        
        // 1) Generate Aggregate 생성 및 저장
        Generate generate = new Generate(
            event.getBookId(), 
            event.getSummary(), 
            event.getTitle());
        generateRepository.save(generate);

        // 2) 내부 Command 생성 및 발행
        RequestBookPublicationCommand command = new RequestBookPublicationCommand();
        command.setBookId(event.getBookId());
        command.setSummary(event.getSummary());
        command.setTitle(event.getTitle());
        generateCommandHandler.handle(command);
        publisher.publishEvent(command);
        //Generate.requestBookPublication(command);
        
        log.info("Dispatched RequestBookPublicationCommand for bookId={}", event.getBookId());
    }
}
//>>> Clean Arch / Inbound Adaptor
