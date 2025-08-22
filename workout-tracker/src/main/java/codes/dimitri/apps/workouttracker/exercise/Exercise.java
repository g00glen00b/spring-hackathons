package codes.dimitri.apps.workouttracker.exercise;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Exercise {
    @Id
    private UUID id;
    private String name;
    private String description;
    @Enumerated
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private ExerciseCategory category;
    @Enumerated
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private MuscleGroup muscleGroup;
}
