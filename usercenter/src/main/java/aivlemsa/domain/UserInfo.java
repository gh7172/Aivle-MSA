package aivlemsa.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "UserInfo_table")
@Data
public class UserInfo {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long userId;

    private Date subscriptionExpiryDate;
    private String bookId;
    private Integer points;
}
