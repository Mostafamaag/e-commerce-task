package flagak.com.e_commerce.repository;

import flagak.com.e_commerce.model.AccountReceivable;
import flagak.com.e_commerce.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountReceivableRepository extends JpaRepository<AccountReceivable, Long> {
    List<AccountReceivable> findByVendor(Vendor vendor);

}
