package ua.sh1chiro.messenger.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.sh1chiro.messenger.models.Chat;
import ua.sh1chiro.messenger.models.ChatHistory;
import ua.sh1chiro.messenger.models.ChatMessage;
import ua.sh1chiro.messenger.models.User;
import ua.sh1chiro.messenger.services.ChatHistoryService;
import ua.sh1chiro.messenger.services.ChatService;
import ua.sh1chiro.messenger.services.UserService;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;

/**
 * @author sh1chiro 20.04.2023
 */
@Controller
@RequiredArgsConstructor
public class ChatController {
    private final UserService userService;
    private final ChatService chatService;
    private final ChatHistoryService chatHistoryService;
    @GetMapping("/")
    public String index(Model model, Principal principal){
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("contacts", user.getContacts());
        return "chat";
    }

    @GetMapping("/chat/{id}")
    public String chat(@PathVariable Long id, Model model, Principal principal){
        Chat chat = chatService.getChatById(id);
        if(chat != null){
            model.addAttribute("chat", chat);
        }else{
            return ("redirect:/");
        }
        User me = userService.getUserByPrincipal(principal);
        model.addAttribute("me", me);
        if(chat.getFirstUser() == me || chat.getSecondUser() == me){
            List<ChatHistory> chatHistory = chatHistoryService.getHistoryByChatId(id);
            if (chatHistory.size() > 0) {
                chatHistory.sort(Comparator.comparing(ChatHistory::getDateOfCreated));
                model.addAttribute("history", chatHistory);
                System.out.println(chatHistory.get(0));
            }


            model.addAttribute("contacts", me.getContacts());
            User interlocutor;
            if(chat.getFirstUser() == me){
                interlocutor = chat.getSecondUser();
            }else{
                interlocutor = chat.getFirstUser();
            }
            model.addAttribute("interlocutor", interlocutor);
            model.addAttribute("chatHistory", chatHistoryService.getHistoryByChatId(id));
            return "chatWithInterlocutor";
        }else{

            return ("redirect:/");
        }
    }

    @GetMapping("/chat")
    public String chatTest(Model model, Principal principal){
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "testChat";
    }
    @MessageMapping("/chat.sendMessage/{id}")
    @SendTo("/topic/chat/{id}")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        chatHistoryService.save(chatMessage);
        return chatMessage;
    }
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
}
