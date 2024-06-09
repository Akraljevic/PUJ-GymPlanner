package ba.sum.workoutplanner.repositories;

import ba.sum.workoutplanner.models.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
}
