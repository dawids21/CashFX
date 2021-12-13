package xyz.stasiak.cashfx;


class ApplicationState {

    private Integer userId = null;
    private Integer accountId = null;

    Integer getUserId() {
        return userId;
    }

    void setUserId(Integer userId) {
        this.userId = userId;
    }

    Integer getAccountId() {
        return accountId;
    }

    void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
}
