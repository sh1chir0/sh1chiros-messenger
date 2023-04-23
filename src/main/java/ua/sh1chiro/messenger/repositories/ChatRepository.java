package ua.sh1chiro.messenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.sh1chiro.messenger.models.Chat;


/**
 * @author sh1chiro 21.04.2023
 */
public interface ChatRepository extends JpaRepository<Chat,Long> {
}
