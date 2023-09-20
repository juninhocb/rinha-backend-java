package com.example.rinhajava.pessoa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.util.List;

@Builder
record PessoaDto(

        @NotBlank
        @Pattern(regexp = "^[a-zA-ZÀ-ÖØ-öø-ÿ\\s]+$")
        String nome,
        @NotBlank
        @Pattern(regexp = "^[a-zA-ZÀ-ÖØ-öø-ÿ\\s]+$")
        String apelido,

        @NotBlank
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
        String nascimento,
        List<String> stack) {
}
