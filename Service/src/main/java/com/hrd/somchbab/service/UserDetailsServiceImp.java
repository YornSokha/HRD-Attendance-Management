package com.hrd.somchbab.service;

import com.hrd.somchbab.repository.UserDetailsRepository;
import com.hrd.somchbab.repository.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

//    @Autowired
//    UserDetailsRepository userDetailsRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        User user = userDetailsRepository.loadUserByEmail(s);
//        System.out.println(user);
//        return user == null ? new User() : user;
//    }

    @Autowired
    private UserDetailsRepository userDetailsRepo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println(userDetailsRepo.loadUserByEmail(s));
        User user = userDetailsRepo.loadUserByEmail(s);
        return user == null ? new User() : user;
    }
}
