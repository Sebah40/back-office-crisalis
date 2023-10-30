package com.orange.Crisalis.service;

import com.orange.Crisalis.model.EnterpriseEntity;
import com.orange.Crisalis.model.PersonEntity;
import com.orange.Crisalis.repository.IPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@Transactional
public class PersonService {

    @Autowired
    IPersonRepository iPersonRepository;

    public boolean existsById(int id){
        return iPersonRepository.existsById(id);
    }

/*    public boolean existsByFistName(String firstName){
        return iPersonRepository.existsByFirstName(firstName);
    }

    public boolean existsByLastName(String lastName){
        return iPersonRepository.existsByLastName(lastName);
    }*/

    public boolean existsByDni(String dni){
        return iPersonRepository.existsByDni(dni);
    }

    public void save(PersonEntity personEntity){
        iPersonRepository.save(personEntity);
    }
}
