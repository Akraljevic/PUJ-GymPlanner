package ba.sum.workoutplanner.repositories;

import ba.sum.workoutplanner.models.User;
import ba.sum.workoutplanner.models.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {
    List<WorkoutPlan> findByUser(User user);

}
