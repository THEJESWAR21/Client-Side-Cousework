/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.mapper;

/**
 *
 * @author Thej
 */

import com.smartcampus.exception.ResourceNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.*;

@Provider
public class ResourceNotFoundMapper implements ExceptionMapper<ResourceNotFoundException> {
    public Response toResponse(ResourceNotFoundException ex) {
        return Response.status(404).entity(ex.getMessage()).build();
    }
}