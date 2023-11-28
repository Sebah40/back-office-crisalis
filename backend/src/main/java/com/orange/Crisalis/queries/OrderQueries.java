package com.orange.Crisalis.queries;

import com.orange.Crisalis.enums.Type;
import com.orange.Crisalis.model.dto.OrderDTO;
import com.orange.Crisalis.model.dto.filters.OrderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@Component
public class OrderQueries {

    @Autowired
    private GenericQueryMethods genericQueryMethods;

    public String orderSearchbar(String clientName) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT ord.* FROM order_entity ord INNER JOIN client_entity ce ON ord.client_id = ce.id ")
                .append("WHERE ce.is_active = 1 ")
                .append(findByClientNameSearchbar(clientName));
        return query.toString();
    }

    public String findByClientNameSearchbar(String clientName) {
        StringBuilder query = new StringBuilder();
        query.append("(ce.business_name LIKE '%").append(clientName).append("%' ")
                .append("OR (CONCAT_WS(' ', ce.first_name, ce.last_name) like '%").append(clientName).append("%'")
                .append(" OR CONCAT_WS(' ', ce.last_name, ce.first_name) like '%").append(clientName).append("%'))");
        return query.toString();
    }

    public String filterOrderList(OrderFilter orderFilter) {
        StringBuilder query = new StringBuilder();

        query.append("SELECT ord.* FROM order_entity ord INNER JOIN client_entity ce ON ord.client_id = ce.id ")
                .append("WHERE ce.is_active = 1 ");
        if(!orderFilter.getState().isEmpty()) {
            query.append("AND ord.order_state")
                    .append(genericQueryMethods.queryIterator(orderFilter.getState()));
        }
        if(orderFilter.getFromDate() != null || orderFilter.getUntilDate() != null) {
            String column = "ord.date_created";
            query.append(genericQueryMethods.dateBetween(orderFilter.getFromDate(), orderFilter.getUntilDate(), column));
        }
        if(orderFilter.getDtype() != null) {
            query.append(" AND ce.dtype = '").append(orderFilter.getDtype()).append("'");
        }
        if(orderFilter.getClientName() != null || orderFilter.getClientName().trim() != "") {
            query.append(" AND ").append(findByClientNameSearchbar(orderFilter.getClientName()));
        }
        System.out.println(query.toString());
        return query.toString();
    }



}
