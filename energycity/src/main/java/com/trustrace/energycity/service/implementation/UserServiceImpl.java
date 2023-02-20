package com.trustrace.energycity.service.implementation;
import com.trustrace.energycity.pojo.User;
import com.trustrace.energycity.repository.UserRepository;
import com.trustrace.energycity.service.UserService;
import com.trustrace.energycity.pojo.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User createUser(User user) {
        if (userRepository.findByEmailId (user.getEmailId ())!=null){
            throw new RuntimeException ("Email Id already exists");
        }
        user.setStatus ("Active");
        return userRepository.save (user);
    }

    @Override
    public User login(UserLogin userLogin) {
        User user=userRepository.findByEmailId (userLogin.getEmailId ());
        if (user==null){
            throw new RuntimeException ("Invalid Id");
        }else if (!userLogin.getPassword ().equals (user.getPassword())){
            throw new RuntimeException ("Wrong Password");
        }
        return user;
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll ();
    }
}


