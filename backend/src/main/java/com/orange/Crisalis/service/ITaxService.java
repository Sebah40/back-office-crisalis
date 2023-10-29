package com.orange.Crisalis.service;

import com.orange.Crisalis.dto.TaxDto;
import com.orange.Crisalis.model.Tax;

import java.util.List;

public interface ITaxService {

    List<TaxDto> getTaxes();

    TaxDto getTaxById(Integer id);

    Tax saveTax(TaxDto taxDto);

    void updateTax(TaxDto tax, Integer id);

    void deleteTax(Integer id);
}
