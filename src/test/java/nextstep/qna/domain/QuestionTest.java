package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static nextstep.qna.domain.AnswerTest.*;
import static nextstep.users.domain.NsUserTest.*;
import static org.assertj.core.api.Assertions.*;

public class QuestionTest {
    public static final Question Q1 = new Question(JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(SANJIGI, "title2", "contents2");

    @DisplayName("Question객체의 deleted상태를 true로 바꿔 질문을 삭제한다.")
    @Test
    void delete() throws CannotDeleteException {
        // given
        Question question = new Question(JAVAJIGI, "title1", "contents1");

        // when
        question.delete(JAVAJIGI);

        // then
        assertThat(question.isDeleted()).isTrue();
    }

    @DisplayName("질문을 삭제할 때 로그인 정보와 질문의 사용자 정보가 일치 하지 않으면 예외를 던진다.")
    @Test
    void deleteWhenNotEqualWithLoginUser() {
        // given
        Question question = new Question(JAVAJIGI, "title1", "contents1");

        // when & then
        assertThatThrownBy(() -> question.delete(SANJIGI)).isInstanceOf(CannotDeleteException.class)
            .hasMessage("질문을 삭제할 권한이 없습니다.");
    }

    @DisplayName("질문을 삭제할 때 작성자가 다른 답변이 있으면 예외를 던진다.")
    @Test
    void deleteWhenNotEqualWithAnswerWriter() {
        // given
        Question question = new Question(JAVAJIGI, "title1", "contents1");
        question.addAnswer(A1);
        question.addAnswer(A2);

        // when & then
        assertThatThrownBy(() -> question.delete(JAVAJIGI)).isInstanceOf(CannotDeleteException.class)
            .hasMessage("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }

    @DisplayName("현재 시간을 인자로 받아 질문, 답변의 삭제 기록 리스트를 반환한다.")
    @Test
    void deleteHistories() throws CannotDeleteException {
        // given
        Question question = new Question(JAVAJIGI, "title1", "contents1");
        Answer answer1 = new Answer(JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
        Answer answer2 = new Answer(JAVAJIGI, QuestionTest.Q1, "Answers Contents2");
        question.addAnswer(answer1);
        question.addAnswer(answer2);
        question.delete(JAVAJIGI);

        LocalDateTime now = LocalDateTime.of(2023,11,28,13,0);

        // when
        List<DeleteHistory> deleteHistories = question.deleteHistories(now);

        // then
        assertThat(deleteHistories).hasSize(3)
            .containsExactlyInAnyOrder(
                new DeleteHistory(ContentType.ANSWER, null, JAVAJIGI, LocalDateTime.of(2023, 11, 28, 13, 0)),
                new DeleteHistory(ContentType.ANSWER, null, JAVAJIGI, LocalDateTime.of(2023, 11, 28, 13, 0)),
                new DeleteHistory(ContentType.QUESTION, 0L, JAVAJIGI, LocalDateTime.of(2023, 11, 28, 13, 0))
            );
    }
}
