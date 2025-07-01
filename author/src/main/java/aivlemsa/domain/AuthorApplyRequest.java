package aivlemsa.domain;

public class AuthorApplyRequest {
    private Long userId;
    private String penName;
    private String portfolio;
    private String selfIntroduction;

    // 기본 생성자 추가
    public AuthorApplyRequest() {}

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getPenName() { return penName; }
    public void setPenName(String penName) { this.penName = penName; }
    public String getPortfolio() { return portfolio; }
    public void setPortfolio(String portfolio) { this.portfolio = portfolio; }
    public String getSelfIntroduction() { return selfIntroduction; }
    public void setSelfIntroduction(String selfIntroduction) { this.selfIntroduction = selfIntroduction; }
}
