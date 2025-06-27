package aivlemsa.external;

import java.util.Date;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = "point", url = "${api.url.point}")
public interface PointService {
    @RequestMapping(method = RequestMethod.POST, path = "/points")
    public void useSubscriptionPoints(@RequestBody Point point);

    @GetMapping("/points/{userId}")
    Point getUserPoints(@PathVariable("userId") Long userId);


    // @PostMapping("/points")
    // void useSubscriptionPoints(@RequestBody Point point);
}
