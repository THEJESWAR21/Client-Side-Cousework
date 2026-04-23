/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resource;

import com.smartcampus.model.Sensor;
import com.smartcampus.storage.DataStore;
import com.smartcampus.exception.ResourceNotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
/**
 *
 * @author Thej
 */


public class SensorResource {
  @GET
    public Collection<Sensor> getAllSensors() {
        return DataStore.sensors.values();
    }

    @GET
    @Path("/{id}")
    public Sensor getSensor(@PathParam("id") int id) {
        Sensor s = DataStore.sensors.get(id);
        if (s == null) throw new ResourceNotFoundException("Sensor not found");
        return s;
    }

    @POST
    public Sensor addSensor(Sensor sensor) {
        int id = DataStore.sensors.size() + 1;
        sensor.setId(id);
        DataStore.sensors.put(id, sensor);
        return sensor;
    }

    @DELETE
    @Path("/{id}")
    public String deleteSensor(@PathParam("id") int id) {
        if (DataStore.sensors.remove(id) == null)
            throw new ResourceNotFoundException("Sensor not found");
        return "Deleted";
    }

    // 🌡️ Sensor reading update
    @PUT
    @Path("/{id}/reading")
    @Consumes(MediaType.TEXT_PLAIN)
    public Sensor updateReading(@PathParam("id") int id, String value) {
        Sensor s = DataStore.sensors.get(id);
        if (s == null) throw new ResourceNotFoundException("Sensor not found");

        s.setValue(value);
        return s;
    }
}
