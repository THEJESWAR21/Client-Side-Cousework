package com.smartcampus.mapper;

import com.smartcampus.exception.ResourceNotFoundException;
import com.smartcampus.exception.ErrorResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ResourceNotFoundMapper implements ExceptionMapper<ResourceNotFoundException> {

    @Override
    public Response toResponse(ResourceNotFoundException ex) {

        ErrorResponse error = new ErrorResponse(404, ex.getMessage());

        return Response.status(Response.Status.NOT_FOUND)
                .entity(error)
                .build();
    }
}