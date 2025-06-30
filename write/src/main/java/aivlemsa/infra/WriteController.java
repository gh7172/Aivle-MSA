package aivlemsa.infra;

import aivlemsa.domain.*;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//<<< Clean Arch / Inbound Adaptor

@RestController
@RequestMapping("/write")
public class WriteController {

    @Autowired
    private WriteService writeService;

    // 상태 변경 전용 엔드포인트
    @PatchMapping("/{bookId}/changeState")
    public ResponseEntity<Write> changeState(@PathVariable Long bookId, @RequestParam String state) {
        try {
            Write updatedWrite = writeService.changeState(bookId, state);
            return ResponseEntity.ok(updatedWrite);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 출간 요청 전용 엔드포인트 (가장 간단하고 명확함)
    @PostMapping("/{bookId}/requestPublication")
    public ResponseEntity<Write> requestPublication(@PathVariable Long bookId) {
        try {
            Write updatedWrite = writeService.changeState(bookId, "bookPublicationRequested");
            return ResponseEntity.ok(updatedWrite);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
//>>> Clean Arch / Inbound Adaptor
