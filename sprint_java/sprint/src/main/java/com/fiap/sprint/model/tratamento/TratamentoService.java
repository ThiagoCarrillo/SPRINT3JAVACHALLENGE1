package com.fiap.sprint.model.tratamento;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TratamentoService {

    @Autowired
    private TratamentoRepository repository;

    @Transactional
    public Tratamento cadastrar(TratamentoDTO dados) {
        var tratamento = new Tratamento(dados);
        return repository.save(tratamento);
    }

    public Page<Tratamento> listar(Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao);
    }

    @Transactional
    public Tratamento atualizar(AttTratamentoDTO dados) {
        var tratamento = repository.findById(dados.id())
                .orElseThrow(() -> new EntityNotFoundException("Tratamento não encontrado"));
        tratamento.atualizarInformacoes(dados);
        return tratamento;
    }

    @Transactional
    public void excluir(Long id) {
        var tratamento = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tratamento não encontrado"));
        tratamento.excluir(); // Marca como excluído
        repository.save(tratamento); // Salva a alteração no banco de dados
    }

    public Tratamento detalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tratamento não encontrado"));
    }
}