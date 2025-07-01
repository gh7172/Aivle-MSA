package aivlemsa.domain;

import aivlemsa.infra.AbstractEvent;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UsedPoints extends AbstractEvent {
    private Long userId;
    private Integer points;

    public UsedPoints(Point aggregate) {
        super(aggregate);
        this.userId = aggregate.getUserId();
        this.points = aggregate.getPoints();
    }

    public UsedPoints() {
        super();
    }
}
