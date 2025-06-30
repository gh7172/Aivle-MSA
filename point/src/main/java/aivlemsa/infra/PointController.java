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
}
//>>> Clean Arch / Inbound Adaptor
