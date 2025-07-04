package aivlemsa.infra;

import aivlemsa.application.BookService;
import aivlemsa.config.kafka.KafkaProcessor;
import aivlemsa.domain.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동으로 생성합니다.
@Slf4j
public class PolicyHandler {

    // BookRepository 대신 BookService를 주입받습니다.
    private final BookService bookService;

    @StreamListener(
            value = KafkaProcessor.INPUT,
            condition = "headers['type']=='GenerationSucceeded'"
    )
    public void wheneverGenerationSucceeded_AddBook(
            @Payload GenerationSucceeded generationSucceeded
    ) {
        // System.out.println 대신 logger를 사용합니다.
        log.info(
                "\n\n##### listener AddBook : {}\n\n",
                generationSucceeded.toString()
        );

        // Book 엔티티의 정적 메서드 호출 대신,
        // 주입받은 BookService의 메서드를 호출합니다.
        bookService.addBook(generationSucceeded);
    }
}
//>>> Clean Arch / Inbound Adaptor
