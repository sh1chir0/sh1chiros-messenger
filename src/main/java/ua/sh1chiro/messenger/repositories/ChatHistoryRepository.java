package ua.sh1chiro.messenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.sh1chiro.messenger.models.ChatHistory;

import java.util.List;

/**
 * @author sh1chiro 21.04.2023
 */
public interface ChatHistoryRepository extends JpaRepository<ChatHistory,Long> {
    List<ChatHistory> getHistoryByChatId(Long chatId);
}
