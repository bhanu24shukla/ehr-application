# EHR Meaningful Use Data Service

## Overview

This Spring Boot application fetches and processes healthcare data on the **Meaningful Use** of Certified Electronic Health Record Technology (CEHRT) by eligible and critical access hospitals in the United States.

It downloads a publicly available CSV dataset from [healthit.gov](https://www.healthit.gov) and extracts the percentage of hospitals by state that demonstrated Meaningful Use in **2014**.

---

## What the Application Does

- Connects to the **healthit.gov open data API** and downloads the CSV dataset.  
- Parses the CSV to extract relevant columns:  
  - `region` (State)
  - `period` (Year)
  - `% Hospitals Demonstrating Meaningful Use` (`pct_hospitals_mu_aiu`)  
- Filters the dataset for the year 2014 only.
- Sorts states by **descending percentage** of hospitals demonstrating Meaningful Use.  
- Returns the processed data either as a **list in the service** or via a REST API.

---

## REST API Endpoint

The application exposes a REST endpoint for retrieving the Meaningful Use data:

| HTTP Method | Endpoint               | Description                                                                                                          |
|-------------|------------------------|----------------------------------------------------------------------------------------------------------------------|
| GET         | `/meaningful-use/2014` | Returns a JSON list of states with percentages of hospitals demonstrating Meaningful Use in 2014, sorted descending. |

Example JSON response:

```json
[
  {"state":"CA","percentage":85.45},
  {"state":"NY","percentage":82.12},
  {"state":"TX","percentage":79.50}
]