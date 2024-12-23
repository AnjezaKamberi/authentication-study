package com.betaplan.anjeza.auth.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.betaplan.anjeza.auth.models.User;
//import org.springframework.security.core.userdetails.User; DONT DO
import com.betaplan.anjeza.auth.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * Load user details by username for authentication
     *
     * @param username the username to search for
     * @return UserDetails containing the user's credentials and authorities
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("User not found");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user));
    }

    /**
     * Generate a list of granted authorities for the given user based on role that he has
     *
     * @param user the User whose authorities are to be retrieved
     * @return a list of GrantedAuthority objects representing the user's permissions
     */
    private List<GrantedAuthority> getAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole().name());
        authorities.add(grantedAuthority);
        return authorities;
    }
}