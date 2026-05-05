package com.project_inventory.project_inventory_api.Repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project_inventory.project_inventory_api.Model.Freezer;

public interface FreezerRepository extends JpaRepository<Freezer, Long> {

    List<Freezer> findByName(String name);

    @Query("SELECT p FROM Freezer p WHERE p.expiration_date < :date")
    List<Freezer> findByBestBeforeDate(@Param("date") Date date);
}
