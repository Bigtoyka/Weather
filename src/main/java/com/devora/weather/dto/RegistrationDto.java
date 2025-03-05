package com.devora.weather.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDto {
    @NotBlank(message = "Username обязателен")
    @Pattern(regexp = "^\\S+$", message = "Username dont have space")
    private String username;

    @NotBlank(message = "Password обязателен")
    private String password;

    @NotBlank(message = "Подтверждение пароля обязательно")
    private String confirmPassword;
}
