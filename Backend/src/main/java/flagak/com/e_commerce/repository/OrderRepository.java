package flagak.com.e_commerce.repository;

import flagak.com.e_commerce.model.Order;
import flagak.com.e_commerce.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByUser(User user);
}
