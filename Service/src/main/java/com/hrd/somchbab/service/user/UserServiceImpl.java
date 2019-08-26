package com.hrd.somchbab.service.user;

import com.hrd.somchbab.repository.UserDetailsRepository;
import com.hrd.somchbab.repository.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// DON'T TOUCH THIS CLASS !!!!

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserDetailsRepository currentUserRepository;

    @Autowired
    public UserServiceImpl(UserDetailsRepository currentUserRepository) {
        this.currentUserRepository = currentUserRepository;
    }

    @Override
    public User getUserByEmail(String email) {
        LOGGER.debug("Getting user by email={}", email.replaceFirst("@.*", "@***"));
        return currentUserRepository.loadUserByEmail(email);
    }

}
