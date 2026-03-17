/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.compsciia.compsciia;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.json.JSONException;
import org.json.JSONObject;
 
/**
 *
 * @author fernandonunes
 */
public class AddressValidation {
    public static void validateCEP(String cep, Address address, JFrame frame) {
        String cleanCep = cep.trim();
        String url = "https://viacep.com.br/ws/" + cleanCep + "/json/";
        
        try {
            //System creates the HTTP Client for the request
            HttpClient client = HttpClient.newHttpClient();
            //Request is send based on the previously created URL
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            //Te
            JSONObject addressJSON = new JSONObject(response.body());

            
            address.setStreet(addressJSON.getString("logradouro"));
            address.setPostalCode(cleanCep);
            address.setNeighborhood(addressJSON.getString("bairro"));
            address.setCity(addressJSON.getString("localidade"));
            address.setValid(true);

        } catch (IOException | InterruptedException | JSONException e) {
            JOptionPane.showMessageDialog(frame, "Insert a valid Postal Code","Error loading Postal Code",
                    JOptionPane.ERROR_MESSAGE);
            
        }
    }
}
