package com.smartcampus.resource;

import com.smartcampus.model.Sensor;
import com.smartcampus.model.SensorReading;
import com.smartcampus.storage.DataStore;
import com.smartcampus.exception.ResourceNotFoundException;
import com.smartcampus.exception.BadRequestException;
import com.smartcampus.exception.SensorUnavailableException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    private String sensorId;

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    public List<SensorReading> getReadings() {

        if (!DataStore.sensors.containsKey(sensorId)) {
            throw new ResourceNotFoundException("Sensor not found");
        }

        return DataStore.readings.getOrDefault(sensorId, new ArrayList<>());
    }

    @POST
    public SensorReading addReading(SensorReading reading) {

        Sensor sensor = DataStore.sensors.get(sensorId);

        if (sensor == null) {
            throw new ResourceNotFoundException("Sensor not found");
        }

        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) {
            throw new SensorUnavailableException("Sensor is under maintenance");
        }

        if (reading == null || reading.getValue() == 0) {
            throw new BadRequestException("Valid reading value required");
        }

        reading.setId(UUID.randomUUID().toString());
        reading.setTimestamp(System.currentTimeMillis());

        DataStore.readings.get(sensorId).add(reading);

        // 🔥 required side-effect
        sensor.setCurrentValue(reading.getValue());

        return reading;
    }
}