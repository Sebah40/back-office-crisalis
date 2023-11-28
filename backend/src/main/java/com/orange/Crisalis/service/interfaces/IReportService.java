package com.orange.Crisalis.service.interfaces;

import com.orange.Crisalis.model.dto.ReportTotalDiscount;

import java.util.Date;
import java.util.List;

public interface IReportService {

    List<ReportTotalDiscount> totalDiscountReport(String fromDate, String untilDate);

}
