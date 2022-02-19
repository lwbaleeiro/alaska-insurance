package br.com.alaska.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Sex {

    MALE(0, "Male"),
    FEMALE(1, "Female");

    private Integer code;
    private String description;
}
