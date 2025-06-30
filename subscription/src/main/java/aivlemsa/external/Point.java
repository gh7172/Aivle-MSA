package aivlemsa.external;

import java.util.Date;
import lombok.Data;

@Data
public class Point {

    private Long userId;
    private Integer points;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Integer getPoints() { return points; }
    public void setPoints(Integer points) { this.points = points; }
}
