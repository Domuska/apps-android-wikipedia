package org.wikipedia.TestingHelpers;

import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.idling.CountingIdlingResource;

public class TabAnimationIdlingResource {
    private static final CountingIdlingResource idlingResource = new CountingIdlingResource("search");

    public static void incrementResource(){
        idlingResource.increment();
    }

    public static void decementResource(){
        idlingResource.decrement();
    }

    public static IdlingResource getIdlingResource(){
        return idlingResource;
    }
}
