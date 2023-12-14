package nextstep.courses.domain.session;

import nextstep.courses.domain.session.coverimage.CoverImages;
import nextstep.courses.domain.session.enroll.Enrolment;
import nextstep.courses.domain.session.enroll.RecruitingStatus;
import nextstep.courses.domain.session.period.Period;
import nextstep.courses.domain.session.student.SessionStudents;
import nextstep.courses.dto.EnrolmentInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static nextstep.courses.domain.session.PayType.FREE;
import static nextstep.courses.domain.session.PayType.PAY;
import static nextstep.courses.domain.session.SessionStatus.PREPARE;
import static nextstep.courses.domain.session.SessionStatus.PROGRESS;
import static nextstep.courses.domain.session.enroll.RecruitingStatus.*;
import static nextstep.users.domain.fixture.DomainFixture.JAVAJIGI;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FreeSessionTest {

    @DisplayName("현재 강의의 상태가 진행중이 아니면 등록할 때 예외를 발생시킨다.")
    @Test
    void validateRecruitingStatus() {
        // given
        FreeSession freeSession = createFreeSession(PREPARE, RECRUITING_ON);
        EnrolmentInfo enrolmentInfo = createEnrolment(freeSession.id, JAVAJIGI.getId(), 10000L);

        // when & then
        assertThatThrownBy(() -> freeSession.enroll(enrolmentInfo)).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("해당 강의는 현재 준비중입니다.");
    }

    @DisplayName("현재 강의의 상태가 진행중이 아니면 등록할 때 예외를 발생시킨다.")
    @Test
    void validateSessionStatus() {
        // given
        FreeSession freeSession = createFreeSession(PROGRESS, RECRUITING_OFF);
        EnrolmentInfo enrolmentInfo = createEnrolment(freeSession.id, JAVAJIGI.getId(), 10000L);

        // when & then
        assertThatThrownBy(() -> freeSession.enroll(enrolmentInfo)).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("해당 강의는 현재 모집중이 아닙니다.");
    }

    private FreeSession createFreeSession(SessionStatus sessionStatus, RecruitingStatus recruitingStatus) {
        return new FreeSession(
            1L,
            PAY,
            sessionStatus,
            recruitingStatus,
            new CoverImages(),
            new SessionStudents(),
            Period.from(
                LocalDate.of(2023, 12, 5),
                LocalDate.now()
            ));
    }

    private EnrolmentInfo createEnrolment(Long sessionId, Long nsUserId, Long payAmount) {
        return new EnrolmentInfo(sessionId, nsUserId, payAmount);
    }
}