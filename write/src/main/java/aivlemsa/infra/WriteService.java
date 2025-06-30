package aivlemsa.infra;

import aivlemsa.domain.Write;
import aivlemsa.domain.WriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@Transactional
public class WriteService {

    @Autowired
    private WriteRepository writeRepository;

    // 나중에 변경한다면, 출간요청된 책은 수정 불가로 변경
    // 상태 변경 전용 서비스 (커스텀 엔드포인트용)
    public Write changeState(Long bookId, String newState) {
        Write write = findById(bookId);

        // 서비스를 통한 상태 변경임을 표시
        write.changeStateByService(newState);

        return writeRepository.save(write);
    }

    public Write findById(Long bookId) {
        return writeRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));
    }
}