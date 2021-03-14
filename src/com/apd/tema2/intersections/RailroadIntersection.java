package com.apd.tema2.intersections;

import com.apd.tema2.entities.Car;
import com.apd.tema2.entities.Intersection;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CyclicBarrier;

public class RailroadIntersection implements Intersection {
    CyclicBarrier barrier;
    CyclicBarrier barrier2;
    BlockingQueue<Car> queue0;
    BlockingQueue<Car> queue1;

    Object myMutex;

    public void setRaiload(int size) {
        barrier = new CyclicBarrier(size);
        barrier2 = new CyclicBarrier(size);
        queue0 = new ArrayBlockingQueue<Car>(size);
        queue1 = new ArrayBlockingQueue<Car>(size);

        myMutex = new Object();
    }

    public CyclicBarrier getBarrier() {
        return barrier;
    }

    public CyclicBarrier getBarrier2() {
        return barrier2;
    }

    public BlockingQueue<Car> getQueue0() {
        return queue0;
    }

    public BlockingQueue<Car> getQueue1() {
        return queue1;
    }

    public Object getMyMutex() {
        return myMutex;
    }
}
