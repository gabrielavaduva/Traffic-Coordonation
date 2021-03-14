package com.apd.tema2.factory;

import com.apd.tema2.Main;
import com.apd.tema2.entities.*;
import com.apd.tema2.intersections.*;
import com.apd.tema2.utils.Constants;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

/**
 * Clasa Factory ce returneaza implementari ale InterfaceHandler sub forma unor
 * clase anonime.
 */
public class IntersectionHandlerFactory {

    public static IntersectionHandler getHandler(String handlerType) {
        // simple semaphore intersection
        // max random N cars roundabout (s time to exit each of them)
        // roundabout with exactly one car from each lane simultaneously
        // roundabout with exactly X cars from each lane simultaneously
        // roundabout with at most X cars from each lane simultaneously
        // entering a road without any priority
        // crosswalk activated on at least a number of people (s time to finish all of
        // them)
        // road in maintenance - 2 ways 1 lane each, X cars at a time
        // road in maintenance - 1 way, M out of N lanes are blocked, X cars at a time
        // railroad blockage for s seconds for all the cars
        // unmarked intersection
        // cars racing
        /**
         *
         *
         */
        return switch (handlerType) {
            /**
             * Asteapta timpul precizat si apoi trece masina.
             *
             */
            case "simple_semaphore" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) throws InterruptedException {
                    System.out.println("Car " + car.getId() +" has reached the semaphore, now waiting...");
                    Thread.sleep(car.getWaitingTime());
                    System.out.println("Car " + car.getId() + " has waited enough, now driving...");
                }
            };

            /**
             * In acest caz, folosim un semafor care sa contorizeze cate masini ajung in sens.
             *
             */
            case "simple_n_roundabout" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) throws InterruptedException {
                    System.out.println("Car " + car.getId() + " has reached the roundabout, now waiting...");

                    ((SimpleRoundabout) Main.intersection).getMy_semaphore().acquire();
                    System.out.println("Car " + car.getId() + " has entered the roundabout");

                    // waits
                    Thread.sleep(((SimpleRoundabout) Main.intersection).getRound_time());

                    // leaves
                    System.out.println("Car " + car.getId() + " has exited the roundabout after "
                            + ((SimpleRoundabout) Main.intersection).getRound_time()/1000
                            + " seconds");

                    ((SimpleRoundabout) Main.intersection).getMy_semaphore().release();
                }
            };

            /**
             * Pentru acest caz, exista un semafor cu 1 permit pentru fiecare directie.
             *
             */
            case "simple_strict_1_car_roundabout" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) throws InterruptedException {
                    System.out.println("Car " + car.getId() + " has reached the roundabout");

                    int lane = car.getStartDirection();
                    ((Simple1InRoundabout) Main.intersection).getLineSemaphore(lane).acquire();

                    System.out.println("Car " + car.getId() + " has entered the roundabout from lane " + lane);
                    Thread.sleep(((Simple1InRoundabout) Main.intersection).getRound_time());

                    System.out.println("Car " + car.getId() + " has exited the roundabout after "
                            + ((Simple1InRoundabout) Main.intersection).getRound_time()/1000
                            + " seconds");

                    ((Simple1InRoundabout) Main.intersection).getLineSemaphore(lane).release();


                }
            };

            /**
             * Similar cu situatia de mai sus, insa semafoarele pentru directii au permits cate masini
             * sunt permise in sens, iar barieriele sunt folosite pentru a ne asigura ca avem numarul de
             * masini necesare.
             */
            case "simple_strict_x_car_roundabout" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) throws InterruptedException, BrokenBarrierException {
                    System.out.println("Car " + car.getId() + " has reached the roundabout, now waiting...");

                    try {
                        ((SimpleStrictRoundabout) Main.intersection).getBarrier3().await();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }

                    int lane = car.getStartDirection();
                    ((SimpleStrictRoundabout) Main.intersection).getLineSemaphore(lane).acquire();

                    System.out.println("Car " + car.getId() + " was selected to enter the roundabout from lane " + lane);

                    ((SimpleStrictRoundabout) Main.intersection).getBarrier1().await();


                    System.out.println("Car " + car.getId() + " has entered the roundabout from lane " + lane);
                    Thread.sleep(((SimpleStrictRoundabout) Main.intersection).getRound_time());

                    try {
                        ((SimpleStrictRoundabout) Main.intersection).getBarrier2().await();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Car " + car.getId() + " has exited the roundabout after "
                            + ((SimpleStrictRoundabout) Main.intersection).getRound_time()/1000
                            + " seconds");


                    ((SimpleStrictRoundabout) Main.intersection).getLineSemaphore(lane).release();
                }
            };

            /**
             * Similar cu cel de mai sus, semafoarele setate cu permits numarul de masini din fiecare
             * directie.
             */
            case "simple_max_x_car_roundabout" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) throws InterruptedException {
                    // Get your Intersection instance
                    System.out.println("Car " + car.getId() + " has reached the roundabout from lane " + car.getStartDirection());

                    try {
                        sleep(car.getWaitingTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } // NU MODIFICATI

                    // Continuati de aici


                    int lane = car.getStartDirection();
                    ((SimpleXInRoundAbout) Main.intersection).getLineSemaphore(lane).acquire();

                    System.out.println("Car " + car.getId() + " has entered the roundabout from lane " + lane);

                    Thread.sleep(((SimpleXInRoundAbout) Main.intersection).getRound_time());

                    System.out.println("Car " + car.getId() + " has exited the roundabout after "
                            + ((SimpleXInRoundAbout) Main.intersection).getRound_time()/1000
                            + " seconds");

                    ((SimpleXInRoundAbout) Main.intersection).getLineSemaphore(lane).release();

                }
            };

            /**
             * In functie de prioritate, masinile sunt tratate diferit. Pentru cele low
             * verificam daca sunt deja masini in sens, iar daca nu sunt pot trece. Daca totusi
             * sunt, vor astepta pana cand vor fi anuntate de cele cu high priority ca toate au
             * iesit din sens.
             */
            case "priority_intersection" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) throws InterruptedException {
                    // Get your Intersection instance

                    try {
                        sleep(car.getWaitingTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } // NU MODIFICATI

                    // Continuati de aici
                    String priority;
                    if (car.getPriority() == 1) {
                        priority = "low";
                    } else {
                        priority = "high";
                    }

                    if (priority == "low") {
                        System.out.println("Car " + car.getId() + " with " + priority + " priority is trying to enter the intersection..." );
                        AtomicInteger in = ((PriorityIntersection) Main.intersection).getInIntersection();
                        if (in.get() == 0) {
                            System.out.println("Car " + car.getId() + " with " + priority + " priority has entered the intersection");
                        } else {
                            ((PriorityIntersection) Main.intersection).getQueue().put(car.getId());
                            synchronized ((PriorityIntersection) Main.intersection) {
                                ((PriorityIntersection) Main.intersection).wait();

                                System.out.println("Car " + ((PriorityIntersection) Main.intersection).getQueue().take() + " with " + priority + " priority has entered the intersection");
                            }
                        }
                    } else {
                        ((PriorityIntersection) Main.intersection).getInIntersection().incrementAndGet();
                        System.out.println("Car " + car.getId() + " with " + priority + " priority has entered the intersection");
                        Thread.sleep(Constants.PRIORITY_INTERSECTION_PASSING);
                        System.out.println("Car " + car.getId() + " with " + priority + " priority has exited the intersection");

                        int in = ((PriorityIntersection) Main.intersection).getInIntersection().decrementAndGet();
                        if (in == 0) {
                            synchronized ((PriorityIntersection) Main.intersection) {
                                ((PriorityIntersection) Main.intersection).notifyAll();
                            }
                        }
                    }
                }
            };

            /**
             * Am folosit variabilele oferite de pedestrians, finished si pass, pentru a
             * lasa masinile sa treaca sau nu. Pentru a printa o singura data ma folosesc
             * de prevoutput (previous output) pentru a stii ce am printrat inainte. Daca ce
             * am printat inainte este diferit de e doresc sa printez acum, atunci stim ca este
             * ok sa printam.
             */
            case "crosswalk" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    boolean previousGreen = false;
                    boolean previousRed = true;

                    String output = "Car " + car.getId() + " has now red light";
                    String prevout = "";

                    while(!Main.pedestrians.isFinished()) {
                        if (!Main.pedestrians.isPass()) {
                            output = "Car " + car.getId() + " has now green light";
                        } else {
                            output = "Car " + car.getId() + " has now red light";
                        }

                        if (!output.equals(prevout)) {
                            System.out.println(output);
                        }
                        prevout = output;
                    }

                    output = "Car " + car.getId() + " has now green light";

                    if (!output.equals(prevout)) {
                        System.out.println(output);

                    }

                }
            };
            case "simple_maintenance" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) throws InterruptedException, BrokenBarrierException {
                    System.out.println("Car " + car.getId() + " from side number " +
                            car.getStartDirection() + " has reached the bottleneck");

                    if (car.getStartDirection() == 0) {
                        ((MaintenanceIntersection) Main.intersection).getQueue0().add(car);
                    } else {
                        ((MaintenanceIntersection) Main.intersection).getQueue1().add(car);
                    }

                    ((MaintenanceIntersection) Main.intersection).getBarrier().await();

                    synchronized (((MaintenanceIntersection) Main.intersection).getMyMutex()) {
                        int pass = ((MaintenanceIntersection) Main.intersection).getPassing().get();
                        // daca e directia de trecere 0
                        if (pass == 0) {
                            Car takenCar = ((MaintenanceIntersection) Main.intersection).getQueue0().take();
                            System.out.println("Car " + takenCar.getId() + " from side number " +
                                    takenCar.getStartDirection() + " has passed the bottleneck");
                        } else {
                            // daca e directia de trecere 1
                            Car takenCar = ((MaintenanceIntersection) Main.intersection).getQueue1().take();
                            System.out.println("Car " + takenCar.getId() + " from side number " +
                                    takenCar.getStartDirection() + " has passed the bottleneck");
                        }

                        // verificare al carui rand este

                        int in = ((MaintenanceIntersection) Main.intersection).getCarsPassing().incrementAndGet();
                        if (in == ((MaintenanceIntersection) Main.intersection).allowed) {
                            ((MaintenanceIntersection) Main.intersection).getCarsPassing().set(0);

                            if (((MaintenanceIntersection) Main.intersection).getPassing().get() == 0) {
                                ((MaintenanceIntersection) Main.intersection).getPassing().set(1);
                            } else {
                                ((MaintenanceIntersection) Main.intersection).getPassing().set(0);
                            }
                        }
                    }
                }
            };
            case "complex_maintenance" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    
                }
            };


            /**
             * Asteptam ca toate masinile sa ajunga la bariera, iar pentru fiecare directie din care
             * vin, aceste sunt adaugate in blocking queues diferite.
             */
            case "railroad" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) throws InterruptedException {

                    synchronized (((RailroadIntersection) Main.intersection).getMyMutex()) {
                        if (car.getStartDirection() == 0) {
                            ((RailroadIntersection)Main.intersection).getQueue0().add(car);
                        } else {
                            ((RailroadIntersection) Main.intersection).getQueue1().add(car);
                        }
                        System.out.println("Car " + car.getId() + " from side number " + car.getStartDirection() + " has stopped by the railroad");

                    }


                    try {
                        ((RailroadIntersection)Main.intersection).getBarrier().await();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }

                    if (car.getId() == 0) {
                        System.out.println("The train has passed, cars can now proceed");
                    }

                    try {
                        ((RailroadIntersection)Main.intersection).getBarrier2().await();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    Car takenCar;

                    synchronized (((RailroadIntersection) Main.intersection).getMyMutex()) {
                        if (car.getStartDirection() == 0) {
                            takenCar = ((RailroadIntersection)Main.intersection).getQueue0().take();
                        } else {
                            takenCar = ((RailroadIntersection)Main.intersection).getQueue1().take();
                        }
                        System.out.println("Car " + takenCar.getId()
                                + " from side number " + takenCar.getStartDirection() + " has started driving");
                    }


                }
            };
            default -> null;
        };
    }
}
