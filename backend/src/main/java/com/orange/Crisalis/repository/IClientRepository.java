package com.orange.Crisalis.repository;

import com.orange.Crisalis.model.ClientEntity;
import com.orange.Crisalis.model.dto.ReportTotalDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface IClientRepository extends JpaRepository <ClientEntity, Integer>{
    @Query
            (value = "SELECT * FROM client_entity c " +
                    "WHERE c.id LIKE %"+":query"+"% OR c.dni LIKE %"+":query"+"% OR c.business_name LIKE %"+":query"+"% OR c.cuit LIKE %"+":query"+"% OR c.dni_responsible LIKE %"+":query"+"% " +
                    "OR c.first_name LIKE %"+":query"+"% OR c.last_name LIKE %"+":query"+"% OR c.first_name_responsible LIKE %"+":query"+"% OR c.last_name_responsible LIKE %"+":query"+"% ", nativeQuery = true)
    List<ClientEntity> searchByAnyParameter(@Param("query") String query);



    boolean existsById(int id);

    @Override
    List<ClientEntity> findAll();
}