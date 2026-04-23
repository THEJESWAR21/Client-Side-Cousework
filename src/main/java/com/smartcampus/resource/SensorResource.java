package com.smartcampus.resource;

import com.smartcampus.model.Sensor;
import com.smartcampus.storage.DataStore;
import com.smartcampus.exception.ResourceNotFoundException;
import com.smartcampus.exception.BadRequestException;
import com.smartcampus.exception.LinkedResourceNotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    @GET
    public List<Sensor> getSensors(@QueryParam("type") String type) {
        return DataStore.sensors.values()
                .stream()
                .filter(sensor ->
                        type == null || sensor.getType().equalsIgnoreCase(type)
                )
                .toList();
    }

    @GET
    @Path("/{id}")
    public Sensor getSensor(@PathParam("id") String id) {
        Sensor sensor = DataStore.sensors.get(id);

        if (sensor == null) {
            throw new ResourceNotFoundException("Sensor not found");
        }

        return sensor;
    }

    @POST
    public Sensor createSensor(Sensor sensor) {

        if (sensor.getId() == null || sensor.getId().isBlank()) {
            throw new BadRequestException("Sensor ID is required");
        }

        if (!DataStore.rooms.containsKey(sensor.getRoomId())) {
            throw new LinkedResourceNotFoundException("Room does not exist");
        }

        DataStore.sensors.put(sensor.getId(), sensor);

        DataStore.rooms.get(sensor.getRoomId())
                .getSensorIds()
                .add(sensor.getId());

        DataStore.readings.put(sensor.getId(), new ArrayList<>());

        return sensor;
    }

    @DELETE
    @Path("/{id}")
    public String deleteSensor(@PathParam("id") String id) {

        Sensor sensor = DataStore.sensors.remove(id);

        if (sensor == null) {
            throw new ResourceNotFoundException("Sensor not found");
        }

        DataStore.rooms.get(sensor.getRoomId())
                .getSensorIds()
                .remove(id);

        DataStore.readings.remove(id);

        return "Sensor deleted successfully";
    }

    // ✅ Sub-resource locator
    @Path("/{id}/readings")
    public SensorReadingResource getReadingResource(@PathParam("id") String id) {

        if (!DataStore.sensors.containsKey(id)) {
            throw new ResourceNotFoundException("Sensor not found");
        }

        return new SensorReadingResource(id);
    }
}