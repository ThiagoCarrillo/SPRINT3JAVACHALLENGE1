package com.fiap.sprint.model.sinistro;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

//Classe que pega os dados de medico e faz a persistencia com o banco de dados


// dentro do<> a gente passa o tipo da entidade (qual tabela queremos "medico, paciente" etc) e o tipo da chave primaria da tabela.
public interface SinistroRepository extends JpaRepository<Sinistro, Long> {

    Page<Sinistro> findAllByAtivoTrue(Pageable paginacao);
}
