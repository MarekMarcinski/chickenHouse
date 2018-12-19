package org.marcinski.chickenHouse.repository;

import org.marcinski.chickenHouse.entity.Slaughter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SlaughterRepository extends JpaRepository<Slaughter, Long> {
    List<Slaughter> findAllByCycleId(Long cycleId);
}
