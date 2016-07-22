package org.wikipedia.espresso_test.Utilities;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.widget.EditText;

import org.wikipedia.R;
import org.wikipedia.search.SearchResult;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

public class Utils {

    public static void searchAndOpenArticleWith(final String name, String articleToString, Activity currentActivity){
        String searchBarHint = currentActivity.getString(R.string.search_hint);
        onView(allOf(withText(searchBarHint), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))).perform(click());

        //have to do a dirty trick like this since otherwise IdlingResource will stop after each letter
        //to wait for response to network call
        final EditText searchField = (EditText) currentActivity.findViewById(R.id.search_src_text);
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                searchField.setText(name);
            }
        });

        //open the article in results
        onData(allOf(instanceOf(SearchResult.class), hasToString(is(articleToString))))
                .inAdapterView(withId(R.id.search_results_list))
                .perform(click());
    }
}
