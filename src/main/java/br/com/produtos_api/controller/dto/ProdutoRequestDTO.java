package br.com.produtos_api.controller.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProdutoRequestDTO(

        @NotBlank(message = "O nome do produto é obrigatório")
        @Size(min = 4, max = 32, message = "O nome deve ter entre 4 e 32 caracteres")
        String nome,

        @NotBlank(message = "A descrição do produto é obrigatória")
        @Size(min = 10, max = 255, message = "A descrição deve ter entre 4 e 255 caracteres")
        String descricao,

        @NotNull(message = "O preço é obrigatório")
        @DecimalMin(value = "0.01", inclusive = true, message = "O preço deve ser maior que zero")
        BigDecimal preco,

        @NotNull(message = "A quantidade em estoque é obrigatória")
        @Min(value = 0, message = "A quantidade deve ser no mínimo 0")
        @Max(value = 9999, message = "A quantidade deve ser no máximo 9999")
        Integer quantidadeEstoque
) {
}
