package ua.sh1chiro.messenger.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import ua.sh1chiro.messenger.models.Contact;
import ua.sh1chiro.messenger.models.User;
import ua.sh1chiro.messenger.services.ChatService;
import ua.sh1chiro.messenger.services.UserService;

import java.security.Principal;
import java.util.List;

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
            List<Contact> contacts = firstUser.getContacts();
            for (int i = 0; i < contacts.size(); i++) {
                if(contacts.get(i).getContact() == secondUser){
                    return ("redirect:/");
                }
            }
            chatService.createChat(firstUser, secondUser);
        }
        return ("redirect:/");
    }
}
