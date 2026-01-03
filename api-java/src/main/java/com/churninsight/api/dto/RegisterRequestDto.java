package com.churninsight.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(
        @NotBlank @Email
        String email,

        @NotBlank
        @Size(min = 6, max = 72)
        String password,

        @NotBlank
        @Size(min = 2, max = 80)
        String name
) {}
