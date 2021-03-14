package com.apd.tema2.intersections;

import com.apd.tema2.Main;
import com.apd.tema2.entities.Intersection;

import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class SimpleStrictRoundabout implements Intersection {
    int cars_in_direction;
    int round_time;
    Semaphore round_semaphore;
    ArrayList<Semaphore> in_semaphore;
    CyclicBarrier barrier;
    CyclicBarrier barrier2;
    CyclicBarrier barrier3;

    public void setRoundAbout(int cars_in_direction, int round_time, int cars_in_semaphore) {
        this.cars_in_direction = cars_in_direction;
        this.round_time = round_time;

        barrier = new CyclicBarrier(cars_in_semaphore * cars_in_direction);
        barrier2 = new CyclicBarrier(cars_in_semaphore * cars_in_direction);
        barrier3 = new CyclicBarrier(Main.carsNo);

        round_semaphore = new Semaphore(-cars_in_semaphore + 1);

        in_semaphore = new ArrayList<>();

        for (int i = 0; i < cars_in_semaphore; i++) {
            in_semaphore.add(new Semaphore(cars_in_direction));
        }
    }

    public Semaphore getRound_semaphore() {
        return round_semaphore;
    }

    public Semaphore getLineSemaphore(int i) {
        return in_semaphore.get(i);
    }

    public int getRound_time() {
        return round_time;
    }

    public CyclicBarrier getBarrier1() {
        return barrier;
    }

    public CyclicBarrier getBarrier2() {
        return barrier2;
    }

    public CyclicBarrier getBarrier3() {
        return barrier3;
    }
}
