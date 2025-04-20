package br.com.produtos_api.business.services;

import br.com.produtos_api.controller.dto.ProdutoCreateRequestDTO;
import br.com.produtos_api.controller.dto.ProdutoRequestDTO;

import br.com.produtos_api.infrastructure.exceptions.InvalidValueException;
import br.com.produtos_api.infrastructure.exceptions.ListException;
import br.com.produtos_api.infrastructure.exceptions.ProductNotFound;
import br.com.produtos_api.infrastructure.exceptions.ServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final List<ProdutoRequestDTO> listaProdutos = new ArrayList<>();
    private Long nextId = 1L;

    public ProdutoRequestDTO addProdutos(ProdutoCreateRequestDTO produto) {

        if (produto == null) {

            throw new ServerException("Produto não pode ser nulo.");
        }

        if (produto.preco() <= 0 || produto.quantidadeEstoque() < 0) {

            throw new InvalidValueException("Valor negativo. Valor inválido.");
        }

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

        if (listaProdutos.isEmpty()) {

            throw new ListException("Nenhum produto cadastrado.");
        }

        return listaProdutos;
    }

    public ProdutoRequestDTO getProdutoById(Long id) {

        return listaProdutos.stream().filter(
                produto -> produto.id().equals(id)
        ).findFirst().orElse(null);
    }

    public void updateProduto(Long id, ProdutoCreateRequestDTO produto) {

        if (produto.preco() <= 0 || produto.quantidadeEstoque() < 0) {

            throw new InvalidValueException("Atualização inválida.");
        }

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

        throw new ProductNotFound("Produto não encontrado. ID: " + id);
    }

    public void removeProduto(Long id) {
        boolean removido = listaProdutos.removeIf(produto -> produto.id().equals(id));

        if (!removido) {

            throw new ProductNotFound("Produto não encontrado. ID: " + id);
        }
    }
}
