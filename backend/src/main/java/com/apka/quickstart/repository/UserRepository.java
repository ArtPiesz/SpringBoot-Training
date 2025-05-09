package com.apka.quickstart.repository;


import com.apka.quickstart.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

        Optional<User> findByUsername(String username);
        Optional<User> findByEmail(String email);

        Boolean existsByUsername(String username);
        Boolean existsByEmail(String email);

        User findById(long id);

}
