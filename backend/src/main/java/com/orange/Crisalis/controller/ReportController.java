package com.orange.Crisalis.controller;

import com.orange.Crisalis.model.dto.ReportTotalDiscount;
import com.orange.Crisalis.model.dto.ReportTotalDiscountDTO;
import com.orange.Crisalis.service.interfaces.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private IReportService reportService;

    @GetMapping("/total-discount")
    @Transactional(readOnly = false)
    public List<ReportTotalDiscountDTO> getTotalDiscountPerClient(@RequestParam("fromDate") String fromDate,
                                                                  @RequestParam("untilDate") String untilDate) {
        return reportService.totalDiscountReport(fromDate, untilDate);
    }
}
