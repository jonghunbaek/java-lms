package nextstep.courses.dto;

public class EnrolmentInfo {

    private Long sessionId;
    private Long nsUserId;
    private Long payAmount;

    public EnrolmentInfo(Long sessionId, Long nsUserId, Long payAmount) {
        this.sessionId = sessionId;
        this.nsUserId = nsUserId;
        this.payAmount = payAmount;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public Long getNsUserId() {
        return this.nsUserId;
    }

    public boolean isNotSameAmount(Long amount) {
        return !this.payAmount.equals(amount);
    }
}
