package ua.sh1chiro.messenger.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sh1chiro 23.04.2023
 */
@Entity
@Data
@Table(name="contacts")
@AllArgsConstructor
@NoArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne
    private User user;
    @ManyToOne
    private User contact;

    @Column(name = "chat_id")
    private Long chatId;

    public Contact(User user, User contact, Long chatId) {
        this.user = user;
        this.contact = contact;
        this.chatId = chatId;
    }
}
