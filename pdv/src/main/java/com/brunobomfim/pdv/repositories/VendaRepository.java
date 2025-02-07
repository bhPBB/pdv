package com.brunobomfim.pdv.repositories;

import com.brunobomfim.pdv.models.Venda;
import com.brunobomfim.pdv.models.VendaPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, VendaPk> {
    
    List<Venda> findByIdUsuarioEmail(String usuarioEmail);

    @Query("SELECT COALESCE(MAX(v.id.codigo), 0) + 1 FROM Venda v WHERE v.id.usuarioEmail = :email")
    Long getNextVendaCodigo(@Param("email") String usuarioEmail);

}
