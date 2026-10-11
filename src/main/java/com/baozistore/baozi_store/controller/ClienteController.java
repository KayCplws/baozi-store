
package com.baozistore.baozi_store.controller;

import com.baozistore.baozi_store.model.Cliente;
import com.baozistore.baozi_store.repository.ClienteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @PostMapping
    public Cliente cadastrar(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @GetMapping
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    @GetMapping("/{id}")
    public Cliente buscarPorId(@PathVariable Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    @PutMapping("/{id}")
    public Cliente atualizar(@PathVariable Long id, @RequestBody Cliente dados) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        cliente.setNome(dados.getNome());
        cliente.setEmail(dados.getEmail());

        return clienteRepository.save(cliente);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        clienteRepository.deleteById(id);
    }
}
