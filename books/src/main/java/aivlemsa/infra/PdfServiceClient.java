package aivlemsa.infra;

import aivlemsa.application.dto.BookDetailDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "pdf-generator-client", url = "${feign.client.pdf-generator.url}")
public interface PdfServiceClient {

    @PostMapping("/pdf")
    byte[] generatePdf(@RequestBody BookDetailDto bookData);
}
