package ftn.sbz.cdssserver.repository;

import ftn.sbz.cdssserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    User findById(long id);

    User findByUsername(String username);
}
