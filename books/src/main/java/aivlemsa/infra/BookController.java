package aivlemsa.infra;

import aivlemsa.application.BookService;
import aivlemsa.application.dto.BookDetailDto;
import aivlemsa.application.dto.BookSimpleDto;
import aivlemsa.domain.*;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RestController
@RequestMapping(value="/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService; // Repository 대신 Service 주입

    /**
     * 전체 책 목록 조회 API (본문 제외)
     * GET /books
     */
    @GetMapping
    public ResponseEntity<List<BookSimpleDto>> getAllBooks() {
        List<BookSimpleDto> books = bookService.findAllBooks();
        return ResponseEntity.ok(books);
    }

    /**
     * 특정 책 상세 조회 API (본문 포함)
     * GET /books/{bookId}
     */
    @GetMapping("/{bookId}")
    public ResponseEntity<BookDetailDto> getBookById(@PathVariable Long bookId) {
        return bookService.findBookById(bookId)
                .map(ResponseEntity::ok) // Optional에 값이 있으면 200 OK
                .orElse(ResponseEntity.notFound().build()); // 없으면 404 Not Found
    }
}
//>>> Clean Arch / Inbound Adaptor 