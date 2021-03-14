package com.apd.tema2.intersections;

import com.apd.tema2.Main;
import com.apd.tema2.entities.Car;
import com.apd.tema2.entities.Intersection;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class MaintenanceIntersection implements Intersection {
    BlockingQueue<Car> queue0;
    BlockingQueue<Car> queue1;
    AtomicInteger passing = new AtomicInteger(0);
    AtomicInteger carsPassing = new AtomicInteger(0);
    CyclicBarrier barrier;
    public int allowed;

    Object myMutex;

    public void setMaintenance(int X) {
        queue0 = new ArrayBlockingQueue<>(Main.carsNo);
        queue1 = new ArrayBlockingQueue<>(Main.carsNo);
        allowed = X;
        myMutex = new Object();
        barrier = new CyclicBarrier(Main.carsNo);
    }

    public CyclicBarrier getBarrier() {
        return barrier;
    }

    public BlockingQueue<Car> getQueue0() {
        return queue0;
    }

    public BlockingQueue<Car> getQueue1() {
        return queue1;
    }

    public AtomicInteger getPassing() {
        return passing;
    }

    public AtomicInteger getCarsPassing() {
        return carsPassing;
    }

    public Object getMyMutex() {
        return myMutex;
    }
}
