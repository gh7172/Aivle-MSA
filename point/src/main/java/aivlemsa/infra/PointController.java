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

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/points")
@Transactional
public class PointController {
    @Autowired
    PointRepository pointRepository;

    @RequestMapping(value = "/points/chargepoints",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Point chargePoints(HttpServletRequest request, HttpServletResponse response
        ) throws Exception {
            System.out.println("##### /point/chargePoints  called #####");
            Point point = new Point();
            point.chargePoints();
            pointRepository.save(point);
            return point;
    }
}
//>>> Clean Arch / Inbound Adaptor
