package EntryService.Controllers;

import EntryService.vo.UserVO;
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
    public boolean createUser(@RequestBody UserVO userVO) {
        boolean response = restTemplate.postForObject( userUrl, userVO, Boolean.class);
        return response;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/check/")
    public UserVO checkUser(@RequestBody UserVO userVO) {
        UserVO response = restTemplate.postForObject( userUrl + "check/", userVO, UserVO.class);
        return response;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/update/")
    public boolean updateUser(@RequestBody UserVO userVO) {
        boolean response = restTemplate.postForObject( userUrl + "update/", userVO, Boolean.class);
        return response;
    }
}
