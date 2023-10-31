package com.orange.Crisalis.service;

import com.orange.Crisalis.dto.TaxDto;
import com.orange.Crisalis.model.Tax;
import com.orange.Crisalis.repository.ITaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaxServiceImpl implements ITaxService {

    private ITaxRepository taxRepository;

    @Autowired
    public TaxServiceImpl(ITaxRepository taxRepository) {
        this.taxRepository = taxRepository;
    }

    public final boolean verifyTax(TaxDto taxDto) {
        if(taxDto != null && taxDto.getTaxPercentage() != null && taxDto.getTaxName() != null) {
            return taxRepository.findTaxByTaxName(taxDto.getTaxName()).isEmpty();
        }
        return false;
    }

    @Override
    public boolean verifyTaxById(Integer id) {
        return taxRepository.findTaxById(id).isPresent();
    }

    @Override
    public List<TaxDto> getTaxes() {
        List<TaxDto> taxList = taxRepository.findAllTaxes().stream().map(TaxDto::new).collect(Collectors.toList());;
        return taxList;
    }

    @Override
    public TaxDto getTaxById(Integer id) {
        TaxDto tax = new TaxDto(taxRepository.findTaxById(id).orElse(null));
        return tax;
    }

    @Override
    public Tax saveTax(TaxDto taxDto) {
        Tax tax = new Tax();
        tax.setTaxName(taxDto.getTaxName());
        tax.setTaxPercentage(taxDto.getTaxPercentage());
        return taxRepository.save(tax);
    }

    @Override
    public void updateTax(TaxDto taxDto, Integer id) {
        Tax tax = taxRepository.findTaxById(id).orElse(null);
        tax.setTaxName(taxDto.getTaxName());
        tax.setTaxPercentage(taxDto.getTaxPercentage());
        taxRepository.updateTax(id, tax.getTaxName(), tax.getTaxPercentage());
    }




    @Override
    public void deleteTax(Integer id) {
        Tax tax = taxRepository.findTaxById(id).orElse(null);
        tax.setActive(false);
        taxRepository.save(tax);
    }


    public Optional<Tax> findById(Integer taxId) {
        return taxRepository.findById(taxId);
    }

    public List<Tax> findTaxesBySellableGoodsId(Long sellableGoodId) {
        return this.taxRepository.findTaxesBySellableGoodsId(sellableGoodId);
    }
}
