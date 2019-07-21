package com.m3.csalgorithms.systems.atc;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
 * There is only one runway in an airport.
 * There should be some time gap between one airplane landing and other airplane coming(example 3min).
 * Eg: There is a plane landing at 10:00 AM and other plane is in the request to land at 10:03 AM.
 * If there is a request that comes for 10:01 it should not be accepted.
 * There will be multiple requests coming(with time) and we should be able to determine if we can handle the request or not
 * to land the plane on the runway.
 */
public class SimpleAtc {
    public static final long DELAY = 180000;

    private Queue<Airplane> requestQ = new LinkedList<Airplane>();
    private Airplane lastinQ = null;

    public boolean canPlaneLand(int planeid, long requestlandingtime) {
        Airplane plane = new Airplane(planeid).landingRequest(requestlandingtime);
        long timenow = Instant.now().getEpochSecond();
        landPlanes(timenow);
        if (lastinQ == null) { // Q traversal will happen in FIFO order
            System.out.println("Need to compute last in queue ...");
            synchronized (requestQ) {
                for (Airplane ap : requestQ) {
                    lastinQ = ap;
                }
            }
        } else {
            System.out.println("Last in queue already computed");
        }
        if (lastinQ == null) { // permit
            requestQ.offer(plane);
            lastinQ = plane;
            return true;
        }
        if (lastinQ.requestTime() + DELAY <= requestlandingtime) {
            requestQ.offer(plane);
            lastinQ = plane;
            return true;
        }
        return false;
    }

    public void landPlanes(long timenow) {
        while (!requestQ.isEmpty() && timenow > requestQ.peek().requestTime()) {
            Airplane plane = requestQ.poll();
            System.out.println("Landed plane [" + plane.id() + "] at [" + plane.requestTime() + "]");
        }
    }

    public void clear() {
        while (requestQ.poll() != null);
    }

    void populatePlanes(List<Long> requestedtimes, boolean setlastone) {
        for (int i = 0; i < requestedtimes.size(); i++) {
            Airplane plane = new Airplane(i+1).landingRequest(requestedtimes.get(i));
            requestQ.offer(plane);
            if (setlastone && i == (requestedtimes.size() - 1)) {
                lastinQ = plane;
            }
        }
    }

    static class Airplane {
        private final int _id;
        private long _requestTime;

        Airplane(int theid) {
            _id = theid;
        }

        Airplane landingRequest(long datetimemillis) {
            _requestTime = datetimemillis;
            return this;
        }

        public int id() { return _id; }
        public long requestTime() { return _requestTime; }
    }
}
