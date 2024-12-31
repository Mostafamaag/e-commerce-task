package flagak.com.e_commerce.service;

import flagak.com.e_commerce.exception.InvalidCredentialsException;
import flagak.com.e_commerce.exception.AlreadyExistsException;
import flagak.com.e_commerce.model.User;
import flagak.com.e_commerce.repository.UserRepository;
import flagak.com.e_commerce.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;

    }

    public User signUp(String email, String password){
        if(userRepository.findByEmail(email).isPresent()){
            throw new AlreadyExistsException("User with this email already exists");
        }
        User user = new User();
        user.setEmail(email);
        String hashedPassword = bCryptPasswordEncoder.encode(password);
        user.setPassword(hashedPassword);
        userRepository.save(user);
        return user;
    }

    public String login(String email, String password){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials!"));

        if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
            throw new InvalidCredentialsException("Wrong email or password!");
        }

        return jwtUtil.generateToken(user.getId(),"USER");
    }
}
