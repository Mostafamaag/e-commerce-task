package flagak.com.e_commerce.repository;

import flagak.com.e_commerce.model.Cart;
import flagak.com.e_commerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findById(Long Id);
    Optional<Cart> findByUser(User user);
    // Find a cart by user id

}
