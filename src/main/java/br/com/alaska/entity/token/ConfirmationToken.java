package br.com.alaska.entity.token;

import br.com.alaska.entity.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.AUTO;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class ConfirmationToken {

    @Id
    @SequenceGenerator(name = "CONFIRMATION_TOKEN_SEQUENCE", sequenceName = "confirmation_token_sequence_id", allocationSize = 1)
    @GeneratedValue(strategy = AUTO, generator = "CONFIRMATION_TOKEN_SEQUENCE")
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;
    @ManyToOne
    @JoinColumn(nullable = false, name = "users_id")
    private User user;

    public ConfirmationToken(String token,
                             LocalDateTime createdAt,
                             LocalDateTime expiresAt,
                             User user) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.user = user;
    }
}
