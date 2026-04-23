package com.smartcampus.resource;

import com.smartcampus.model.Room;
import com.smartcampus.model.Sensor;
import com.smartcampus.storage.DataStore;
import com.smartcampus.exception.ResourceNotFoundException;
import com.smartcampus.exception.BadRequestException;



import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;


@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
/**
 *
 * @author Thej
 */
public class RoomResource {

     // GET all rooms
    @GET
    public Collection<Room> getAllRooms() {
        return DataStore.rooms.values();
    }

    // GET room by ID
    @GET
    @Path("/{id}")
    public Room getRoom(@PathParam("id") String id) {
        Room room = DataStore.rooms.get(id);

        if (room == null) {
            throw new ResourceNotFoundException("Room not found");
        }

        return room;
    }

    // CREATE new room
    @POST
    public Room createRoom(Room room) {

        if (room.getId() == null || room.getId().isBlank()) {
            throw new BadRequestException("Room ID is required");
        }

        if (room.getName() == null || room.getName().isBlank()) {
            throw new BadRequestException("Room name is required");
        }

        if (room.getCapacity() <= 0) {
            throw new BadRequestException("Capacity must be greater than 0");
        }

        if (DataStore.rooms.containsKey(room.getId())) {
            throw new BadRequestException("Room with this ID already exists");
        }

        DataStore.rooms.put(room.getId(), room);

        return room;
    }

    // DELETE room
    @DELETE
    @Path("/{id}")
    public String deleteRoom(@PathParam("id") String id) {

        Room removed = DataStore.rooms.remove(id);

        if (removed == null) {
            throw new ResourceNotFoundException("Room not found");
        }

        return "Room deleted successfully";
    }

    // SUB-RESOURCE: Get all sensors in a room
    @GET
    @Path("/{id}/sensors")
    public List<Sensor> getSensorsByRoom(@PathParam("id") String id) {

        Room room = DataStore.rooms.get(id);

        if (room == null) {
            throw new ResourceNotFoundException("Room not found");
        }

        List<Sensor> sensors = new ArrayList<>();

        for (String sensorId : room.getSensorIds()) {
            Sensor sensor = DataStore.sensors.get(sensorId);

            if (sensor != null) {
                sensors.add(sensor);
            }
        }

        return sensors;
    }
}