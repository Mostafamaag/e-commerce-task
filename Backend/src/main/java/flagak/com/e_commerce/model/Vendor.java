package flagak.com.e_commerce.model;


import flagak.com.e_commerce.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vendors")
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String businessName;

    @Column(nullable = false, unique = true)
    private String businessCertificateNumber;

    @Column(nullable = false, unique = true)
    private String billingAddress;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role = Role.VENDOR;


}
