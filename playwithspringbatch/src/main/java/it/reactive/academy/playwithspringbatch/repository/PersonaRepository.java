package it.reactive.academy.playwithspringbatch.repository;

import it.reactive.academy.playwithspringbatch.entity.PersonaModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonaRepository extends JpaRepository<PersonaModel, Integer> {


    Page<PersonaModel> findByNome(String nome, Pageable pageable);
}
