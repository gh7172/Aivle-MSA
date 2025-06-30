package aivlemsa.infra;

import aivlemsa.domain.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/User")
@Transactional
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/users")
    public User signUp(@RequestBody SignUpRequest request) {
        User user = new User();
        user.setLoginId(request.getLoginId());
        user.setPassword(request.getPassword());
        user.setIsAuthor(request.getIsAuthor());
        return userRepository.save(user);
    }
}
//>>> Clean Arch / Inbound Adaptor
