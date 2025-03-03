package am.itspace.projectscope.security;



import am.itspace.projectscope.model.User;
import am.itspace.projectscope.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User byUsername = userRepository.findByEmail(s).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CurrentUser(byUsername);

    }
}
