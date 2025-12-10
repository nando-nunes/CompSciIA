/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.compsciia.compsciia;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 *
 * @author fernandonunes
 */
public class AddressValidation {
    public static String[] getAddressByCep(String cep) {
        String cleanCep = cep.replace("-", "").trim();
        String url = "https://viacep.com.br/ws/" + cleanCep + "/json/";
        
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            // Simple Manual Parsing (to avoid external library dependency issues in code snippet)
            String street = extractJsonValue(json, "logradouro");
            String neighborhood = extractJsonValue(json, "bairro");
            String city = extractJsonValue(json, "localidade");
            String state = extractJsonValue(json, "uf");

            return new String[]{street, neighborhood, city, state};

        } catch (Exception e) {
            return null;
        }
    }

    // Helper to find value in JSON string
    private static String extractJsonValue(String json, String key) {
        int startIndex = json.indexOf("\"" + key + "\"");
        if (startIndex == -1) return "";
        startIndex = json.indexOf(":", startIndex) + 2; // Move past ": "
        int endIndex = json.indexOf("\"", startIndex);
        return json.substring(startIndex, endIndex);
    }
}
