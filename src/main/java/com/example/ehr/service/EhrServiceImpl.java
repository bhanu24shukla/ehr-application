package com.example.ehr.service;

import com.example.ehr.model.StateRecord;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Service
public class EhrServiceImpl implements EhrService {

    private final String dataUrl;

    public EhrServiceImpl(@Value("${ehr.data-url}") String dataUrl) {
        this.dataUrl = dataUrl;
    }

    @Override
    public List<StateRecord> getMeaningfulUse2014() throws Exception {

        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(dataUrl))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to download dataset: " + response.statusCode());
        }

        List<StateRecord> records = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new StringReader(response.body()))) {

            String[] header = reader.readNext();

            if (header == null) {
                throw new RuntimeException("CSV header is null.");
            }

            Map<String, Integer> index = new HashMap<>();

            for (int i = 0; i < header.length; i++) {
                index.put(header[i].trim().toLowerCase(), i);
            }

            Integer regionIndex = index.get("region");
            Integer periodIndex = index.get("period");
            Integer pctIndex = index.get("pct_hospitals_mu_aiu");

            if (regionIndex == null || periodIndex == null || pctIndex == null) {
                throw new RuntimeException("Required columns not found in CSV");
            }

            String[] row;

            while ((row = reader.readNext()) != null) {

                String period = row[periodIndex];

                if ("2014".equals(period)) {

                    String state = row[regionIndex];
                    String pct = row[pctIndex];

                    if (pct != null && !pct.isEmpty()) {
                        records.add(new StateRecord(state, Double.parseDouble(pct)));
                    }
                }
            }
        }

        return records.stream()
                .sorted(Comparator.comparingDouble(StateRecord::percentage).reversed())
                .toList();
    }
}