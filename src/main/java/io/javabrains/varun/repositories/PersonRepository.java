package io.javabrains.varun.repositories;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import io.javabrains.varun.models.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
 
    Set<Person> findByIdIn(Collection<Long> ids);
    Optional<Person> findByUsername(String username);
}
