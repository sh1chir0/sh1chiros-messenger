package ua.sh1chiro.messenger.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.sh1chiro.messenger.models.Image;
import ua.sh1chiro.messenger.models.User;
import ua.sh1chiro.messenger.repositories.ImageRepository;

import java.io.IOException;

/**
 * @author sh1chiro 22.04.2023
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final UserService userService;

    public void add(MultipartFile file, Long userId) throws IOException {
        Image image = toImageEntity(file, userId);
        imageRepository.save(image);

        User user = userService.getUserById(userId);
        user.setImageId(image.getId());
        userService.updateUser(user);
    }
    public void addDefault(MultipartFile file) throws IOException {
        Image image = toImageEntity(file, 0L);
        imageRepository.save(image);
    }
    private Image toImageEntity(MultipartFile file, Long userId) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        image.setUserId(userId);
        return image;
    }
    public Image findByUserId(Long userId){
        return imageRepository.findByUserId(userId);
    }

    public void remove(Long id){
        imageRepository.deleteById(id);
    }

}
