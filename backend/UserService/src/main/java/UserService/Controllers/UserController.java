package UserService.Controllers;

import UserService.Entities.UserE;
import UserService.vo.UserVO;
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
    public boolean createUser(@RequestBody UserVO userVO) {
        UserE user = UserE.fromVO(userVO);
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
    public UserE checkUser(@RequestBody UserVO userVO) {
        UserE user = UserE.fromVO((UserVO) userVO);
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
    public boolean updateUser(@RequestBody UserVO userVO) {
        try {
            UserE newUser = UserE.fromVO(userVO);
            userRepository.save(newUser);
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }
}
