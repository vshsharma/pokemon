//package com.pokedex.pokemon
//
//import androidx.test.espresso.IdlingRegistry
//import androidx.test.espresso.idling.CountingIdlingResource
//import androidx.test.ext.junit.rules.ActivityScenarioRule
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import androidx.test.filters.LargeTest
//import dagger.hilt.android.testing.HiltAndroidRule
//import dagger.hilt.android.testing.HiltAndroidTest
//import org.junit.After
//import org.junit.Assert.*
//import org.junit.Before
//import org.junit.Rule
//
//import org.junit.Test
//import org.junit.runner.RunWith
//import javax.inject.Inject
//
//@HiltAndroidTest
//@Config(application = PokedexApplication::class)
//@LargeTest
//@RunWith(AndroidJUnit4::class)
//class MainActivityTest {
//    @get:Rule
//    var hiltRule = HiltAndroidRule(this)
//
////    @Inject
////    lateinit var analyticsAdapter: AnalyticsAdapter
//
//    @Rule
//    @JvmField
//    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)
//
//
//    @Before
//    fun registerIdlingResource(){
//        hiltRule.inject()
//        IdlingRegistry.getInstance().register(CountingIdlingResource("GLOBAL"))
//    }
//
//    @After
//    fun unregisterIdlingResource(){
//        IdlingRegistry.getInstance().register(CountingIdlingResource("GLOBAL"))
//    }
//}