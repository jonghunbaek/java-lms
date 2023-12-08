package nextstep.courses.domain.session.student;

import java.util.ArrayList;
import java.util.List;

public class Students {

    private List<Student> students;

    public Students() {
        this.students = new ArrayList<>();
    }

    public Students(List<Student> students) {
        this.students = students;
    }

    public boolean add(Student student) {
        validateDuplicate(student);

        return this.students.add(student);
    }

    private void validateDuplicate(Student student) {
        if (students.contains(student)) {
            throw new IllegalArgumentException("이미 해당 강의를 수강 중 입니다.");
        }
    }

    public int size() {
        return this.students.size();
    }
}
