/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.filter;

/**
 *
 * @author Thej
 */
import javax.ws.rs.container.*;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    public void filter(ContainerRequestContext req) throws IOException {
        System.out.println("Request: " + req.getMethod() + " " + req.getUriInfo().getRequestUri());
    }

    public void filter(ContainerRequestContext req, ContainerResponseContext res) throws IOException {
        System.out.println("Response: " + res.getStatus());
    }
}