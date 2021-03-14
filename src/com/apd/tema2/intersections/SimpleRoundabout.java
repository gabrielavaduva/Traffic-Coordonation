package com.apd.tema2.intersections;

import com.apd.tema2.entities.Intersection;

import java.util.concurrent.Semaphore;

public class SimpleRoundabout implements Intersection {
    int max_cars;
    int round_time;
    Semaphore my_semaphore;

    public int getMax_cars() {
        return max_cars;
    }

    public int getRound_time() {
        return round_time;
    }

    public Semaphore getMy_semaphore() {
        return my_semaphore;
    }

    public void setRoundabout(int max_cars, int round_time) {
        this.max_cars = max_cars;
        this.round_time = round_time;
        my_semaphore = new Semaphore(max_cars);
    }

    public void acquireSemaphore() throws InterruptedException {
        my_semaphore.acquire();
    }

    public void releaseSemaphore() {
        my_semaphore.release();
    }
}
