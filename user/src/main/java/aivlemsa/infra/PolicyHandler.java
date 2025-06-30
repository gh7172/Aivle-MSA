package aivlemsa.infra;

import aivlemsa.config.kafka.KafkaProcessor;
import aivlemsa.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PolicyHandler {

    @Autowired
    UserRepository userRepository;

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='AuthorRegistrationApproved'"
    )
    public void wheneverAuthorRegistrationApproved_UpdateState(
        AuthorRegistrationApproved event
    ) {
        System.out.println("##### listener UpdateState : " + event);

        // 실제로 isAuthor를 true로 변경
        if (event.getId() != null) {
            User user = userRepository.findById(event.getId()).orElse(null);
            if (user != null) {
                user.setIsAuthor(true);
                userRepository.save(user);
            }
        }
    }
}
