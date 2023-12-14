package nextstep.courses.domain.session.student;

import nextstep.courses.domain.session.enums.SelectionStatus;

import java.util.ArrayList;
import java.util.List;

import static nextstep.courses.domain.session.enums.SelectionStatus.*;

public class SessionStudents {

    private List<SessionStudent> students;

    public SessionStudents() {
        this.students = new ArrayList<>();
    }

    public SessionStudents(List<SessionStudent> students) {
        this.students = students;
    }

    public boolean add(SessionStudent student) {
        validateDuplicate(student);

        return this.students.add(student);
    }

    private void validateDuplicate(SessionStudent student) {
        if (students.contains(student)) {
            throw new IllegalArgumentException("이미 해당 강의를 수강 중 입니다.");
        }
    }

    public int numOfAllStudents() {
        return this.students.size();
    }

    public long numOfSelectedStudents() {
        return students.stream()
            .filter(student -> student.getSelectionStatus().equals(APPROVAL))
            .count();
    }

    public SessionStudent selectStudent(SessionStudent waitingStudent, SelectionStatus selectionStatus) {
        return students.stream()
            .filter(student -> student.equals(waitingStudent))
            .findFirst().orElseThrow(() -> new IllegalArgumentException("수강생이 존재하지 않습니다."))
            .changeStatus(selectionStatus);
    }
}
