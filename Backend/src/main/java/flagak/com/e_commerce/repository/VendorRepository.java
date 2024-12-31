package flagak.com.e_commerce.repository;

import flagak.com.e_commerce.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    Optional<Vendor> findByEmail(String email);
    Optional<Vendor> findById(Long id);
    Optional<Vendor> businessCertificateNumber(String number);
    Optional<Vendor> findByBillingAddress(String address);
   // @Query("SELECT v FROM Vendor v WHERE v.id = :id")
    //Vendor findById(Long integer);
}
