package aivlemsa.pdf_generator.application;

import aivlemsa.pdf_generator.application.dto.BookDetailDto;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Slf4j
@Service
public class PdfGenerationServiceImpl implements PdfGenerationService {

    private static final String KOREAN_FONT_PATH = "fonts/NanumGothic.ttf";

    // 1. 폰트 파일을 바이트 배열로 미리 로드하여 메모리에 저장합니다. (성능 최적화)
    private byte[] koreanFontBytes;

    @PostConstruct
    public void init() {
        try {
            log.info("Loading Korean font from classpath: {}", KOREAN_FONT_PATH);
            ClassPathResource resource = new ClassPathResource(KOREAN_FONT_PATH);
            try (InputStream is = resource.getInputStream()) {
                this.koreanFontBytes = is.readAllBytes();
            }
        } catch (IOException e) {
            log.error("Failed to load Korean font bytes. Service will not be able to generate PDFs with Korean text.", e);
            // 폰트 로딩 실패는 심각한 문제이므로, 애플리케이션 시작을 중단시키는 것이 더 안전할 수 있습니다.
            throw new RuntimeException("Fatal: Could not load essential Korean font.", e);
        }
    }

    @Override
    public byte[] generatePdf(BookDetailDto bookData) throws Exception {
        if (koreanFontBytes == null) {
            throw new IllegalStateException("Korean font is not available. Cannot generate PDF.");
        }

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PdfWriter writer = new PdfWriter(baos);
             PdfDocument pdf = new PdfDocument(writer)) {

            // 2. 매 요청마다 바이트 배열로부터 새로운 PdfFont 객체를 생성합니다.
            PdfFont koreanFont = PdfFontFactory.createFont(koreanFontBytes, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
            Document document = new Document(pdf).setFont(koreanFont);

            // --- 페이지 1: 커버 이미지 ---
            addCoverPage(document, bookData);

            // --- 페이지 2: 제목 페이지 ---
            addTitlePage(document, bookData);

            // --- 페이지 3: 요약 ---
            addSummaryPage(document, bookData);

            // --- 페이지 4부터: 본문 ---
            addContentPage(document, bookData);

            document.close();
            log.info("Successfully generated PDF for bookId: {}", bookData.getBookId());
            return baos.toByteArray();
        }
    }

    // --- 가독성을 위해 각 페이지 생성 로직을 private 메서드로 분리 ---

    private void addCoverPage(Document document, BookDetailDto bookData) {
        if (StringUtils.hasText(bookData.getCoverImage())) {
            try {
                Image cover = new Image(ImageDataFactory.create(new URL(bookData.getCoverImage())));
                cover.setWidth(UnitValue.createPercentValue(100));
                document.add(cover);
            } catch (Exception imgEx) {
                log.error("Failed to load cover image from URL: {}", bookData.getCoverImage(), imgEx);
                document.add(new Paragraph("Cover image could not be loaded.").setTextAlignment(TextAlignment.CENTER));
            }
        }
    }

    private void addTitlePage(Document document, BookDetailDto bookData) {
        document.add(new AreaBreak());
        Paragraph title = new Paragraph(bookData.getTitle())
                .setBold()
                .setFontSize(32)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginTop(document.getPdfDocument().getDefaultPageSize().getHeight() / 3);
        Paragraph subInfo = new Paragraph("출판일: " + bookData.getPublishDate() + " / 조회수: " + bookData.getViewCount())
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginTop(20);
        document.add(title);
        document.add(subInfo);
    }

    private void addSummaryPage(Document document, BookDetailDto bookData) {
        if (StringUtils.hasText(bookData.getSummary())) {
            document.add(new AreaBreak());
            Paragraph summaryHeader = new Paragraph("요약").setBold().setFontSize(24).setMarginTop(50).setMarginBottom(20);
            Paragraph summaryContent = new Paragraph(bookData.getSummary()).setFontSize(12);
            document.add(summaryHeader);
            document.add(summaryContent);
        }
    }

    private void addContentPage(Document document, BookDetailDto bookData) {
        if (StringUtils.hasText(bookData.getText())) {
            document.add(new AreaBreak());
            Paragraph textHeader = new Paragraph("본문").setBold().setFontSize(24).setMarginTop(50).setMarginBottom(20);
            Paragraph textContent = new Paragraph(bookData.getText()).setFontSize(12);
            document.add(textHeader);
            document.add(textContent);
        }
    }
}
