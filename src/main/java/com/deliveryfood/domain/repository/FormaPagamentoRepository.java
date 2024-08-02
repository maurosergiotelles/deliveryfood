package com.deliveryfood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deliveryfood.domain.model.FormaPagamento;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

}
