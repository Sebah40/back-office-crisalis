package com.orange.Crisalis.service;

import com.orange.Crisalis.model.ClientEntity;
import com.orange.Crisalis.repository.IClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ClientService {

    @Autowired
    IClientRepository iClientRepository;

    public List<ClientEntity> findAll(){
        return iClientRepository.findAll();
    }
}
