package com.m3.csalgorithms.systems.atc;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimpleAtcTest {
    private SimpleAtc atc;

    @BeforeEach
    void setUp() throws Exception {
        atc = new SimpleAtc();
    }

    @AfterEach
    void tearDown() throws Exception {
        atc.clear();
        atc = null;
    }

    @Test
    void planeArrivingWithNoneInQueueCanLand() {
        boolean result = atc.canPlaneLand(1, Instant.now().getEpochSecond());
        assertTrue(result);
    }

    @Test
    void planeArrivingAfterAllInQueueLandCanLand() {
    	long now = Instant.now().getEpochSecond();
        List<Long> requesttimes = Arrays.asList(now-40*60000, now-37*60000, now-33*60000, now-30*60000, now-25*60000, now-22*60000, now-19*60000, now-15*60000, now-10*60000, now-7*60000, now-3*60000);
        atc.populatePlanes(requesttimes, false);
        boolean result = atc.canPlaneLand(20, now+120000);
        assertTrue(result);
    }

    @Test
    void planeArrivingWellAfterComputedLastInQueueCanLand() {
    	long now = Instant.now().getEpochSecond();
        List<Long> requesttimes = Arrays.asList(now-30*60000, now-25*60000, now-22*60000, now-19*60000, now-15*60000, now-10*60000, now-7*60000, now-3*60000, now+60000, now+5*60000, now+8*60000);
        atc.populatePlanes(requesttimes, false);
        boolean result = atc.canPlaneLand(20, now+780000);
        assertTrue(result);
    }

    @Test
    void planeArrivingWellAfterSavedLastInQueueCanLand() {
    	long now = Instant.now().getEpochSecond();
        List<Long> requesttimes = Arrays.asList(now-30*60000, now-25*60000, now-22*60000, now-19*60000, now-15*60000, now-10*60000, now-7*60000, now-3*60000, now+60000, now+5*60000, now+8*60000);
        atc.populatePlanes(requesttimes, true);
        boolean result = atc.canPlaneLand(20, now+780000);
        assertTrue(result);
    }

    @Test
    void planeArrivingQuicklyAfterSavedLastInQueueCannotLand() {
    	long now = Instant.now().getEpochSecond();
        List<Long> requesttimes = Arrays.asList(now-30*60000, now-25*60000, now-22*60000, now-19*60000, now-15*60000, now-10*60000, now-7*60000, now-3*60000, now+60000, now+5*60000, now+8*60000);
        atc.populatePlanes(requesttimes, true);
        boolean result = atc.canPlaneLand(20, now+540000);
        assertFalse(result);
    }

}
