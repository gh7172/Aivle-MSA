package aivlemsa.infra;

import aivlemsa.config.kafka.KafkaProcessor;
import aivlemsa.domain.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
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
    AuthorRepository authorRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {
        System.out.println("##### received message : " + eventString);

        Map<String, Object> eventMap;
        try {
            eventMap = objectMapper.readValue(eventString, Map.class);
        } catch (Exception e) {
            // JSON 파싱 실패 시 로그만 남기고 종료
            e.printStackTrace();
            return;
        }

        String eventType = (String) eventMap.get("eventType");

        if ("AuthorRegistrationApproved".equals(eventType)) {
            try {
                AuthorRegistrationApproved event = objectMapper.readValue(
                    eventString,
                    AuthorRegistrationApproved.class
                );
                System.out.println(
                    "##### listener AuthorRegistrationApproved : " + event
                );
                // 여기에 기존 wheneverAuthorRegistrationApproved 로직 구현
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("AuthorRegistrationRejected".equals(eventType)) {
            try {
                AuthorRegistrationRejected event = objectMapper.readValue(
                    eventString,
                    AuthorRegistrationRejected.class
                );
                System.out.println(
                    "##### listener AuthorRegistrationRejected : " + event
                );
                // 여기에 기존 wheneverAuthorRegistrationRejected 로직 구현
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /*  기존 리스너들은 이제 사용하지 않으므로 주석 처리합니다.
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='AuthorRegistrationApproved'"
    )
    public void wheneverAuthorRegistrationApproved(
        @Payload AuthorRegistrationApproved event
    ) {
        // 승인 이벤트 수신 시 처리 로직 (예시)
        System.out.println(
            "##### listener AuthorRegistrationApproved : " + event
        );
        // 필요시 authorRepository 등 활용 가능
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='AuthorRegistrationRejected'"
    )
    public void wheneverAuthorRegistrationRejected(
        @Payload AuthorRegistrationRejected event
    ) {
        // 거절 이벤트 수신 시 처리 로직 (예시)
        System.out.println(
            "##### listener AuthorRegistrationRejected : " + event
        );
        // 필요시 authorRepository 등 활용 가능
    }
    */
}
//>>> Clean Arch / Inbound Adaptor
