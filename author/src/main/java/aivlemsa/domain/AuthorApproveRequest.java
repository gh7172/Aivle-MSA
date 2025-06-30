package aivlemsa.domain;

public class AuthorApproveRequest {
    private Long userId;
    private boolean approved;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }
}
