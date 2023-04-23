package ua.sh1chiro.messenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.sh1chiro.messenger.models.Image;


/**
 * @author sh1chiro 22.04.2023
 */
public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByUserId(Long userId);
}
