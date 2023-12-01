package com.orange.Crisalis.service.interfaces;

import com.orange.Crisalis.model.dto.ReportTotalDiscount;
import com.orange.Crisalis.model.dto.ReportTotalDiscountDTO;

import java.util.Date;
import java.util.List;

public interface IReportService {


    List<ReportTotalDiscountDTO> totalDiscountReport(String fromDate, String untilDate);

}
