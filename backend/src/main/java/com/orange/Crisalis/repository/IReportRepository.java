package com.orange.Crisalis.repository;

import com.orange.Crisalis.model.dto.ReportTotalDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface IReportRepository extends JpaRepository<ReportTotalDiscount, Long> {
    @Transactional
    @Procedure(name = "reportTotalDiscount(:fromDate, :untilDate)")
    List<ReportTotalDiscount> reportTotalDiscount(@Param("fromDate") String fromDate, @Param("untilDate") String untilDate);
}
