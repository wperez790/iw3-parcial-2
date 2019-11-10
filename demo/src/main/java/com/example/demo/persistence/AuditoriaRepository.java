package com.example.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Auditoria;

@Repository
public interface AuditoriaRepository  extends JpaRepository<Auditoria, Integer> {

}
