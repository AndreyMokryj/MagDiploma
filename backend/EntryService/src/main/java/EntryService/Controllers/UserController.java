package EntryService.Controllers;

import ParallelSolarPanelsPackage.Model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path="/app/users")
@Component
public class UserController {
    @Autowired
    RestTemplate restTemplate;

    private String userUrl = "http://user-service/users/";

    @CrossOrigin(origins = "*")
    @PostMapping("/")
    public boolean createUser(@RequestBody UserDTO userDTO) {
        boolean response = restTemplate.postForObject( userUrl, userDTO, Boolean.class);
        return response;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/check/")
    public UserDTO checkUser(@RequestBody UserDTO userDTO) {
        UserDTO response = restTemplate.postForObject( userUrl + "check/", userDTO, UserDTO.class);
        return response;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/update/")
    public boolean updateUser(@RequestBody UserDTO userDTO) {
        boolean response = restTemplate.postForObject( userUrl + "update/", userDTO, Boolean.class);
        return response;
    }
}
