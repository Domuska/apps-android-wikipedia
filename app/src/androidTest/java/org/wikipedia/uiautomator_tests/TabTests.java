package org.wikipedia.uiautomator_tests;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Until;

import org.junit.Test;

public class TabTests  extends BaseTestClass{

    @Test
    public void testOpenArticleInNewTab(){

        //        int headerHeight = device.findObject(
//                By.res("org.wikipedia.alpha:id/view_article_header_text")).
//        Point startPoint = new Point(device.getDisplayWidth()/2, device.getDisplayHeight()/5);
//        Double endY = device.getDisplayHeight()*0.8;
//        Point endPoint = new Point(startPoint.x, endY.intValue());

//        device.drag(startPoint.x, startPoint.y, endPoint.x, endPoint.y, 4);
//        device.drag(endPoint.x, endPoint.y, startPoint.x, startPoint.y, 5);

        //tällä tavalla linkin klikkaaminen toimii kyllä, en tiedä miksi mutta se toimii.
        //uiautomatorviewerin dumpissa kyllä näkyy elementit näin. Voisiko sivua scrollata...?
        device.findObject(By.desc("artificial intelligence")).click();

        device.wait(Until.findObject(
                By.res("org.wikipedia.alpha:id/link_preview_title")), GENERAL_TIMEOUT);
    }
}
