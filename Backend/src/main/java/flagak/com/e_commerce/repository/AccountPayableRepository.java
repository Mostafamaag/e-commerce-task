package flagak.com.e_commerce.repository;

import flagak.com.e_commerce.model.AccountPayable;
import flagak.com.e_commerce.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountPayableRepository extends JpaRepository<AccountPayable, Long> {
    List<AccountPayable> findByVendor(Vendor vendor);
}