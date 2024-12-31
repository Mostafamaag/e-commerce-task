package flagak.com.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import flagak.com.e_commerce.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //Optional<User> findById(String email);
    Optional<User> findByEmail(String email);
    //Optional<User> findById(Long id);
    Optional<User> findById(Long id);
}
