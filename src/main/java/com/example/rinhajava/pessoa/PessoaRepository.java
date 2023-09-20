package com.example.rinhajava.pessoa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

interface PessoaRepository extends JpaRepository<Pessoa, UUID> {
    @Query("SELECT p FROM Pessoa p WHERE p.apelido LIKE %:criteria% OR p.nome LIKE %:criteria% OR p.stack LIKE %:criteria% OR p.nascimento LIKE %:criteria%")
    List<Pessoa> findByCriteria(@Param("criteria") String criteria);
}
