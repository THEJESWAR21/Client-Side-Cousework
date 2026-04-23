/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;
/**
 *
 * @author Thej
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class DiscoveryResource {   
    @GET
    public Map<String, String> getEndpoints() {
        Map<String, String> map = new HashMap<>();
        map.put("rooms", "/api/v1/rooms");
        map.put("sensors", "/api/v1/sensors");
        map.put("test", "/api/v1/test");
        return map;
    }

}
