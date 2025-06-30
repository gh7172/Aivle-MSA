package aivlemsa.infra;

import aivlemsa.domain.GetPay;
import aivlemsa.domain.GetPayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetPayQueryService {

    private final GetPayRepository getPayRepository;

    public Optional<GetPayQuery> getPayById(String id) {
        return getPayRepository.findById(id)
                .map(entity -> new GetPayQuery(
                        entity.getId(),
                        entity.getUserId(),
                        entity.getBookId(),
                        entity.getStatus()
                ));
    }
}
