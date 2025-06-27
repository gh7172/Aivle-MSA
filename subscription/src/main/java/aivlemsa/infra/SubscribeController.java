package aivlemsa.infra;

import aivlemsa.domain.*;
import aivlemsa.external.PointService;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 날짜 관련
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

// 포인트 도메인 (Point 클래스)
import aivlemsa.external.Point;

// 레포지토리와 도메인
import aivlemsa.domain.Subscribe;
import aivlemsa.domain.SubscribeRepository;

// 기타 Spring 관련
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/subscribes")
@Transactional
public class SubscribeController {

    @Autowired
    SubscribeRepository subscribeRepository;
    @Autowired
    private PointService pointService;

    // 구독 신청 + 포인트 차감 + 만료일 연장 API
    @PostMapping("/subscribe")
    public String subscribe(@RequestParam Long userId) {
        final int requiredPoints = 9900;

        // 유저 포인트 조회 (FeignClient 호출)
        Point userPoint = pointService.getUserPoints(userId);
        System.out.println("2. 포인트 조회 결과: " + userPoint);

        if (userPoint != null && userPoint.getPoints() >= requiredPoints) {
            // 포인트 차감
            Point deduction = new Point();
            deduction.setUserId(userId);
            deduction.setPoints(userPoint.getPoints() - requiredPoints);
            pointService.useSubscriptionPoints(deduction);

            // 구독 생성 또는 갱신
            Subscribe subscribe = subscribeRepository.findById(userId)
                .orElseGet(() -> {
                    Subscribe newSub = new Subscribe();
                    newSub.setUserId(userId);
                    return newSub;
                });

            Date currentExpiry = subscribe.getSubscriptionExpiryDate();
            LocalDate newExpiryDate;

            if (currentExpiry != null) {
                newExpiryDate = currentExpiry.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                    .plusDays(30);
            } else {
                newExpiryDate = LocalDate.now().plusDays(30);
            }

            Date newExpiry = Date.from(newExpiryDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            subscribe.setSubscriptionExpiryDate(newExpiry);
            subscribeRepository.save(subscribe);

            return "구독 성공 및 포인트 차감 완료";
        } else {
            return "포인트 부족으로 구독 실패";
        }
    }


}
//>>> Clean Arch / Inbound Adaptor
