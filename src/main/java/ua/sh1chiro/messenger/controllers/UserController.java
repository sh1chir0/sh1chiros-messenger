package ua.sh1chiro.messenger.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import ua.sh1chiro.messenger.models.User;
import ua.sh1chiro.messenger.services.ImageService;
import ua.sh1chiro.messenger.services.UserService;

import java.io.IOException;
import java.security.Principal;

/**
 * @author sh1chiro 20.04.2023
 */
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }
    @PostMapping("/registration")
    public String createUser(User user, Model model){
        if(userService.createUser(user)){
            return ("redirect:/login");
        }else{
            return ("redirect:/registration");
        }
    }

    @GetMapping("/settings")
    public String settingsTemplate(Principal principal, Model model){
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("contacts", user.getContacts());
        return "settings";
    }
    @PostMapping("/settings")
    public String updateUser(@RequestParam("file1") MultipartFile file1, User user, Principal principal) throws IOException {
        User userBeforeUpdate = userService.getUserByPrincipal(principal);
        if(!user.getNickname().equals(userBeforeUpdate.getNickname()) && userService.getUserByNickname(user.getNickname()) != null){
            return ("redirect:/settings");
        }
        if(!user.getEmail().equals(userBeforeUpdate.getEmail()) && userService.getUserByEmail(user.getEmail()) != null){
            return ("redirect:/settings");
        }
        user.setId(userBeforeUpdate.getId());
        user.setPassword(userBeforeUpdate.getPassword());
        user.setActive(userBeforeUpdate.isActive());
        user.setDateOfCreated(userBeforeUpdate.getDateOfCreated());
        user.setImageId(userBeforeUpdate.getImageId());
        userService.updateUser(user);
        if(!file1.isEmpty()){
            if(userBeforeUpdate.getImageId() == 3){
                imageService.add(file1, userService.getUserByPrincipal(principal).getId());
            }
            else {
                imageService.remove(userBeforeUpdate.getImageId());
                imageService.add(file1, userService.getUserByPrincipal(principal).getId());
            }
        }

        if(!user.getEmail().equals(userBeforeUpdate.getEmail())){
            return ("redirect:/login");
        }
        return ("redirect:/settings");
    }
//    @PostMapping("/settings")
//    public String addDefaultPhoto(@RequestParam("file1") MultipartFile file1) throws IOException {
//        imageService.addDefault(file1);
//        return ("redirect:/settings");
//    }
    @PostMapping("/update-password")
    public String updatePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 Principal principal, Model model){
        User user = userService.getUserByPrincipal(principal);
        if(passwordEncoder.matches(oldPassword, user.getPassword())){
            user.setPassword(passwordEncoder.encode(newPassword));
            userService.updateUser(user);
        }
        return ("redirect:/settings");
    }
}
