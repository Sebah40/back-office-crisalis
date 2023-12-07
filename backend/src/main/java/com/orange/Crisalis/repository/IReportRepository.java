package com.orange.Crisalis.repository;

import com.orange.Crisalis.model.dto.ReportTotalDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface IReportRepository extends JpaRepository<ReportTotalDiscount, Long> {
    @Transactional
    @Procedure(name = "reportTotalDiscount(:from_date, :until_date)")
    List<ReportTotalDiscount> reportTotalDiscount(@Param("from_date") String fromDate, @Param("until_date") String untilDate);
}
