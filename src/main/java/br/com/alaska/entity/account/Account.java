package br.com.alaska.entity.account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Account {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

}
