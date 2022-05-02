package UserService.Controllers;

import ParallelSolarPanelsPackage.Model.UserDTO;
import UserService.Entities.UserE;
import UserService.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path="/users")
@Component
public class UserController {
    @Autowired
    private UserRepository userRepository;

    private UserE retrieveUser(String username) {
        try {
            Optional<UserE> user = userRepository.findByUN(username);
            return user.get();
        }
        catch (Exception ex){
            return null;
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/")
    public boolean createUser(@RequestBody UserDTO userDTO) {
        UserE user = UserE.fromDTO(userDTO);
        UserE savedUser;
        if (retrieveUser(user.getUsername()) == null) {
            user.setId(UUID.randomUUID().toString());
            savedUser = userRepository.save(user);
            return true;
        }
        return false;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/check/")
    public UserE checkUser(@RequestBody UserDTO userDTO) {
        UserE user = UserE.fromDTO((UserDTO) userDTO);
        UserE savedUser;
        try {
            savedUser = retrieveUser(user.getUsername());
            return (savedUser.getPassword().equals(user.getPassword())) ? savedUser : null;
        }
        catch (Exception e) {
            return null;
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/update/")
    public boolean updateUser(@RequestBody UserDTO userDTO) {
        try {
            UserE newUser = UserE.fromDTO(userDTO);
            userRepository.save(newUser);
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }
}
