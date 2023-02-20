package com.trustrace.energycity.service;
import org.springframework.stereotype.Service;
import com.trustrace.energycity.pojo.User;
import com.trustrace.energycity.pojo.UserLogin;
import java.util.List;

@Service
public interface UserService {
    User createUser(User user) ;

    User login(UserLogin userLogin);

    List<User> getAllUser();
}

