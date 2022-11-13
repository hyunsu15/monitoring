package com.example.monitoring.common.util.grade;

import java.util.List;
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
                .orElseGet(() -> defaultGrade);
    }
}
