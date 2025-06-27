package aivlemsa.domain;

import aivlemsa.WriteApplication;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "write_table")
@Data
//<<< DDD / Aggregate Root
public class Write {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;

    private Long userId;

    private String title;

    private Date publishDate;

    private String summary;

    private String state;

    @PostPersist
    public void onPostPersist() {
        BookPublicationRequested bookPublicationRequested = new BookPublicationRequested(
                this
        );
        bookPublicationRequested.publishAfterCommit();
    }

    @PrePersist
    public void onPrePersist() {
        BookDraftSaved bookDraftSaved = new BookDraftSaved(this);
        bookDraftSaved.publishAfterCommit();
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



        repository().findById(bookAddFailed.getBookId()).ifPresent(write->{
         write.setState(bookAddFailed.getState());
         repository().save(write);

         });

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root