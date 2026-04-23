/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.storage;

import com.smartcampus.model.Room;
import com.smartcampus.model.Sensor;
import com.smartcampus.model.Booking;

import java.util.*;
/**
 *
 * @author Thej
 */
public class DataStore {
    public static Map<Integer, Room> rooms = new HashMap<>();
    public static Map<Integer, Sensor> sensors = new HashMap<>();
    public static Map<Integer, Booking> bookings = new HashMap<>();

    static {
        // Sample Rooms
        rooms.put(1, new Room(1, "Lab A", 30));
        rooms.put(2, new Room(2, "Lecture Hall", 100));

        // Sample Sensors
        sensors.put(1, new Sensor(1, "Temperature", "22C"));
        sensors.put(2, new Sensor(2, "Humidity", "60%"));

        // Sample Bookings
        bookings.put(1, new Booking(1, 1, "10:00-11:00"));
    }
}
