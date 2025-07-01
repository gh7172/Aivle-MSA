package aivlemsa.infra;

import aivlemsa.domain.*;
import aivlemsa.external.PointService;
import aivlemsa.external.Point;
import aivlemsa.domain.Subscribe;
import aivlemsa.domain.SubscribeRepository;

import java.util.Optional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional
public class SubscribeController {

    @Autowired
    SubscribeRepository subscribeRepository;

    @Autowired
    private PointService pointService;

    @PostMapping("/subscribe")
    public String subscribe(@RequestParam Long userId) {
        final int requiredPoints = 9900;

        try {
            // 포인트 차감 시도
            pointService.useSubscriptionPoints(userId, requiredPoints);

            // 포인트 차감 성공 시 구독 처리
            Subscribe subscribe = subscribeRepository.findById(userId)
                .orElseGet(() -> {
                    Subscribe newSub = new Subscribe();
                    newSub.setUserId(userId);
                    return newSub;
                });

            Date currentExpiry = subscribe.getSubscriptionExpiryDate();
            LocalDate baseDate = LocalDate.now();

            if (currentExpiry != null) {
                try {
                    baseDate = new java.sql.Timestamp(currentExpiry.getTime())
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                } catch (Exception e) {
                    System.out.println("만료일 변환 오류 발생: " + e.getMessage());
                }
            }

            LocalDate newExpiryDate = baseDate.plusDays(30);
            Date newExpiry = Date.from(newExpiryDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            subscribe.setSubscriptionExpiryDate(newExpiry);
            subscribeRepository.save(subscribe);

            return "구독 성공 및 포인트 차감 완료";

        } catch (Exception e) {
            return "포인트 부족으로 구독 실패";
        }
    }
}
