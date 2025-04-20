package br.com.produtos_api.controller.dto;

import java.time.OffsetDateTime;

public record ProdutoRequestDTO(

        Long id,
        String nome,
        String descricao,
        Double preco,
        Double quantidadeEstoque,
        OffsetDateTime dataCadastro
) {
}
