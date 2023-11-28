package com.orange.Crisalis.service;

import com.orange.Crisalis.model.dto.ReportTotalDiscount;
import com.orange.Crisalis.repository.IReportRepository;
import com.orange.Crisalis.service.interfaces.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ReportServiceImpl implements IReportService {

    @Autowired
    private IReportRepository reportRepository;

    @Override
    public List<ReportTotalDiscount> totalDiscountReport(String fromDate, String untilDate) {
        List<ReportTotalDiscount> reports = reportRepository.reportTotalDiscount(fromDate, untilDate);
        return reports;
    }
}
