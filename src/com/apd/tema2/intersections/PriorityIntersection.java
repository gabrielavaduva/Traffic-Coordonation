package com.apd.tema2.intersections;

import com.apd.tema2.entities.Intersection;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class PriorityIntersection implements Intersection {
    AtomicInteger inIntersection = new AtomicInteger(0);
    int highPriorityCars;
    int lowPriorityCars;
    BlockingQueue<Integer> queue;

    public void setPriorities(int highPriorityCars, int lowPriorityCars) {
        this.highPriorityCars = highPriorityCars;
        this.lowPriorityCars = lowPriorityCars;
        queue = new ArrayBlockingQueue<Integer>(lowPriorityCars);
    }

    public AtomicInteger getInIntersection() {
        return inIntersection;
    }

    public BlockingQueue<Integer> getQueue() {
        return queue;
    }
}
