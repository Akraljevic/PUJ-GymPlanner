package ba.sum.workoutplanner.service;
import ba.sum.workoutplanner.dto.UserDto;
import ba.sum.workoutplanner.models.User;
public interface UserService {

    User findByUsername(String username);
    User save (UserDto userDto);

}