package com.netlabs.controller;

import com.netlabs.repository.PurchasesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchases")
public class PurchasesController {

    @Autowired
    PurchasesRepository purchasesRepository;
    
}
