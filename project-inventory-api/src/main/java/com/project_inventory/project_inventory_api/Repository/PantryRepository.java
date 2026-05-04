package com.project_inventory.project_inventory_api.Repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project_inventory.project_inventory_api.Model.Pantry;

public interface PantryRepository extends JpaRepository<Pantry, Long> {

    @Query("SELECT p FROM Pantry p WHERE p.expiration_date < :date")
    List<Pantry> findByBestBeforeDate(@Param("date") Date date);
}