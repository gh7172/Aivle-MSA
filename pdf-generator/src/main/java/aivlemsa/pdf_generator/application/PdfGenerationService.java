package aivlemsa.pdf_generator.application;

import aivlemsa.pdf_generator.application.dto.BookDetailDto;

public interface PdfGenerationService {
    /**
     * BookDetailDto를 기반으로 PDF 파일의 byte 배열을 생성합니다.
     *
     * @param bookData PDF로 만들 책의 상세 정보
     * @return 생성된 PDF의 byte 배열
     * @throws Exception PDF 생성 중 오류 발생 시
     */
    byte[] generatePdf(BookDetailDto bookData) throws Exception;
}
