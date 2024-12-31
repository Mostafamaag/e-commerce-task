package flagak.com.e_commerce.repository;

import flagak.com.e_commerce.model.Cart;
import flagak.com.e_commerce.model.CartItem;
import flagak.com.e_commerce.model.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem, Long> {

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    @Modifying
    void deleteAllByCart(Cart cart);
}