/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resource;

import com.smartcampus.model.Room;
import com.smartcampus.model.Sensor;
import com.smartcampus.storage.DataStore;
import com.smartcampus.exception.ResourceNotFoundException;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.stream.Collectors;


@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
/**
 *
 * @author Thej
 */
public class RoomResource {

    @GET
    public Collection<Room> getAllRooms() {
        return DataStore.rooms.values();
    }

    @GET
    @Path("/{id}")
    public Room getRoom(@PathParam("id") int id) {
        Room room = DataStore.rooms.get(id);
        if (room == null) throw new ResourceNotFoundException("Room not found");
        return room;
    }

    @POST
    public Room addRoom(Room room) {
        int id = DataStore.rooms.size() + 1;
        room.setId(id);
        DataStore.rooms.put(id, room);
        return room;
    }

    @DELETE
    @Path("/{id}")
    public String deleteRoom(@PathParam("id") int id) {
        if (DataStore.rooms.remove(id) == null)
            throw new ResourceNotFoundException("Room not found");
        return "Deleted";
    }

    // 🔥 Sub-resource
    @GET
    @Path("/{roomId}/sensors")
    public List<Sensor> getSensorsByRoom(@PathParam("roomId") int roomId) {
        if (!DataStore.rooms.containsKey(roomId))
            throw new ResourceNotFoundException("Room not found");

        return DataStore.sensors.values()
                .stream()
                .filter(s -> s.getRoomId() == roomId)
                .collect(Collectors.toList());
    }
}