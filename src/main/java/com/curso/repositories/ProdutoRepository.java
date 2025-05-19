package com.curso.repositories;

import com.curso.domains.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
 Optional<Produto> findByCodigoBarra(String codigoBarra);

}
