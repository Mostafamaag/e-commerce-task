package flagak.com.e_commerce.repository;

import flagak.com.e_commerce.model.Product;
import flagak.com.e_commerce.model.Vendor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    Optional<Product> findById(Long id);
    List<Product> findByVendor(Vendor vendor);
    List<Product> findAll();

}
