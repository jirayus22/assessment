package com.kbtg.bootcamp.posttest.service;
import com.google.gson.Gson;
import com.kbtg.bootcamp.posttest.entity.UsersEntity;
import com.kbtg.bootcamp.posttest.repository.UsersRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class AuthenticationUserDetailService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final JwtService jwtService;
    public AuthenticationUserDetailService(UsersRepository usersRepository, JwtService jwtService) {
        this.usersRepository = usersRepository;
        this.jwtService = jwtService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByUsername(username);
    }

    public String generateJwt(String username){
        UsersEntity usersEntity = usersRepository.findByUsername(username);
        return jwtService.generateToken(usersEntity);
    }

}


