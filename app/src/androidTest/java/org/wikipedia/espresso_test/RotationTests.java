package org.wikipedia.espresso_test;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.fail;

public class RotationTests extends BaseTestClass{


    @Before
    public void setUp(){
        startActivity = myActivityRule.getActivity();
    }

    @Test
    public void testOpenToC_rotatePhone(){
        fail("not implemented");
    }

    @Test
    public void testOpenTab_rotatePhone(){
        fail("not implemented");
    }
}
