package by.evlashkina.crypto.repository;

import by.evlashkina.crypto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {
    long count ();
}
