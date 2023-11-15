package com.orange.Crisalis.service;

import com.orange.Crisalis.model.ClientEntity;
import com.orange.Crisalis.repository.IClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientService {

    @Autowired
    IClientRepository iClientRepository;

    public List<ClientEntity> findAll(){
        return iClientRepository.findAll();
    }
    public ClientEntity getById(Integer id){
        Optional<ClientEntity> clienteOptional = iClientRepository.findById(id);
        return clienteOptional.orElse(null);
    }
    public ClientEntity saveClient(ClientEntity client) {
        return iClientRepository.save(client);
    }

}
