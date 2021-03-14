package com.apd.tema2.factory;

import com.apd.tema2.Main;
import com.apd.tema2.entities.Pedestrians;
import com.apd.tema2.entities.ReaderHandler;
import com.apd.tema2.intersections.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Inet4Address;

/**
 * Returneaza sub forma unor clase anonime implementari pentru metoda de citire din fisier.
 */
public class ReaderHandlerFactory {

    public static ReaderHandler getHandler(String handlerType) {
        // simple semaphore intersection
        // max random N cars roundabout (s time to exit each of them)
        // roundabout with exactly one car from each lane simultaneously
        // roundabout with exactly X cars from each lane simultaneously
        // roundabout with at most X cars from each lane simultaneously
        // entering a road without any priority
        // crosswalk activated on at least a number of people (s time to finish all of them)
        // road in maintenance - 1 lane 2 ways, X cars at a time
        // road in maintenance - N lanes 2 ways, X cars at a time
        // railroad blockage for T seconds for all the cars
        // unmarked intersection
        // cars racing
        return switch (handlerType) {
            case "simple_semaphore" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) {
                    // Exemplu de utilizare:
                     Main.intersection = IntersectionFactory.getIntersection("simple_intersection");
                }
            };
            case "simple_n_roundabout" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    Main.intersection = IntersectionFactory.getIntersection("simple_roundabout");
                    // To parse input line use:
                    String[] line = br.readLine().split(" ");

                    ((SimpleRoundabout) Main.intersection).setRoundabout(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
                }
            };
            case "simple_strict_1_car_roundabout" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    Main.intersection = IntersectionFactory.getIntersection("simple_1in");
                    String[] line = br.readLine().split(" ");
                    int max_c = Integer.parseInt(line[0]);
                    int round_wait = Integer.parseInt(line[1]);
                    ((Simple1InRoundabout) Main.intersection).setRoundAbout(max_c, round_wait, max_c);
                }
            };
            case "simple_strict_x_car_roundabout" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    Main.intersection = IntersectionFactory.getIntersection("simple_strict_x_in");
                    String[] line = br.readLine().split(" ");
                    int max_c = Integer.parseInt(line[0]);
                    int round_wait = Integer.parseInt(line[1]);
                    int cars_in_direction = Integer.parseInt(line[2]);
                    ((SimpleStrictRoundabout) Main.intersection).setRoundAbout(cars_in_direction, round_wait, max_c);
                }
            };
            case "simple_max_x_car_roundabout" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    Main.intersection = IntersectionFactory.getIntersection("simple_x_in");
                    String[] line = br.readLine().split(" ");
                    int max_c = Integer.parseInt(line[0]);
                    int round_wait = Integer.parseInt(line[1]);
                    int cars_in_direction = Integer.parseInt(line[2]);
                    ((SimpleXInRoundAbout) Main.intersection).setRoundAbout(cars_in_direction, round_wait, max_c);
                }
            };
            case "priority_intersection" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    Main.intersection = IntersectionFactory.getIntersection("priority_intersection");
                    String[] line = br.readLine().split(" ");
                    int low = Integer.parseInt(line[1]);
                    int high = Integer.parseInt(line[0]);
                    ((PriorityIntersection) Main.intersection).setPriorities(low, high);
                }
            };
            case "crosswalk" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    Main.intersection = IntersectionFactory.getIntersection("crosswalk");
                    String[] line = br.readLine().split(" ");
                    int exe = Integer.parseInt(line[0]);
                    int max_ped = Integer.parseInt(line[1]);
                    Main.pedestrians = new Pedestrians(exe, max_ped);
                }
            };
            case "simple_maintenance" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    Main.intersection = IntersectionFactory.getIntersection("maintenance");
                    String[] line = br.readLine().split(" ");
                    int X = Integer.parseInt(line[0]);

                    ((MaintenanceIntersection) Main.intersection).setMaintenance(X);

                }
            };
            case "complex_maintenance" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    
                }
            };
            case "railroad" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    Main.intersection = IntersectionFactory.getIntersection("railroad");
                    ((RailroadIntersection) Main.intersection).setRaiload(Main.carsNo);
                }
            };
            default -> null;
        };
    }

}
