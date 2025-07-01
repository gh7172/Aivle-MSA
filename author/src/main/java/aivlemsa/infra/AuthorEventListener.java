package aivlemsa.infra;

import aivlemsa.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AuthorEventListener {
    @Autowired
    private UserServiceClient userServiceClient;

    @KafkaListener(topics = "author-registration-approved", groupId = "author-service")
    public void handleAuthorApproved(AuthorApproveRequest event) {
        // user 서비스에 isAuthor true로 변경 요청
        userServiceClient.setAuthorTrue(event.getUserId());
        System.out.println("작가 등록 승인 이벤트 수신: " + event.getUserId());
    }

    @KafkaListener(topics = "author-registration-rejected", groupId = "author-service")
    public void handleAuthorRejected(AuthorApproveRequest event) {
        // TODO: 거절 알림 처리
        System.out.println("작가 등록 거절 이벤트 수신: " + event.getUserId());
    }
}
