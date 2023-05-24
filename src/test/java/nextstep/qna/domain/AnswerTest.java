package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static nextstep.qna.domain.QuestionTest.Q1;
import static org.assertj.core.api.Assertions.*;

public class AnswerTest {
    public static final Answer A1 = new Answer(NsUserTest.JAVAJIGI, Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(NsUserTest.SANJIGI, Q1, "Answers Contents2");

    @Test
    @DisplayName("로그인 사용자와 답변자 체크")
    void validateDelete_exception() {
        assertThatThrownBy(() -> {
            A1.validateDelete(NsUserTest.SANJIGI);
        }).isInstanceOf(CannotDeleteException.class).hasMessageContaining("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }

    @Test
    @DisplayName("답변 삭제")
    void delete() {
        A1.delete();

        assertThat(A1.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("삭제 기록 확인")
    void deleteHistory() {
        DeleteHistory deleteHistory = A1.createDeleteHistory();

        assertThat(deleteHistory).isEqualTo(DeleteHistory.of(ContentType.ANSWER, null, NsUserTest.JAVAJIGI, LocalDateTime.now()));
    }
}
