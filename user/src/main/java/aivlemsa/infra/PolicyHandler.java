package aivlemsa.infra;

import aivlemsa.config.kafka.KafkaProcessor;
import aivlemsa.domain.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
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
    UserRepository userRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='AuthorRegistrationApproved'"
    )
    public void wheneverAuthorRegistrationApproved_UpdateState(
        @Payload AuthorRegistrationApproved authorRegistrationApproved
    ) {
        AuthorRegistrationApproved event = authorRegistrationApproved;
        System.out.println(
            "\n\n##### listener UpdateState : " +
            authorRegistrationApproved +
            "\n\n"
        );

        // Sample Logic //
        User.updateState(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
