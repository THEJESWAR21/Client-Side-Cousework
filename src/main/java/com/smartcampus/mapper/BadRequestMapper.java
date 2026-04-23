package com.smartcampus.mapper;

import com.smartcampus.exception.BadRequestException;
import com.smartcampus.exception.ErrorResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BadRequestMapper implements ExceptionMapper<BadRequestException> {

    @Override
    public Response toResponse(BadRequestException ex) {

        ErrorResponse error = new ErrorResponse(400, ex.getMessage());

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(error)
                .build();
    }
}