package aivlemsa.infra;

import aivlemsa.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;

@RestController
@RequestMapping("/author")
@Transactional
public class AuthorController {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    // 1. 작가 등록 신청 (사용자)
    @PostMapping("/apply")
    public ResponseEntity<?> applyAuthor(@RequestBody AuthorApplyRequest request) {
        Author author = new Author();
        author.setUserId(request.getUserId());
        author.setPenName(request.getPenName());
        author.setPortfolio(request.getPortfolio());
        author.setSelfIntroduction(request.getSelfIntroduction());
        authorRepository.save(author);

        // Kafka로 신청 이벤트 발행 (타입 헤더 추가)
        Message<AuthorApplyRequest> applyMsg = MessageBuilder
            .withPayload(request)
            .setHeader("__TypeId__", "aivlemsa.domain.AuthorApplyRequest")
            .build();
        kafkaTemplate.send("author-registration-requested", applyMsg);

        return ResponseEntity.ok("작가 등록 신청 완료");
    }

    // 2. 관리자 승인/거절
    @PostMapping("/approve")
    public ResponseEntity<?> approveAuthor(@RequestBody AuthorApproveRequest request) {
        Author author = authorRepository.findById(request.getUserId()).orElse(null);
        if (author == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("신청 내역 없음");
        }
        Message<AuthorApproveRequest> approveMsg = MessageBuilder
            .withPayload(request)
            .setHeader("__TypeId__", "aivlemsa.domain.AuthorApproveRequest")
            .setHeader("type", "AuthorRegistrationApproved")
            .build();
        if (request.isApproved()) {
            if (author != null) {
                AuthorRegistrationApproved event = new AuthorRegistrationApproved(author);
                // Kafka로 aivlemsa 토픽에 이벤트 발행 (타입 헤더 추가)
                Message<AuthorRegistrationApproved> approvedMsg = MessageBuilder
                    .withPayload(event)
                    .setHeader("__TypeId__", "aivlemsa.domain.AuthorRegistrationApproved")
                    .setHeader("type", "AuthorRegistrationApproved")
                    .build();
                System.out.println(">>> 승인 메시지 발행: " + approvedMsg); // 로그 추가
                kafkaTemplate.send("aivlemsa", approvedMsg);
            }
            return ResponseEntity.ok("작가 등록 승인");
        } else {
            // Kafka로 거절 이벤트 발행 (타입 헤더 추가)
            kafkaTemplate.send("author-registration-rejected", approveMsg);
            return ResponseEntity.ok("작가 등록 거절");
        }
    }
}
//>>> Clean Arch / Inbound Adaptor
// kafka-console-consumer --bootstrap-server localhost:9092 --topic aivlemsa --from-beginning
