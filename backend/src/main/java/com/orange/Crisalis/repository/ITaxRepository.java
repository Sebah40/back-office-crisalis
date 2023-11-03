package com.orange.Crisalis.repository;

import com.orange.Crisalis.model.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITaxRepository extends JpaRepository<Tax, Integer> {

    @Query(value = "SELECT DISTINCT * FROM tax WHERE is_active = 1", nativeQuery = true)
    List<Tax> findAllTaxes();

    @Query(value = "SELECT * FROM tax  WHERE id = :id AND is_active = 1", nativeQuery = true)
    Optional<Tax> findTaxById(@Param("id") Integer id);

    @Query(value = "SELECT * FROM tax  WHERE tax_name LIKE %:name% AND is_active = 1", nativeQuery = true)
    Optional<Tax> findTaxByTaxName(String name);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tax SET tax_name = :tax_name, tax_percentage = :tax_percentage WHERE id = :id", nativeQuery = true)
    void updateTax(@Param("id") Integer id, @Param("tax_name") String taxName, @Param("tax_percentage") Double taxPercentage);

    List<Tax> findTaxesBySellableGoodsId(Long sellableGoodId);
}
