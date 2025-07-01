package aivlemsa.domain;

import aivlemsa.WriteApplication;

import java.time.LocalDate;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.annotate.JsonIgnore;

@Entity
@Table(name = "write_table")
@Data
@Slf4j
//<<< DDD / Aggregate Root
public class Write {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;

    private Long userId;

    private String title;

    @Lob
    private String text;

    private String state;

    @JsonIgnore
    @Transient
    private String previousState;

    @JsonIgnore
    @Transient
    private boolean stateChangedByService = false; // 서비스를 통한 상태 변경 여부

    @PrePersist
    public void onPrePersist() {
        // 초기 상태 설정
        if (this.state == null || this.state.isEmpty()) {
            this.state = "draft";
        }
    }

    // 최초 저장 시에는 이벤트 발행 안함
    // @PostPersist 제거

    @PreUpdate
    public void onPreUpdate() {
        // 업데이트 전 준비
    }

    @PostUpdate
    public void onPostUpdate() {
        // 오직 출간 요청 상태로 변경되고, 서비스를 통한 변경인 경우에만 이벤트 발행
        if ("bookPublicationRequested".equals(this.state) &&
                this.stateChangedByService &&
                !this.state.equals(this.previousState)) {

            BookPublicationRequested bookPublicationRequested = new BookPublicationRequested(this);
            bookPublicationRequested.setPublishDate(LocalDate.now());
            bookPublicationRequested.publishAfterCommit();
        }
        // 플래그 리셋
        this.stateChangedByService = false;
    }

    // 서비스에서만 호출하는 상태 변경 메서드
    public void changeStateByService(String newState) {
        this.previousState = this.state;
        this.state = newState;
        this.stateChangedByService = true;
    }

    public static WriteRepository repository() {
        WriteRepository writeRepository = WriteApplication.applicationContext.getBean(
                WriteRepository.class
        );
        return writeRepository;
    }

    //<<< Clean Arch / Port Method
    public static void stateUpdate(BookAdded bookAdded) {
        //implement business logic here:

        /** Example 1:  new item
         Write write = new Write();
         repository().save(write);

         */

        /** Example 2:  finding and process */
        log.info("State update by bookId: {}", bookAdded.getBookId());

         repository().findById(bookAdded.getBookId()).ifPresent(write->{
             write.setState(bookAdded.getState());
             repository().save(write);

         });

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void stateUpdate(GenerationFailed generationFailed) {
        //implement business logic here:

        /** Example 1:  new item
         Write write = new Write();
         repository().save(write);

         */

        /** Example 2:  finding and process */

         // if generationFailed.aiId exists, use it

         // ObjectMapper mapper = new ObjectMapper();
         // Map<, Object> 집필요청Map = mapper.convertValue(generationFailed.getAiId(), Map.class);

        log.info("State update by bookId: {}", generationFailed.getBookId());
        repository().findById(generationFailed.getBookId()).ifPresent(write->{
            write.setState(generationFailed.getState());
            repository().save(write);

        });

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void stateUpdate(GenerationSucceeded generationSucceeded) {
        //implement business logic here:

        /** Example 1:  new item
         Write write = new Write();
         repository().save(write);

         */

        /** Example 2:  finding and process */

         // if generationSucceeded.aiId exists, use it

         // ObjectMapper mapper = new ObjectMapper();
         // Map<, Object> 집필요청Map = mapper.convertValue(generationSucceeded.getAiId(), Map.class);

        log.info("State update by bookId: {}", generationSucceeded.getBookId());

        repository().findById(generationSucceeded.getBookId()).ifPresent(write->{
            write.setState(generationSucceeded.getState());
            repository().save(write);

         });

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void stateUpdate(BookAddFailed bookAddFailed) {
        //implement business logic here:

        /** Example 1:  new item
         Write write = new Write();
         repository().save(write);

         */

        /** Example 2:  finding and process         */
        log.info("State update by bookId: {}", bookAddFailed.getBookId());



        repository().findById(bookAddFailed.getBookId()).ifPresent(write->{
         write.setState(bookAddFailed.getState());
         repository().save(write);

         });

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root