package com.apd.tema2.factory;

import com.apd.tema2.entities.Intersection;
import com.apd.tema2.intersections.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Prototype Factory: va puteti crea cate o instanta din fiecare tip de implementare de Intersection.
 */
public class IntersectionFactory {
    private static Map<String, Intersection> cache = new HashMap<>();

    static {
        // cache.put("name_intersection", new Intersection() {
        // });
        cache.put("simple_intersection", new SimpleIntersection() {
        });
        cache.put("simple_roundabout", new SimpleRoundabout(){});
        cache.put("simple_1in", new Simple1InRoundabout(){});
        cache.put("simple_x_in", new SimpleXInRoundAbout(){});
        cache.put("simple_strict_x_in", new SimpleStrictRoundabout(){});
        cache.put("priority_intersection", new PriorityIntersection());
        cache.put("crosswalk", new CrosswalkIntersection());
        cache.put("railroad", new RailroadIntersection());
        cache.put("maintenance", new MaintenanceIntersection());
    }

    public static Intersection getIntersection(String handlerType) {
        return cache.get(handlerType);
    }

}
