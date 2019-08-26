package com.hrd.somchbab.service.user;


import com.hrd.somchbab.repository.model.User;

import java.util.Collection;
import java.util.Optional;

// DON'T TOUCH THIS INTERFACE !!!!
public interface UserService { ;

    User getUserByEmail(String email);

}
