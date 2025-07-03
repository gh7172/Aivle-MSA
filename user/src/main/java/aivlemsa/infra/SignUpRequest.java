package aivlemsa.infra;

import lombok.Data;

@Data
public class SignUpRequest {
    private String loginId;
    private String password;
    private String name;
    private Boolean isAuthor;
    private Boolean isKtCustomer;
}
