package br.com.produtos_api.business.services;

import br.com.produtos_api.controller.dto.ProdutoCreateRequestDTO;
import br.com.produtos_api.controller.dto.ProdutoRequestDTO;
import br.com.produtos_api.model.Produto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final List<ProdutoRequestDTO> listaProdutos = new ArrayList<>();
    private Long nextId = 1L;

    public ProdutoRequestDTO addProdutos(ProdutoCreateRequestDTO produto) {
        ProdutoRequestDTO novoProduto = new ProdutoRequestDTO(
                nextId++,
                produto.nome(),
                produto.descricao(),
                produto.preco(),
                produto.quantidadeEstoque(),
                OffsetDateTime.now()
        );

        listaProdutos.add(novoProduto);
        return novoProduto;
    }

    public List<ProdutoRequestDTO> getAllProdutos() {
        return listaProdutos;
    }

    public ProdutoRequestDTO getProdutoById(Long id) {
        return listaProdutos.stream().filter(
                produto -> produto.id().equals(id)
        ).findFirst().orElse(null);
    }

    public void updateProduto(Long id, ProdutoCreateRequestDTO produto) {

        for (int i = 0; i < listaProdutos.size(); i++) {
            ProdutoRequestDTO atual = listaProdutos.get(i);
            if (atual.id().equals(id)) {
                ProdutoRequestDTO atualizado = new ProdutoRequestDTO(
                        id,
                        produto.nome(),
                        produto.descricao(),
                        produto.preco(),
                        produto.quantidadeEstoque(),
                        atual.dataCadastro()
                );
                listaProdutos.set(i, atualizado);
                return;
            }
        }

        throw new RuntimeException("Produto não encontrado. ID: " + id);
    }

    public void removeProduto(Long id) {
        boolean removido = listaProdutos.removeIf(produto -> produto.id().equals(id));

        if (!removido) {
            throw new RuntimeException("Produto não encontrado. ID: " + id);
        }
    }


}
