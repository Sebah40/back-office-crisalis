package com.orange.Crisalis.controller;

import com.orange.Crisalis.model.SellableGood;
import com.orange.Crisalis.model.Tax;
import com.orange.Crisalis.model.dto.SellableGoodTax;
import com.orange.Crisalis.repository.ITaxRepository;
import com.orange.Crisalis.security.Controller.Message;
import com.orange.Crisalis.service.SellableGoodService;
import com.orange.Crisalis.service.TaxServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/good")
@CrossOrigin(origins = {"http://localhost:4200", "https://localhost:4200"})
public class SellableGoodController {
  @Autowired
  private SellableGoodService sellableGoodService;
  @Autowired
  private TaxServiceImpl taxService;

  @Autowired
  private ITaxRepository taxRepository;

  @PreAuthorize("hasAnyRole('USER' ,'ADMIN')")
  @GetMapping("/getAll")
  public List<SellableGood> getAllSellableGoods() {
    return sellableGoodService.findAll();
  }
  @PreAuthorize("hasAnyRole('USER' ,'ADMIN')")
  @GetMapping("/read")
  public SellableGood getSellableGoodById(@RequestBody Long id) throws Exception {
    return sellableGoodService.findById(id).orElseThrow(() -> new Exception("Producto/Servicio con id:" + id+" no se encontro."));
  }
  @PreAuthorize("hasRole('ADMIN')")
  @Transactional
  @PostMapping("/create")
  public ResponseEntity<SellableGood> createSellableGood(@RequestBody SellableGood sellableGood) {
    Set<Tax> taxes = new HashSet<>();
    if(!sellableGood.getTaxes().isEmpty()) {
      taxes = sellableGood.getTaxes().stream()
          .filter(tax -> taxRepository.existsById(tax.getId()))
          .map(tax -> taxRepository.findById(tax.getId()).get())
          .collect(Collectors.toSet());
      sellableGood.setTaxes(new HashSet<>());
    }
    SellableGood newSellableGood = sellableGoodService.save(sellableGood);
    taxes.forEach(newSellableGood::addTax);
    newSellableGood = sellableGoodService.save(newSellableGood);
    return new ResponseEntity<>(newSellableGood, HttpStatus.CREATED);
  }
  @PreAuthorize("hasRole('ADMIN')")
  @Transactional
  @PutMapping("/edit")
  public ResponseEntity<?> updateSellableGoods(@RequestBody SellableGood sellableGood) throws Exception {
    SellableGood existingSellableGood = sellableGoodService.findById(sellableGood.getId()).orElseThrow(() -> new Exception("Product not found with id: " + sellableGood.getId()));
    existingSellableGood.setName(sellableGood.getName());
    existingSellableGood.setType(sellableGood.getType());
    existingSellableGood.setPrice(sellableGood.getPrice());
    existingSellableGood.setDescription(sellableGood.getDescription());
    existingSellableGood.setActive(sellableGood.isActive());
    existingSellableGood.setTaxes(new HashSet<>());
    Set<Tax> taxes = sellableGood.getTaxes().stream()
        .filter(tax -> taxRepository.existsById(tax.getId()))
        .map(tax -> taxRepository.findById(tax.getId()).get())
        .collect(Collectors.toSet());
    taxes.forEach(existingSellableGood::addTax);
    sellableGoodService.save(existingSellableGood);
    return new ResponseEntity(new Message("Editado exitosamente"), HttpStatus.OK);
  }
  @PreAuthorize("hasRole('ADMIN')")
  @Transactional
  @PutMapping("/disable")
  public ResponseEntity<?> deleteSellableGood(@RequestBody Long id) throws Exception {
    SellableGood existingSellableGood = sellableGoodService.findById(id).orElseThrow(() -> new Exception("Producto no encontrado id: " + id));
    existingSellableGood.setActive(false);
    sellableGoodService.save(existingSellableGood);
    return new ResponseEntity(new Message("Producto/Servicio se deshabilito exitosamente"), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @Transactional
  @PutMapping("/addTax")
  public ResponseEntity<?> addTaxSellableGood(@RequestBody SellableGoodTax data) throws Exception {
    SellableGood existingSellableGood = sellableGoodService.findById(data.getSellableGoodId()).orElseThrow(() -> new Exception("Producto no encontrado id: " + data.getSellableGoodId()));
    Tax existingTax = taxService.findById(data.getTaxId()).orElseThrow(() -> new Exception("Impuesto no encontrado id: " + data.getTaxId()));
    existingSellableGood.addTax(existingTax);
    System.out.println("ASDGFADSFGSDFGSERWTQWERFASDFASDFASDFQWERFQWAWERQASWDFASDFASDFASDFQWER");
    System.out.println(data);
    sellableGoodService.save(existingSellableGood);
    return new ResponseEntity(new Message("Se agrego impuesto exitosamente"), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/removeTax")
  public ResponseEntity<?> deleteTaxFromSellableGood(@RequestBody SellableGoodTax data) throws Exception {
    SellableGood sellableGood = sellableGoodService.findById(data.getSellableGoodId())
        .orElseThrow(() -> new Exception("No se encontro el producto/servicio con id = " + data.getSellableGoodId()));

    sellableGood.removeTax(data.getTaxId());
    sellableGoodService.save(sellableGood);
    return new ResponseEntity<>(new Message("Se quito el impuesto exitosamente"),HttpStatus.OK);
  }

  @PreAuthorize("hasAnyRole('USER' ,'ADMIN')")
  @GetMapping("/getTaxes")
  public ResponseEntity<List<Tax>> getAllTaxesBySellableGoodId(@RequestBody Long sellableGoodId) throws Exception {
    if (!sellableGoodService.existsById(sellableGoodId)) {
      throw new Exception("No se encontro producto/servicio id = " + sellableGoodId);
    }

    List<Tax> taxes = taxService.findTaxesBySellableGoodsId(sellableGoodId);
    return new ResponseEntity<>(taxes, HttpStatus.OK);
  }


}
