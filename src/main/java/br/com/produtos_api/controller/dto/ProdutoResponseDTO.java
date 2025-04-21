package br.com.produtos_api.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ProdutoResponseDTO(

        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Integer quantidadeEstoque,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        OffsetDateTime dataCadastro
) {
}
