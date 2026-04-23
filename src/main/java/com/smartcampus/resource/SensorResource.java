/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resource;

import com.smartcampus.model.Sensor;
import com.smartcampus.storage.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
/**
 *
 * @author Thej
 */
public class SensorResource {
      // GET all sensors
    @GET
    public Collection<Sensor> getAllSensors() {
        return DataStore.sensors.values();
    }

    // GET sensor by ID
    @GET
    @Path("/{id}")
    public Response getSensor(@PathParam("id") int id) {
        Sensor sensor = DataStore.sensors.get(id);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor not found")
                    .build();
        }

        return Response.ok(sensor).build();
    }

    // POST new sensor
    @POST
    public Response addSensor(Sensor sensor) {
        int id = DataStore.sensors.size() + 1;
        sensor.setId(id);
        DataStore.sensors.put(id, sensor);

        return Response.status(Response.Status.CREATED)
                .entity(sensor)
                .build();
    }

    // DELETE sensor
    @DELETE
    @Path("/{id}")
    public Response deleteSensor(@PathParam("id") int id) {
        Sensor removed = DataStore.sensors.remove(id);

        if (removed == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor not found")
                    .build();
        }

        return Response.ok("Sensor deleted").build();
    }
}
