package ba.sum.workoutplanner.controllers;

import ba.sum.workoutplanner.models.Workout;
import ba.sum.workoutplanner.models.WorkoutPlan;
import ba.sum.workoutplanner.repositories.UserRepository;
import ba.sum.workoutplanner.service.WorkoutPlanService;
import ba.sum.workoutplanner.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class WorkoutPlanController {

    @Autowired
    private WorkoutPlanService workoutPlanService;

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/plans")
    public String getWorkoutPlans(Model model, Principal principal) {
        List<WorkoutPlan> plans = workoutPlanService.getWorkoutPlansByUsername(principal.getName());
        model.addAttribute("plans", plans);
        return "plans";
    }

    @GetMapping("/plans/new")
    public String showNewPlanForm(Model model) {
        List<Workout> workouts = workoutPlanService.getAllWorkouts();
        model.addAttribute("workouts", workouts);
        model.addAttribute("workoutPlan", new WorkoutPlan());
        return "newPlan";
    }

    @PostMapping("/plans")
    public String createWorkoutPlan(@ModelAttribute WorkoutPlan workoutPlan, @RequestParam List<Long> workoutIds, Principal principal) {
        try {
            workoutPlan.setUser(userRepository.findByUsername(principal.getName()));
            List<Workout> selectedWorkouts = workoutService.getWorkoutsByIds(workoutIds);
            workoutPlan.setWorkouts(selectedWorkouts);
            workoutPlanService.saveWorkoutPlan(workoutPlan);
            return "redirect:/plans";
        } catch (Exception e) {
            // Log the error and return an appropriate error view
            e.printStackTrace();
            return "error"; // Replace with your error page
        }
    }
    @GetMapping("/plans/{id}")
    public String viewWorkoutPlan(@PathVariable Long id, Model model) {
        WorkoutPlan workoutPlan = workoutPlanService.getWorkoutPlanById(id);
        if (workoutPlan == null) {
            return "redirect:/plans";
        }
        model.addAttribute("workoutPlan", workoutPlan);
        return "viewPlan";
    }

    @GetMapping("/plans/{id}/edit")
    public String showEditPlanForm(@PathVariable Long id, Model model) {
        WorkoutPlan workoutPlan = workoutPlanService.getWorkoutPlanById(id);
        List<Workout> workouts = workoutPlanService.getAllWorkouts();
        model.addAttribute("workoutPlan", workoutPlan);
        model.addAttribute("workouts", workouts);
        return "editPlan";
    }

    @PostMapping("/plans/{id}/update")
    public String updateWorkoutPlan(@PathVariable Long id, @ModelAttribute WorkoutPlan workoutPlan, @RequestParam List<Long> workoutIds, Principal principal) {
        WorkoutPlan existingWorkoutPlan = workoutPlanService.getWorkoutPlanById(id);
        if (existingWorkoutPlan != null) {
            existingWorkoutPlan.setName(workoutPlan.getName());
            List<Workout> selectedWorkouts = workoutService.getWorkoutsByIds(workoutIds);
            existingWorkoutPlan.setWorkouts(selectedWorkouts);
            workoutPlanService.saveWorkoutPlan(existingWorkoutPlan);
        }
        return "redirect:/plans";
    }

    @PostMapping("/plans/{id}/delete")
    public String deleteWorkoutPlan(@PathVariable Long id) {
        workoutPlanService.deleteWorkoutPlan(id);
        return "redirect:/plans";
    }

}
