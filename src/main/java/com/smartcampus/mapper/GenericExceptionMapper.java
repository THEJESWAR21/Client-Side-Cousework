/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.mapper;

/**
 *
 * @author Thej
 */

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.*;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {
    public Response toResponse(Exception ex) {
        return Response.status(500).entity("Error: " + ex.getMessage()).build();
    }
}
