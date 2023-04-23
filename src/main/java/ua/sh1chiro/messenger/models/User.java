package ua.sh1chiro.messenger.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.sh1chiro.messenger.models.enums.Role;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author sh1chiro 20.04.2023
 */
@Entity
@Data
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name="phoneNumber")
    private String phoneNumber;
    @Column(name = "name")
    private String name;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "country")
    private String country;
    @Column(name="active")
    private boolean active;
    @Column(name="status")
    private String status;
    @Column(name="instagram")
    private String instagram;
    @Column(name="facebook")
    private String facebook;
    @Column(name="linkedin")
    private String linkedin;
    @Column(name="twitter")
    private String twitter;
    @Column(name="discord")
    private String discord;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Contact> contacts = new ArrayList<>();

    public void addContact(User user_contact, Long chatId) {
        Contact contact = new Contact(this, user_contact, chatId);
        contacts.add(contact);
    }

    @Column(name="password", length = 1000)
    private String password;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name="user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();
    private LocalDateTime dateOfCreated;
    @Column(name="image_id")
    private Long imageId;
    @PrePersist
    private void init(){
        dateOfCreated = LocalDateTime.now();
    }

    public boolean isAdmin(){
        if(roles.contains(Role.ROLE_ADMIN)){
            return true;
        }

        return false;
    }

    //security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", name='" + name + '\'' +
                ", active=" + active +
                ", roles=" + roles +
                ", dateOfCreated=" + dateOfCreated +
                '}';
    }
}






