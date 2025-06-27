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
    WriteRepository writeRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
            value = KafkaProcessor.INPUT,
            condition = "headers['type']=='BookAdded'"
    )
    public void wheneverBookAdded_StateUpdate(@Payload BookAdded bookAdded) {
        BookAdded event = bookAdded;
        System.out.println(
                "\n\n##### listener StateUpdate : " + bookAdded + "\n\n"
        );

        // Sample Logic //
        Write.stateUpdate(event);
    }

    @StreamListener(
            value = KafkaProcessor.INPUT,
            condition = "headers['type']=='GenerationFailed'"
    )
    public void wheneverGenerationFailed_StateUpdate(
            @Payload GenerationFailed generationFailed
    ) {
        GenerationFailed event = generationFailed;
        System.out.println(
                "\n\n##### listener StateUpdate : " + generationFailed + "\n\n"
        );

        // Sample Logic //
        Write.stateUpdate(event);
    }

    @StreamListener(
            value = KafkaProcessor.INPUT,
            condition = "headers['type']=='GenerationSucceeded'"
    )
    public void wheneverGenerationSucceeded_StateUpdate(
            @Payload GenerationSucceeded generationSucceeded
    ) {
        GenerationSucceeded event = generationSucceeded;
        System.out.println(
                "\n\n##### listener StateUpdate : " + generationSucceeded + "\n\n"
        );

        // Sample Logic //
        Write.stateUpdate(event);
    }

    @StreamListener(
            value = KafkaProcessor.INPUT,
            condition = "headers['type']=='BookAddFailed'"
    )
    public void wheneverBookAddFailed_StateUpdate(
            @Payload BookAddFailed bookAddFailed
    ) {
        BookAddFailed event = bookAddFailed;
        System.out.println(
                "\n\n##### listener StateUpdate : " + bookAddFailed + "\n\n"
        );

        // Sample Logic //
        Write.stateUpdate(event);
    }
}