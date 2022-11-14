package com.example.monitoring.common.util.grade;

import com.example.monitoring.common.exception.NoMisMatchGradeException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GradeRouter {
    private final List<Grade> grades;

    public Grade findByGradeElseThrow(String grade) {
        return grades.stream()
                .filter(x -> x.isSame(grade))
                .findAny()
                .orElseThrow(() -> new NoMisMatchGradeException());
    }

    public List<Grade> findByGradeListElseThrow(String grade) {
        int value = findByGradeElseThrow(grade).getGradeValue();
        return grades.stream()
                .filter(x -> x.getGradeValue() >= value)
                .collect(Collectors.toList());
    }

}
