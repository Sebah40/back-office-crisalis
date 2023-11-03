package com.orange.Crisalis.service;

import com.orange.Crisalis.model.ClientEntity;
import com.orange.Crisalis.repository.IClientRepository;
import com.orange.Crisalis.repository.IEnterpriseRepository;
import com.orange.Crisalis.repository.IPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class ClientEnterprisePersonService {

    @Autowired
    private IClientRepository iClientRepository;

   public List<ClientEntity> searchByAnyParameter(String query) {
        return iClientRepository.searchByAnyParameter(query);
    }
}
