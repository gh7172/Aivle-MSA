package aivlemsa.application;

import aivlemsa.application.dto.BookDetailDto;
import aivlemsa.application.dto.BookSimpleDto;
import aivlemsa.domain.Book;
import aivlemsa.domain.BookAddFailed;
import aivlemsa.domain.BookRepository;
import aivlemsa.domain.GenerationSucceeded;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {
    private final BookRepository bookRepository;

    /**
     * 모든 책 목록을 Simple DTO로 조회합니다.
     */
    @Transactional(readOnly = true) // 읽기 전용 트랜잭션으로 성능 향상
    public List<BookSimpleDto> findAllBooks() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false)
                .map(BookSimpleDto::new)
                .collect(Collectors.toList());
    }

    /**
     * ID로 특정 책의 상세 정보를 조회하고, 조회수를 1 증가시킵니다.
     */
    @Transactional
    public Optional<BookDetailDto> findBookById(Long bookId) {
        // 1. 먼저 책이 존재하는지 확인합니다.
        return bookRepository.findById(bookId)
                .flatMap(book -> { // flatMap을 사용하여 Optional<Optional<...>>이 되는 것을 방지합니다.
                    // 2. 책이 존재하면, 조회수를 원자적으로 업데이트합니다.
                    bookRepository.incrementViewCount(bookId);

                    // 3. 업데이트된 최신 정보를 DB에서 다시 조회하여 DTO로 변환해 반환합니다.
                    return bookRepository.findById(bookId).map(BookDetailDto::new);
                });
    }

    @Transactional
    public void addBook(GenerationSucceeded generationSucceeded) {
        //implement business logic here:

        /** Example 1:  new item */
        Book book = new Book();
        book.setBookId(generationSucceeded.getBookId());
        book.setSummary(generationSucceeded.getSummary());
        book.setCoverImage(generationSucceeded.getCoverImage());
        book.setState("add Book!");
        book.setText(generationSucceeded.getText());
        book.setPublishDate(generationSucceeded.getPublishDate());
        book.setTitle(generationSucceeded.getTitle());
        book.setUserId(generationSucceeded.getUserId());
        book.setViewCount(0); // viewCount 0으로 초기화
        try {
            bookRepository.save(book);
        } catch (Exception e) {
            log.error("도서 저장 실패", e);
            BookAddFailed bookAddFailed = new BookAddFailed();
            BeanUtils.copyProperties(book, bookAddFailed);
            bookAddFailed.setState("add book Failed");
            bookAddFailed.publishAfterCommit();
        }



        /** Example 2:  finding and process */
        /*
        repository().findById(generationSucceeded.bookId).ifPresent(book->{
            book.setSummary(generationSucceeded.summary);
            book.setCoverImage(generationSucceeded.imageUrl);
            book.setState(generationSucceeded.state);
            repository().save(book);
        });
        */
    }
}
