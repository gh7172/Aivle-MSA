package aivlemsa.pdf_generator.infra;

import aivlemsa.pdf_generator.application.PdfGenerationService;
import aivlemsa.pdf_generator.application.dto.BookDetailDto;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PdfGenerationController {

    private final PdfGenerationService pdfGenerationService;

    @PostMapping("/pdf")
    public ResponseEntity<byte[]> generatePdfFromBook(@RequestBody BookDetailDto bookData) {
        try {
            // 2. 실제 PDF 생성 로직은 서비스에 위임
            byte[] pdfBytes = pdfGenerationService.generatePdf(bookData);

            // 3. 컨트롤러는 HTTP 응답을 만드는 역할에만 집중
            return ResponseEntity.ok(pdfBytes);

        } catch (Exception e) {
            // 서비스에서 발생한 예외를 처리하여 500 에러 응답을 반환
            log.error("An error occurred while handling PDF generation request for bookId: {}", bookData.getBookId(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}