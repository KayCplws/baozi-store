
package com.baozistore.baozi_store.controller;

import com.baozistore.baozi_store.model.Cliente;
import com.baozistore.baozi_store.model.Pedido;
import com.baozistore.baozi_store.model.Produto;
import com.baozistore.baozi_store.repository.ClienteRepository;
import com.baozistore.baozi_store.repository.PedidoRepository;
import com.baozistore.baozi_store.repository.ProdutoRepository;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoController(
            PedidoRepository pedidoRepository,
            ClienteRepository clienteRepository,
            ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido criar(@Valid @RequestBody Pedido dados) {

        Cliente cliente = clienteRepository.findById(
                dados.getCliente().getId()
        ).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        Produto produto = produtoRepository.findById(
                dados.getProduto().getId()
        ).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Produto não encontrado"));

        Pedido pedido = new Pedido(
                cliente,
                produto,
                dados.getQuantidade()
        );

        return pedidoRepository.save(pedido);
    }

    @GetMapping
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Pedido buscarPorId(@PathVariable Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Pedido não encontrado"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Pedido não encontrado");
        }

        pedidoRepository.deleteById(id);
    }
}
