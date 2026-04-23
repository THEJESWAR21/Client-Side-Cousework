package com.smartcampus.model;

/**
 *
 * @author Thej
 */
public class Booking {
    private int id;
    private int roomId;
    private String timeSlot;

    public Booking() {}

    public Booking(int id, int roomId, String timeSlot) {
        this.id = id;
        this.roomId = roomId;
        this.timeSlot = timeSlot;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }
}
