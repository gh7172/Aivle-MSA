package aivlemsa.infra;

import aivlemsa.config.kafka.KafkaProcessor;
import aivlemsa.domain.*;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    GenerateRepository generateRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='BookPublicationRequested'"
    )
    public void wheneverBookPublicationRequested_HandleBookPublicationRequested(
        @Payload BookPublicationRequested bookPublicationRequested
    ) {
        BookPublicationRequested event = bookPublicationRequested;
        System.out.println(
            "\n\n##### listener HandleBookPublicationRequested : " +
            bookPublicationRequested +
            "\n\n"
        );

        // Sample Logic //

        RequestBookPublicationCommand command = new RequestBookPublicationCommand();
        command.setBookId(event.getBookId());
        Generate.requestBookPublication(command);
    }
}
//>>> Clean Arch / Inbound Adaptor
