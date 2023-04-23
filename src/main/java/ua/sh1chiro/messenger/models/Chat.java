package ua.sh1chiro.messenger.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author sh1chiro 21.04.2023
 */
@Entity
@Data
@Table(name = "chats")
@AllArgsConstructor
@NoArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "first_user_id", referencedColumnName = "id")
    private User firstUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "second_user_id", referencedColumnName = "id")
    private User secondUser;
    @Column(name = "date_of_created")
    private LocalDateTime dateOfCreated;

    @PrePersist
    private void init(){
        dateOfCreated = LocalDateTime.now();
    }
}
