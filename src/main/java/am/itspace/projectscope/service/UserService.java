package am.itspace.projectscope.service;

import am.itspace.projectscope.model.User;
import am.itspace.projectscope.model.UserType;
import am.itspace.projectscope.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public Optional<User> getUserByEmail(String email){
        Optional<User> byEmail = userRepository.findByEmail(email);
        return byEmail;

    }

    public List<User> findByUsertype(UserType userType){
       return userRepository.findAllByUserType(userType);
    }


}
