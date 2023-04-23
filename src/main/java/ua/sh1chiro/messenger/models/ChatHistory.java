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
@Table(name = "chats_history")
@AllArgsConstructor
@NoArgsConstructor
public class ChatHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    private Chat chat;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @Column(name = "message", length = 1000)
    private String message;
    @Column(name = "dateOfCreated")
    private LocalDateTime dateOfCreated;

    @PrePersist
    private void init(){
        dateOfCreated = LocalDateTime.now();
    }
}
