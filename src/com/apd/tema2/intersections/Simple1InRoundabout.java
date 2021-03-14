package com.apd.tema2.intersections;

import com.apd.tema2.Main;
import com.apd.tema2.entities.Intersection;

import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Simple1InRoundabout implements Intersection {
    int max_cars;
    int round_time;
    Semaphore round_semaphore;
    ArrayList<Semaphore> in_semaphore;


    public void setRoundAbout(int max_cars, int round_time, int cars_in_semaphore) {
        this.max_cars = max_cars;
        this.round_time = round_time;


        round_semaphore = new Semaphore(-cars_in_semaphore + 1);

        in_semaphore = new ArrayList<>();

        for (int i = 0; i < cars_in_semaphore; i++) {
            in_semaphore.add(new Semaphore(1));
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
}
