package com.smartcampus.storage;

import com.smartcampus.model.*;
import java.util.*;

/**
 *
 * @author Thej
 */
public class DataStore {
    public static Map<String, Room> rooms = new HashMap<>();
    public static Map<String, Sensor> sensors = new HashMap<>();
    public static Map<String, List<SensorReading>> readings = new HashMap<>();

    static {
        Room r1 = new Room("ROOM-001", "Library", 50);
        Room r2 = new Room("ROOM-002", "Lab", 30);

        rooms.put(r1.getId(), r1);
        rooms.put(r2.getId(), r2);

        Sensor s1 = new Sensor("TEMP-001", "Temperature", "ACTIVE", 22.5, "ROOM-001");
        Sensor s2 = new Sensor("HUM-001", "Humidity", "ACTIVE", 60.0, "ROOM-002");

        sensors.put(s1.getId(), s1);
        sensors.put(s2.getId(), s2);

        r1.getSensorIds().add(s1.getId());
        r2.getSensorIds().add(s2.getId());

        readings.put(s1.getId(), new ArrayList<>());
        readings.put(s2.getId(), new ArrayList<>());
    }
}
