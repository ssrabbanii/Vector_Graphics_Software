module PROJ.CLEVIS {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires junit;
    //requires org.junit.jupiter.api;
    //requires junit;


    opens hk.edu.polyu.comp.comp2021.Main;
    opens hk.edu.polyu.comp.comp2021.Main.Controller;
    opens hk.edu.polyu.comp.comp2021.Main.View;
    opens hk.edu.polyu.comp.comp2021.Main.Model;
}