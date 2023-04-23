package kz.sportify.gateway.security.services;

import kz.sportify.gateway.models.Users;
import kz.sportify.gateway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Users user = userRepository.findByUsername(username).orElseThrow();
//        .orElseThrow(() -> new BadRequestException("User Not Found with username: " + username));

    return UserDetailsImpl.build(user);
  }

}
