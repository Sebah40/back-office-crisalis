package com.orange.Crisalis.service;

import com.orange.Crisalis.model.EnterpriseEntity;
import com.orange.Crisalis.repository.IEnterpriseRepository;
import com.orange.Crisalis.security.Entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class EnterpriseService {

    @Autowired
    IEnterpriseRepository iEnterpriseRepository;

    public boolean existsById(int id){
        return iEnterpriseRepository.existsById(id);
    }

    public boolean existsByCuit(String cuit){
        return iEnterpriseRepository.existsByCuit(cuit);
    }

    public boolean existsByBusinessName(String businessName){
        return iEnterpriseRepository.existsByBusinessName(businessName);
    }

    public void save(EnterpriseEntity enterpriseEntity){
        iEnterpriseRepository.save(enterpriseEntity);
    }
}