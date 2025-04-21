package br.com.produtos_api.controller.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProdutoRequestDTO(
        @NotBlank(message = "O nome do produto é obrigatório")
        String nome,

        @NotBlank(message = "A descrição do produto é obrigatória")
        String descricao,

        @NotNull(message = "O preço do produto é obrigatório")
        @DecimalMin(value = "1.00", message = "O preço deve ser maior que zero")
        BigDecimal preco,

        @NotNull(message = "A quantidade em estoque é obrigatória")
        @Min(value = 0, message = "A quantidade em estoque deve ser maior ou igual a zero")
        @Max(value = 9999, message = "A quantidade em estoque deve ser menor ou igual a 10.000")
        Integer quantidadeEstoque
) {
}
