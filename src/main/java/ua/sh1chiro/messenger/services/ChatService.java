package ua.sh1chiro.messenger.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.sh1chiro.messenger.models.Chat;

import ua.sh1chiro.messenger.models.User;
import ua.sh1chiro.messenger.repositories.ChatRepository;


/**
 * @author sh1chiro 21.04.2023
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserService userService;
    public void createChat(User firstUser, User secondUser){
        Chat chat = new Chat();
        chat.setFirstUser(firstUser);
        chat.setSecondUser(secondUser);
        chatRepository.save(chat);

        firstUser.addContact(secondUser, chat.getId());
        secondUser.addContact(firstUser, chat.getId());
        userService.updateUser(firstUser);
        userService.updateUser(secondUser);
    }
    public Chat getChatById(Long id){
        return chatRepository.findById(id).orElse(null);
    }
}
