package ua.sh1chiro.messenger.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import ua.sh1chiro.messenger.models.User;
import ua.sh1chiro.messenger.services.ChatService;
import ua.sh1chiro.messenger.services.UserService;

import java.security.Principal;

/**
 * @author sh1chiro 21.04.2023
 */
@Controller
@RequiredArgsConstructor
public class ContactController {
    private final ChatService chatService;
    private final UserService userService;
    @PostMapping("/contact")
    public String createChat(String nickname, Principal principal){
        User firstUser = userService.getUserByPrincipal(principal);
        User secondUser = userService.getUserByNickname(nickname);
        if(secondUser != null){
            chatService.createChat(firstUser, secondUser);
        }
        return ("redirect:/");
    }
}
