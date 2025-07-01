package aivlemsa.infra;

public class SignUpRequest {
    private String loginId;
    private String password;
    private Boolean isAuthor;

    public String getLoginId() {
        return loginId;
    }
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Boolean getIsAuthor() {
        return isAuthor;
    }
    public void setIsAuthor(Boolean isAuthor) {
        this.isAuthor = isAuthor;
    }
}
