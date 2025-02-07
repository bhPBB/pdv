package com.brunobomfim.pdv.repositories;

import com.brunobomfim.pdv.models.VendaItem;
import com.brunobomfim.pdv.models.VendaItemPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaItemRepository extends JpaRepository<VendaItem, VendaItemPk> {
}
