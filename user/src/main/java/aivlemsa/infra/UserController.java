package aivlemsa.infra;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import aivlemsa.domain.LoginRequest;
import aivlemsa.domain.User;
import aivlemsa.domain.UserRepository;

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
        user.setName(request.getName()); // 이름 설정 추가
        user.setIsAuthor(request.getIsAuthor());
        user.setIsKtCustomer(request.getIsKtCustomer()); // KT 고객 여부 설정 추가
        return ResponseEntity.ok(userRepository.save(user));
    }

    @PatchMapping("/users/{userId}/author")
    public ResponseEntity<?> setAuthorTrue(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setIsAuthor(true);
        userRepository.save(user);
        return ResponseEntity.ok("isAuthor updated");
    }

    // 사용자 정보 조회 API 추가
    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userRepository.findByLoginId(request.getLoginId());
        if (user == null || !user.getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("login failed");
        }
        return ResponseEntity.ok(user);
    }
}
//>>> Clean Arch / Inbound Adaptor
