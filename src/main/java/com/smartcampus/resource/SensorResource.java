package com.smartcampus.resource;

import com.smartcampus.model.Sensor;
import com.smartcampus.model.SensorReading;
import com.smartcampus.storage.DataStore;
import com.smartcampus.exception.ResourceNotFoundException;
import com.smartcampus.exception.BadRequestException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 *
 * @author Thej
 */

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    // ✅ GET all sensors
    @GET
    public Collection<Sensor> getAllSensors() {
        return DataStore.sensors.values();
    }

    // ✅ GET sensor by ID
    @GET
    @Path("/{id}")
    public Sensor getSensor(@PathParam("id") String id) {
        Sensor sensor = DataStore.sensors.get(id);

        if (sensor == null) {
            throw new ResourceNotFoundException("Sensor not found");
        }

        return sensor;
    }

    // ✅ CREATE sensor
    @POST
    public Sensor createSensor(Sensor sensor) {

        // 🔴 Validation
        if (sensor.getId() == null || sensor.getId().isBlank()) {
            throw new BadRequestException("Sensor ID is required");
        }

        if (sensor.getType() == null || sensor.getType().isBlank()) {
            throw new BadRequestException("Sensor type is required");
        }

        if (sensor.getStatus() == null || sensor.getStatus().isBlank()) {
            throw new BadRequestException("Sensor status is required");
        }

        if (sensor.getRoomId() == null || sensor.getRoomId().isBlank()) {
            throw new BadRequestException("roomId is required");
        }

        if (!DataStore.rooms.containsKey(sensor.getRoomId())) {
            throw new BadRequestException("Invalid roomId");
        }

        if (DataStore.sensors.containsKey(sensor.getId())) {
            throw new BadRequestException("Sensor with this ID already exists");
        }

        // ✅ Store sensor
        DataStore.sensors.put(sensor.getId(), sensor);

        // ✅ Link to room
        DataStore.rooms.get(sensor.getRoomId())
                .getSensorIds()
                .add(sensor.getId());

        // ✅ Initialize readings list
        DataStore.readings.put(sensor.getId(), new ArrayList<>());

        return sensor;
    }

    // ✅ DELETE sensor
    @DELETE
    @Path("/{id}")
    public String deleteSensor(@PathParam("id") String id) {

        Sensor sensor = DataStore.sensors.remove(id);

        if (sensor == null) {
            throw new ResourceNotFoundException("Sensor not found");
        }

        // 🔥 Remove from room
        if (sensor.getRoomId() != null) {
            DataStore.rooms.get(sensor.getRoomId())
                    .getSensorIds()
                    .remove(id);
        }

        // 🔥 Remove readings
        DataStore.readings.remove(id);

        return "Sensor deleted successfully";
    }

    // 🌡️ ADD SENSOR READING (IMPORTANT FEATURE)
    @PUT
    @Path("/{id}/reading")
    public SensorReading addReading(@PathParam("id") String id, SensorReading reading) {

        Sensor sensor = DataStore.sensors.get(id);

        if (sensor == null) {
            throw new ResourceNotFoundException("Sensor not found");
        }

        if (reading == null) {
            throw new BadRequestException("Reading body is required");
        }

        // 🔴 Validate value
        if (reading.getValue() == 0) {
            throw new BadRequestException("Reading value must be provided");
        }

        // ✅ Generate metadata
        reading.setId(UUID.randomUUID().toString());
        reading.setTimestamp(System.currentTimeMillis());

        // ✅ Store reading
        DataStore.readings.get(id).add(reading);

        // ✅ Update current sensor value
        sensor.setCurrentValue(reading.getValue());

        return reading;
    }

    // 🔎 GET ALL READINGS FOR A SENSOR
    @GET
    @Path("/{id}/readings")
    public List<SensorReading> getReadings(@PathParam("id") String id) {

        if (!DataStore.sensors.containsKey(id)) {
            throw new ResourceNotFoundException("Sensor not found");
        }

        return DataStore.readings.getOrDefault(id, new ArrayList<>());
    }
}