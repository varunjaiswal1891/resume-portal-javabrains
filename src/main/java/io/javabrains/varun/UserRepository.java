package io.javabrains.varun;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import io.javabrains.varun.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUserName(String userName);
}
