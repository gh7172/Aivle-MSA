package aivlemsa.infra;

import aivlemsa.domain.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/User")
@Transactional
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/users")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest request) {
        if (userRepository.findByLoginId(request.getLoginId()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 아이디입니다.");
        }
        User user = new User();
        user.setLoginId(request.getLoginId());
        user.setPassword(request.getPassword());
        user.setIsAuthor(request.getIsAuthor());
        return ResponseEntity.ok(userRepository.save(user));
    }
}
//>>> Clean Arch / Inbound Adaptor
