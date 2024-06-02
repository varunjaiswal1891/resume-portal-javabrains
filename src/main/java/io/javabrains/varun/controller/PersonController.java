package io.javabrains.varun.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.javabrains.varun.dto.PersonInput;
import io.javabrains.varun.models.Person;
import io.javabrains.varun.models.UserRole;
import io.javabrains.varun.repositories.PersonRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

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

    @PostMapping(path = "/persons")
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "BasicAuth")
    public Person createNewPerson(@RequestBody PersonInput personInput) {
        String hashedPassword = passwordEncoder.encode(personInput.getPassword());
        Person newPerson = new Person(personInput.getUsername(), hashedPassword, UserRole.USER);

        return personRepository.save(newPerson);
    }
}
