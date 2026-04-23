/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

/**
 *
 * @author Thej
 */
package com.smartcampus;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class SmartCampusApi {

    public static final String BASE_URI = "http://localhost:8080/api/v1/";

    public static HttpServer startServer() {
        final ResourceConfig config = new ResourceConfig()
                .packages("com.smartcampus");

        return GrizzlyHttpServerFactory.createHttpServer(
                URI.create(BASE_URI), config
        );
    }

    public static void main(String[] args) {
        HttpServer server = startServer();

        System.out.println("Server running at http://localhost:8080/api/v1/");
        System.out.println("Test endpoint: http://localhost:8080/api/v1/test");

        // Check Java version
        System.out.println("Java version: " + System.getProperty("java.version"));

        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));

        try {
            Thread.currentThread().join();
        } catch (InterruptedException ignored) {}
    }
}