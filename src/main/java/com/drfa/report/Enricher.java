package com.drfa.report;


import org.json.JSONObject;

interface Enricher {
    void enrich(JSONObject json);
}
