package br.com.alaska.controllers.user.response;

import br.com.alaska.entity.user.Sex;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserResponse(Long id,
                           String name,
                           String cpf,
                           Sex sex,
                           @JsonDeserialize(using = LocalDateDeserializer.class)
                           @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
                           LocalDate dateOfBirth,
                           String email,
                           String cellphone) {

}
