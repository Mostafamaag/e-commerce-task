package flagak.com.e_commerce.controller;


import flagak.com.e_commerce.dto.LoginDto;
import flagak.com.e_commerce.dto.SignUpUserDto;
import flagak.com.e_commerce.dto.SignUpVendorDto;
import flagak.com.e_commerce.model.User;
import flagak.com.e_commerce.model.Vendor;
import flagak.com.e_commerce.repository.VendorRepository;
import flagak.com.e_commerce.service.UserService;
import flagak.com.e_commerce.service.VendorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final VendorService vendorService;

    public AuthController(UserService userService, VendorService vendorService) {
        this.userService = userService;
        this.vendorService = vendorService;
    }
    @PostMapping(path = "user/login",  consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<Map<String, String>> login(@Valid @ModelAttribute LoginDto loginDto) {
        System.out.println("loginDto");
        String token = userService.login(loginDto.getEmail(), loginDto.getPassword());
        return ResponseEntity.ok(Map.of("token", token));
    }

//    @PostMapping("user/login")
//    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginDto loginDto) {
//        String token = userService.login(loginDto.getEmail(), loginDto.getPassword());
//        return ResponseEntity.ok(Map.of("token", token));
//    }

    @PostMapping(path = "/user/signup" ,consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<User> signUp(@Valid @ModelAttribute SignUpUserDto signUpDto) {
        User user = userService.signUp(signUpDto.getEmail(), signUpDto.getPassword());
        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }

    @PostMapping(path = "/vendor/login", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<Map<String, String>> loginVendor(@Valid @ModelAttribute LoginDto loginDto) {
        String token = vendorService.login(loginDto.getEmail(), loginDto.getPassword());
        return ResponseEntity.ok(Map.of("token", token));
    }
    @PostMapping("/vendor/signup")
    public Vendor signUp(@Valid @RequestBody SignUpVendorDto signUpVendorDto) {
        return vendorService.signUp(signUpVendorDto.getEmail(), signUpVendorDto.getPassword(),
                signUpVendorDto.getBusinessName(), signUpVendorDto.getBusinessCertificateNumber(), signUpVendorDto.getBillingAddress());
    }

}
