package com.orange.Crisalis.service;

import com.orange.Crisalis.model.dto.ReportTotalDiscount;
import com.orange.Crisalis.model.dto.ReportTotalDiscountDTO;
import com.orange.Crisalis.repository.IReportRepository;
import com.orange.Crisalis.repository.OrderRepository;
import com.orange.Crisalis.service.interfaces.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements IReportService {

    @Autowired
    private IReportRepository reportRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<ReportTotalDiscountDTO> totalDiscountReport(String fromDate, String untilDate) {
        List<ReportTotalDiscount> reports = reportRepository.reportTotalDiscount(fromDate, untilDate);
        for (ReportTotalDiscount report : reports){
            System.out.println(report.getOrderNum());
            System.out.println(report.getTotalDiscount());
        }
        List<ReportTotalDiscountDTO> reportsDTO = reports.stream().map(rep -> {
            String clientName = rep.getDtype().equalsIgnoreCase("PERSON")
                    ? rep.getFirstName() + " " + rep.getLastName() : rep.getBusinessName();
            return new ReportTotalDiscountDTO(rep.getClientId(), clientName, rep.getServiceName(), rep.getOrderNum(),
                    rep.getDate(), rep.getTotalDiscount());
        }).collect(Collectors.toList());

        return reportsDTO;
    }

}
