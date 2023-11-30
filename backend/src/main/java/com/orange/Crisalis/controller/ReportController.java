package com.orange.Crisalis.controller;

import com.orange.Crisalis.model.dto.ReportTotalDiscount;
import com.orange.Crisalis.service.interfaces.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private IReportService reportService;

    @GetMapping("/total-discount")
    @Transactional(readOnly = false)
    public List<ReportTotalDiscount> getTotalDiscountPerClient(@RequestParam("from_date") String fromDate,
                                                               @RequestParam("until_date") String untilDate) {
        System.out.println(fromDate);
        System.out.println(untilDate);

        return reportService.totalDiscountReport(fromDate, untilDate);
    }
}
