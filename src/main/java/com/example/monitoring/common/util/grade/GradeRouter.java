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
    private final BronzeGrade defaultGrade;

    public Grade findByGradeElseGetBronze(String grade) {
        return grades.stream()
                .filter(x -> x.isSame(grade))
                .findAny()
                .orElseThrow(() -> new NoMisMatchGradeException());
    }

    public List<Grade> findByGradeListElseGetBronze(String grade) {
        int value = findByGradeElseGetBronze(grade).getGradeValue();
        return grades.stream()
                .filter(x -> x.getGradeValue() >= value)
                .collect(Collectors.toList());
    }

}
