package com.orange.Crisalis.service;

import com.orange.Crisalis.model.SellableGood;
import com.orange.Crisalis.repository.SellableGoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellableGoodService {
  @Autowired
  private SellableGoodRepository sellableGoodRepository;

  public List<SellableGood> findAll() {
    return sellableGoodRepository.findAll();
  }

  public Optional<SellableGood> findById(Long id) {
    return sellableGoodRepository.findById(id);
  }

  public SellableGood save(SellableGood salableGood) {
    return sellableGoodRepository.save(salableGood);
  }

  public void deleteById(Long id) {
    sellableGoodRepository.deleteById(id);
  }

  public boolean existsById(Long sellableGoodId) {
    return this.sellableGoodRepository.existsById(sellableGoodId);
  }
}
