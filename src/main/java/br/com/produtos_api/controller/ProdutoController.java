package br.com.produtos_api.controller;

import br.com.produtos_api.business.services.ProdutoService;
import br.com.produtos_api.controller.dto.ProdutoCreateRequestDTO;
import br.com.produtos_api.controller.dto.ProdutoRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    @Operation(summary = "Adicionar um novo produto")
    public ResponseEntity<ProdutoRequestDTO> addProduto(@RequestBody @Valid ProdutoCreateRequestDTO produto) {

        ProdutoRequestDTO criado = produtoService.addProdutos(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping
    @Operation(summary = "Listar todos os produtos")
    public ResponseEntity<List<ProdutoRequestDTO>> getAllProducts() {

        List<ProdutoRequestDTO> produtos = produtoService.getAllProdutos();
        return ResponseEntity.ok(produtos);

    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID")
    public ResponseEntity<ProdutoRequestDTO> getProdutcsById(@PathVariable Long id) {

        ProdutoRequestDTO produto = produtoService.getProdutoById(id);
        if (produto != null) {
            return ResponseEntity.ok(produto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto por ID")
    public ResponseEntity<Void> updateProduct(@PathVariable Long id, @RequestBody ProdutoCreateRequestDTO produtoAtualizado) {

        try {
            produtoService.updateProduto(id, produtoAtualizado);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover produto por ID")
    public ResponseEntity<Void> removeProduct(@PathVariable Long id) {

        try {
            produtoService.removeProduto(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
