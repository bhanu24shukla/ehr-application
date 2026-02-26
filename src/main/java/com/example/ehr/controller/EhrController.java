package com.example.ehr.controller;

import com.example.ehr.model.StateRecord;
import com.example.ehr.service.EhrService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EhrController {

    private final EhrService ehrService;

    public EhrController(EhrService ehrService) {
        this.ehrService = ehrService;
    }

    @GetMapping("/meaningful-use/2014")
    public List<StateRecord> getMeaningfulUse2014() throws Exception {
        return ehrService.getMeaningfulUse2014();
    }
}