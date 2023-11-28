package com.orange.Crisalis.repository;

import com.orange.Crisalis.model.FilteredReportEntity;
import com.orange.Crisalis.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository

public interface FilteredReportRepository extends JpaRepository<FilteredReportEntity, Long> {
    List<FilteredReportEntity> findByOrderDateBetween(Date startDate, Date endDate);
}
