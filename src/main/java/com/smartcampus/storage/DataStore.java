/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.storage;

import com.smartcampus.model.*;
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
        rooms.put(1, new Room(1, "Lab A", 30));
        rooms.put(2, new Room(2, "Hall", 100));

        Sensor s1 = new Sensor(1, "Temperature", "22C");
        s1.setRoomId(1);
        sensors.put(1, s1);

        Sensor s2 = new Sensor(2, "Humidity", "60%");
        s2.setRoomId(2);
        sensors.put(2, s2);
    }
}
