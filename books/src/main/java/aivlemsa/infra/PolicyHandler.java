package aivlemsa.infra;

import aivlemsa.config.kafka.KafkaProcessor;
import aivlemsa.domain.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
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
    BookRepository bookRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='GenerationSucceeded'"
    )
    public void wheneverGenerationSucceeded_AddBook(
        @Payload GenerationSucceeded generationSucceeded
    ) {
        GenerationSucceeded event = generationSucceeded;
        System.out.println(
            "\n\n##### listener AddBook : " +
            generationSucceeded +
            "\n\n"
        );

        // Sample Logic //
        Book.addBook(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
