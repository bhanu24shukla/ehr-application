package com.example.ehr.service;

import com.example.ehr.model.StateRecord;

import java.util.List;

public interface EhrService {
    List<StateRecord> getMeaningfulUse2014() throws Exception;
}