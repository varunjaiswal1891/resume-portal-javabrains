package io.javabrains.varun.controller;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.javabrains.varun.models.Person;
import io.javabrains.varun.repositories.PersonRepository;

@RestController
public class PersonController {
    
    private final PersonRepository personRepository;

    private final PasswordEncoder passwordEncoder;

    public PersonController(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, world!";
    }

    @GetMapping("/getAllPersons")
    public String getPerson() {

        StringBuilder sb = new StringBuilder();
        personRepository.findAll().forEach(person -> sb.append(person.getUsername()).append(","));
        return sb.toString();
    }
}
