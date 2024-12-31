package flagak.com.e_commerce.service;

import flagak.com.e_commerce.exception.InvalidCredentialsException;
import flagak.com.e_commerce.exception.AlreadyExistsException;
import flagak.com.e_commerce.model.Vendor;
import flagak.com.e_commerce.repository.VendorRepository;
import flagak.com.e_commerce.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class VendorService {
    private final VendorRepository vendorRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public VendorService(VendorRepository vendorRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtil jwtUtil) {
        this.vendorRepository = vendorRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;

    }

    public Vendor signUp(String email, String password, String businessName,String businessCertificateNumber,String billingAddress){
        if(vendorRepository.findByEmail(email).isPresent()){
            throw new AlreadyExistsException("Vendor with this email already exists");
        }
        if(vendorRepository.businessCertificateNumber(businessCertificateNumber).isPresent()){
            throw new AlreadyExistsException("Wrong business certificate number");
        }
        if(vendorRepository.findByBillingAddress(billingAddress).isPresent()){
            throw new AlreadyExistsException("Wrong billing address");
        }

        Vendor vendor = new Vendor();
        vendor.setEmail(email);
        String hashedPassword = bCryptPasswordEncoder.encode(password);
        vendor.setPassword(hashedPassword);
        vendor.setBusinessName(businessName);
        vendor.setBillingAddress(billingAddress);
        vendor.setBusinessCertificateNumber(businessCertificateNumber);

        return vendorRepository.save(vendor);
    }

    public String login(String email, String password){
        Vendor vendor = vendorRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("Wrong email or password!"));

        if(!bCryptPasswordEncoder.matches(password, vendor.getPassword())){
            throw new InvalidCredentialsException("Wrong email or password!");
        }

        return jwtUtil.generateToken(vendor.getId(),"VENDOR");
    }
}
