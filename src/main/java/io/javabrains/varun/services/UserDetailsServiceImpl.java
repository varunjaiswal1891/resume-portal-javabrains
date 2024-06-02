package io.javabrains.varun.services;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.javabrains.varun.models.Person;
import io.javabrains.varun.repositories.PersonRepository;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService {
    
    private final PersonRepository personRepository;

    public UserDetailsServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> personOptional = personRepository.findByUsername(username);
        if (personOptional.isEmpty()) {
            throw new UsernameNotFoundException("Username %s does not exist".formatted(username));
        }
        Person person = personOptional.get();

        return new User(person.getUsername(), person.getPasswordHash(), getAuthorities(person));
    }

    /* 
    private Collection<? extends GrantedAuthority> getAuthorities(Person person) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + person.getRole().name()));
    }
    */

    private Collection<GrantedAuthority> getAuthorities(Person person)
    {
        return Arrays.stream(person.getRole().name().split(",")) // Split the roles by comma
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
    }

}
