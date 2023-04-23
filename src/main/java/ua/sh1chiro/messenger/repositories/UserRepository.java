package ua.sh1chiro.messenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.sh1chiro.messenger.models.User;

/**
 * @author sh1chiro 20.04.2023
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByNickname(String nickname);
}