package hk.edu.polyu.comp.comp2021.Main;

//import Main.JavaFxJUnit4ClassRunner;
import hk.edu.polyu.comp.comp2021.Main.Model.RectangleShape;
import javafx.scene.Group;
import javafx.scene.Scene;
import org.junit.Test;
import org.junit.runner.RunWith;
//import Main.Controller.Controller;
//import Main.View.Clevis;
//import javafx.application.Application;
//import javafx.scene.Node;
//import javafx.stage.Stage;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//
//import java.lang.reflect.Executable;

import static org.junit.Assert.*;

/**
 * This is a sample test class for java fx tests.
 *
 *
 */

@RunWith(JavaFxJUnit4ClassRunner.class)
public class ApplicationTestBase
{

    /**
     * Daft normal test.
     */
    @Test
    public void testNormal()
    {
        assertTrue(true);
    }

    /**
     * Test which would normally fail without running on the JavaFX thread.
     */
    @Test
    public void testNeedsJavaFX()
    {
        Scene scene = new Scene(new Group());
        assertTrue(true);
        new RectangleShape().parseInput(new String[]{"rectangle","1","20","20","50","60"});
    }
}