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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RestController
@RequestMapping(value="/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService; // Repository 대신 Service 주입
    private final PdfServiceClient pdfServiceClient; // Feign 클라이언트 주입
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

    /**
     * 특정 책의 내용을 PDF 파일로 다운로드하는 API
     * GET /books/{bookId}/pdf
     */
    @GetMapping("/{bookId}/pdf")
    public ResponseEntity<byte[]> getBookAsPdf(@PathVariable Long bookId) {
        // 1. 책 정보를 조회합니다.
        return bookService.findBookById(bookId)
                .map(bookDetailDto -> {
                    log.info("----------------PDF-----------");
                    // 2. 책 정보가 있으면, PDF 생성 서비스에 PDF 생성을 요청합니다.
                    byte[] pdfBytes = pdfServiceClient.generatePdf(bookDetailDto);

                    // 3. 브라우저가 파일을 다운로드하도록 HTTP 헤더를 설정합니다.
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_PDF);
                    // 파일 이름을 안전한 문자로만 구성하여 설정합니다. (예: "My_Book_Title.pdf")
                    String filename = bookDetailDto.getTitle().replaceAll("[^a-zA-Z0-9.-]", "_") + ".pdf";
                    headers.setContentDispositionFormData("attachment", filename);
                    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

                    return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
                })
                .orElse(ResponseEntity.notFound().build()); // 책이 없으면 404 Not Found 반환
    }
}
//>>> Clean Arch / Inbound Adaptor 