package br.com.produtos_api.controller;

import br.com.produtos_api.business.services.ProdutoService;
import br.com.produtos_api.controller.dto.ProdutoRequestDTO;
import br.com.produtos_api.controller.dto.ProdutoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    @Operation(summary = "Adicionar um novo produto")
    public ResponseEntity<ProdutoResponseDTO> createProduto(@Valid @RequestBody ProdutoRequestDTO produto) {

        ProdutoResponseDTO criado = produtoService.createProduto(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping
    @Operation(summary = "Listar todos os produtos")
    public ResponseEntity<List<ProdutoResponseDTO>> getAllProdutos() {

        List<ProdutoResponseDTO> produtos = produtoService.findAllProdutos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID")
    public ResponseEntity<ProdutoResponseDTO> getProdutoById(@PathVariable UUID id) {

        ProdutoResponseDTO produto = produtoService.findById(id);
        if (produto != null) {
            return ResponseEntity.ok(produto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto por ID")
    public ResponseEntity<Void> updateProduto(@PathVariable UUID id, @Valid @RequestBody ProdutoRequestDTO produtoAtualizado) {

        try {
            produtoService.updateProduto(id, produtoAtualizado);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover produto por ID")
    public ResponseEntity<Void> deleteProduto(@PathVariable UUID id) {

        try {
            produtoService.deleteProduto(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
