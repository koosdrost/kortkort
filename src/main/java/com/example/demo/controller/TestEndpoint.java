package com.example.demo.controller;

//import org.springframework.ws.server.endpoint.annotation.Endpoint;
//import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
//import org.springframework.ws.server.endpoint.annotation.RequestPayload;
//import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;

import java.util.Map;
@Endpoint
public class TestEndpoint {
//
//    private final Map<String, Product> productMap;
//
//    public ProductsEndpoint(Map<String, Product> productMap) {
//        this.productMap = productMap;
//    }
//
//    @RolesAllowed("user")
//    @PayloadRoot(namespace = "http://www.baeldung.com/springbootsoap/keycloak", localPart = "getProductDetailsRequest")
//    @ResponsePayload
//    public GetProductDetailsResponse getProductDetails(@RequestPayload GetProductDetailsRequest request) {
//        GetProductDetailsResponse response = new GetProductDetailsResponse();
//        response.setProduct(productMap.get(request.getId()));
//        return response;
//    }
//
//    @RolesAllowed("admin")
//    @PayloadRoot(namespace = "http://www.baeldung.com/springbootsoap/keycloak", localPart = "deleteProductRequest")
//    @ResponsePayload
//    public DeleteProductResponse deleteProduct(@RequestPayload DeleteProductRequest request) {
//        DeleteProductResponse response = new DeleteProductResponse();
//        response.setMessage("Success! Deleted the product with the id - "+request.getId());
//        return response;
//    }
}
