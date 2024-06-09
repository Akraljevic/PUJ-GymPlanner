package ba.sum.workoutplanner.service;

import ba.sum.workoutplanner.models.Workout;
import ba.sum.workoutplanner.models.WorkoutPlan;
import ba.sum.workoutplanner.repositories.UserRepository;
import ba.sum.workoutplanner.repositories.WorkoutPlanRepository;
import ba.sum.workoutplanner.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutPlanService {

    @Autowired
    private WorkoutPlanRepository workoutPlanRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private UserRepository userRepository;

    public List<WorkoutPlan> getWorkoutPlansByUsername(String username) {
        return workoutPlanRepository.findByUser(userRepository.findByUsername(username));
    }

    public WorkoutPlan saveWorkoutPlan(WorkoutPlan workoutPlan) {
        return workoutPlanRepository.save(workoutPlan);
    }

    public void deleteWorkoutPlan(Long id) {
        workoutPlanRepository.deleteById(id);
    }

    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }
    public WorkoutPlan getWorkoutPlanById(Long id) {
        return workoutPlanRepository.findById(id).orElse(null);

    }
}
