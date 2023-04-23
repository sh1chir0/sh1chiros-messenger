package ua.sh1chiro.messenger.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.sh1chiro.messenger.models.User;
import ua.sh1chiro.messenger.repositories.UserRepository;
import ua.sh1chiro.messenger.models.enums.Role;

import java.security.Principal;
import java.util.List;
import java.util.Set;

/**
 * @author sh1chiro 20.04.2023
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> allUsers(){
        return userRepository.findAll();
    }
    public void ban(Long id){
        User user = getUserById(id);
        if(user.isActive()){
            user.setActive(false);
            updateUser(user);
        }else{
            user.setActive(true);
            updateUser(user);
        }
    }
    public void changeRole(Long id){
        User user = getUserById(id);
        Set<Role> role = user.getRoles();
        if(user.isAdmin()){
            role.clear();
            role.add(Role.ROLE_USER);
            updateUser(user);
        }
        else{
            role.clear();
            role.add(Role.ROLE_ADMIN);
            updateUser(user);
        }
    }
    public boolean createUser(User user){
        if(userRepository.findByEmail(user.getEmail()) != null) return false;
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setImageId(3L);
        if(user.getEmail().equals("pavel.dereyes@gmail.com")){
            user.getRoles().add(Role.ROLE_ADMIN);
        }else{
            user.getRoles().add(Role.ROLE_USER);
        }
        log.info("Saving new user with email {}", user.getEmail());
        userRepository.save(user);
        return true;
    }
    public User getUserByPrincipal(Principal principal){
        if(principal == null){
            return new User();
        }
        else{
            return userRepository.findByEmail(principal.getName());

        }
    }
    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }
    public boolean updateUser(User user){
        if(user != null){
            userRepository.save(user);
            return true;
        }
        else
            return false;
    }
    public User getUserByNickname(String nickname){
        return userRepository.findByNickname(nickname);
    }
}
