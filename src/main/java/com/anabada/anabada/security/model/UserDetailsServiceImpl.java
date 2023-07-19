package com.anabada.anabada.security.model;

import com.anabada.anabada.security.model.entity.User;
import com.anabada.anabada.security.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(userid).orElseThrow(
                () -> new UsernameNotFoundException("Not Found " + userid));

        return new UserDetailsImpl(user);
    }
}
