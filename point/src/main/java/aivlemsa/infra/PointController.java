package aivlemsa.infra;
import aivlemsa.domain.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;


@RestController
@Transactional
public class PointController {
    @Autowired
    PointRepository pointRepository;

    @PostMapping(value = "/points/charge", produces = "application/json;charset=UTF-8")
    public Point chargePoints(@RequestParam Long userId, @RequestParam int amount) {
        System.out.println("##### /points/charge called #####");

        Point point = pointRepository.findById(userId)
            .orElseGet(() -> {
                Point newPoint = new Point();
                newPoint.setUserId(userId);
                newPoint.setPoints(0);
                return newPoint;
            });

        point.chargePoints(amount);
        pointRepository.save(point);
        return point;
    }

    @PostMapping(value = "/points/use", produces = "application/json;charset=UTF-8")
    public Point usePoints(@RequestParam Long userId, @RequestParam int amount) {
        System.out.println("##### /points/use called #####");

        Point point = pointRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("해당 유저의 포인트 정보가 없습니다."));

        try {
            point.usePoints(amount);
            pointRepository.save(point);

            // ✅ 성공 시 이벤트 발행
            PaymentSucceeded succeededEvent = new PaymentSucceeded(point);
            succeededEvent.publishAfterCommit();

        } catch (IllegalArgumentException e) {
            // ✅ 실패 시 이벤트 발행
            PaymentFailed failedEvent = new PaymentFailed(point);
            failedEvent.publishAfterCommit();

            throw e; // 500 대신 400 에러 처리해도 OK
        }
        return point;
    }
}
//>>> Clean Arch / Inbound Adaptor
