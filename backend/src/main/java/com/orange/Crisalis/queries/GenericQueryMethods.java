package com.orange.Crisalis.queries;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Component
public class GenericQueryMethods {

    // MÉTODO PARA FILTRAR POR CAMPO DE SELECCIÓN MÚLTIPLE.
    public String queryIterator(List<?> list) {
        Iterator<?> mod = list.iterator();
        StringBuilder query = new StringBuilder();
        query.append(" IN (");
        while(mod.hasNext()) {
            query.append("'" + mod.next() + "'");
            query.append(mod.hasNext() ? ", " : ") ");
        }
        return query.toString();
    }

    public String dateBetween(LocalDate fromDate, LocalDate untilDate, String column) {
        StringBuilder query = new StringBuilder();
        query.append(" AND (");
        if(untilDate != null && fromDate != null) {
            if(untilDate.isAfter(fromDate) || untilDate.isEqual(fromDate)) {
                    query.append(column).append(" >= '").append(fromDate)
                            .append("' and ")
                            .append(column).append(" <= '").append(untilDate).append("'");
            }
        } else if(untilDate == null && fromDate != null) {
            query.append(column).append(" >= '").append(fromDate).append("'");
        }
        else if (untilDate != null && fromDate == null) {
            query.append(column).append(" <= '").append(untilDate).append("'");
        }
        query.append(")");
        System.out.println(query);
        return query.toString();
    }

}
