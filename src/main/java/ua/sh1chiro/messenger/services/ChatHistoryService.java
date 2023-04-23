package ua.sh1chiro.messenger.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.sh1chiro.messenger.models.ChatHistory;
import ua.sh1chiro.messenger.models.ChatMessage;
import ua.sh1chiro.messenger.repositories.ChatHistoryRepository;

import java.util.List;

/**
 * @author sh1chiro 21.04.2023
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatHistoryService {
    private final ChatHistoryRepository chatHistoryRepository;
    private final ChatService chatService;
    private final UserService userService;
    public List<ChatHistory> getHistoryByChatId(Long id){
        return chatHistoryRepository.getHistoryByChatId(id);
    }
    public void save(ChatMessage chatMessage){
        ChatHistory chatHistory = new ChatHistory();
        chatHistory.setChat(chatService.getChatById(chatMessage.getChatId()));
        System.out.println(chatMessage.getChatId());
        chatHistory.setUser(userService.getUserByNickname(chatMessage.getSender()));
        chatHistory.setMessage(chatMessage.getContent());
        chatHistoryRepository.save(chatHistory);
    }
}
