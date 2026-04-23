/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resource;
import com.smartcampus.model.Room;
import com.smartcampus.storage.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

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
    public Response getRoom(@PathParam("id") int id) {
        Room room = DataStore.rooms.get(id);

        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Room not found")
                    .build();
        }

        return Response.ok(room).build();
    }

    // POST new room
    @POST
    public Response addRoom(Room room) {
        int id = DataStore.rooms.size() + 1;
        room.setId(id);
        DataStore.rooms.put(id, room);

        return Response.status(Response.Status.CREATED)
                .entity(room)
                .build();
    }

    // DELETE room
    @DELETE
    @Path("/{id}")
    public Response deleteRoom(@PathParam("id") int id) {
        Room removed = DataStore.rooms.remove(id);

        if (removed == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Room not found")
                    .build();
        }

        return Response.ok("Room deleted").build();
    }
}
