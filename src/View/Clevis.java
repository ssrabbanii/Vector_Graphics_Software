package hk.edu.polyu.comp.comp2021.Main.View;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import hk.edu.polyu.comp.comp2021.Main.Controller.Controller;
import hk.edu.polyu.comp.comp2021.Main.Controller.Logger;


import java.io.IOException;
import java.util.*;

/**
 * REMAINING
 */
public class Clevis extends Application {


    /**
     * Padding Value for Bottom Bar
     */
    public static final int padding_value = 15;
    /**
     * Vertical Box Spacing between components
     */
    public static final int vbox_spacing = 50;
    /**
     * Horizontal Box Spacing between components
     */
    public static final int hbox_spacing = 30;
    /**
     * Horizontal Box Spacing between components in Bottom Bar
     */
    public static final int hbox2_spacing = 20;
    /**
     * Scene Width value
     */
    public static final int scene_width = 300;
    /**
     * Scene height value
     */
    public static final int scene_height = 275;

    /**
     * Min height of scene
     */
    public static final int min_height = 600;
    private final double MAX_FONT_SIZE = 20.0;
    private final double TEXT_INPUT_WIDTH=450;
    //UI Components

    /**
     * GUI Button for Undo
     */
    private Button undoButton= new Button("Undo <-");
    /**
     * GUI Button for Redo
     */
    private Button redoButton= new Button("Redo ->");
    private TextField textInput= new TextField("");
    /**
     * display  GUI label
     */
    private static Label viewLabel= new Label("Welcome to CLEVIS");




    //Windows & Scenes
    /**
     * Declaring view handler class Controller
     */
    private Controller controller=new Controller(this);
    private CLIView CLIView =new CLIView(controller,this);
    private  Stage window;
    private  Scene scene1;

    private static Pane root = new Pane();




    //Layouts
    private HBox bottomBar;
    private BorderPane border;





    @Override
    public void start(Stage primaryStage) {
        window=primaryStage;


//        Static Components
        viewLabel.setFont(new Font(MAX_FONT_SIZE));
        viewLabel.setTextFill(Color.web("#FFFFFF"));

        textInput.setPromptText("Enter Commands Here: ");
        textInput.setPrefWidth(TEXT_INPUT_WIDTH);


        //Adjusting Bottom Bar
        bottomBar= new HBox(hbox2_spacing);
        bottomBar.getChildren().addAll(textInput,undoButton,redoButton,viewLabel);
        bottomBar.setPadding(new Insets(padding_value, padding_value, padding_value, padding_value));
        bottomBar.setStyle("-fx-background-color: #161E54;");

        //Adjusting the Center Pane & Scroll View

        ScrollPane scroll= new ScrollPane();
        scroll.setPadding(new Insets(10,10,10,10));
        scroll.setContent(root);


        //Creating Layouts
        border= new BorderPane();
        border.setBottom(bottomBar);
        border.setCenter(scroll);



//ACTION METHODS--------------------------------------------------------------------------------------------

        textInput.setOnKeyPressed( event -> {
            if( event.getCode() == KeyCode.ENTER ) {
                System.out.println(textInput.getText());
                viewLabel.setText(textInput.getText());
                if (textInput.getText().equals("quit")) CLIView.setToggleFlag(true);
                controller.parseInput(textInput.getText());
                textInput.clear();
                update(window,Controller.getNodesList(),root);
                refresh();
                System.out.printf("Enter Command: ");

            }
        } );

        undoButton.setOnAction(e-> {
                    Clevis.displayPrompt("Undo performed");
                    System.out.println("Undo performed");
                    update(window,Controller.undo(),root);
        });
        redoButton.setOnAction(e-> {
                    Clevis.displayPrompt("Redo performed");
                    System.out.println("Redo performed");
                    update(window,Controller.redo(),root);
        });

        window.setOnCloseRequest(e -> {
            e.consume();
            exitScreen(window);
            CLIView.setToggleFlag(true);
            Controller.setExitFlag(true);
        });

//---------------------------------------------------------------------------------------------------------




        //Finalizing Scene & Stage
        scene1 =new Scene(border,00, scene_height);
        window.setMinHeight(min_height);
        window.setMinWidth(1000);
        window.setTitle("CLEVIS");
        window.setScene(scene1);
        window.show();


    }

    /**
     * Exit request handler.
     */
    private void exitScreen (Stage window){

        refresh();
        System.out.println("Preparing for Exit...");
        System.out.println("Toggle is "+ CLIView.getToggleFlag());
        if (CLIView.getToggleFlag() || !Logger.getLastCommand().equals("quit")) System.out.println("Enter HTML fw");






        Label welcomeLabel= new Label("You are exiting CLEVIS");
        Label infoLabel= new Label("Enter file names to be outputted...");
        welcomeLabel.setFont(new Font(MAX_FONT_SIZE));
        infoLabel.setFont(new Font(MAX_FONT_SIZE));


        Label txt_s=new Label(".txt");
        Label html_s=new Label(".html");
        txt_s.setTextFill(Color.web("#FFFFFF"));
        html_s.setTextFill(Color.web("#FFFFFF"));

        TextField html=new TextField();
        TextField notepad= new TextField();
        Button exitButton= new Button("Exit");
        Button stayButton= new Button("Go Back");

        HBox hbox= new HBox(hbox_spacing);
        hbox.getChildren().addAll(html,html_s,notepad,txt_s,exitButton,stayButton);
        hbox.setPadding(new Insets(10,10,10,10));
        VBox vbox= new VBox(vbox_spacing);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(welcomeLabel,infoLabel);
        BorderPane border2= new BorderPane();
        hbox.setStyle("-fx-background-color: #161E54;");
        vbox.setStyle("-fx-background-color: #B4FE98");

        border2.setCenter(vbox);
        border2.setBottom(hbox);
        Scene scene2= new Scene(border2, min_height, min_height);
        window.setScene(scene2);
        window.show();

        exitButton.setOnAction(e->{
            String html_name=html.getText();
            String txt_name=notepad.getText();

            if (html_name.isEmpty() || txt_name.isEmpty()){
                welcomeLabel.setText("Please Fill both the fields");

            }else{
                try {
                    Logger.setTxtFileName(txt_name);
                    Logger.setHtmlFileName(html_name);
                    Logger.outPut();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                window.close();
                System.exit(0);
            }
        });

        window.setOnCloseRequest(e -> {

            e.consume();
            welcomeLabel.setText("Fill the two fields and then press Exit");

        });

        stayButton.setOnAction(e-> {
            window.setScene(scene1);
            Controller.setExitFlag(false);
            CLIView.setToggleFlag(false);
            CLIView.interrupt();
            CLIView = new CLIView(controller,this);



        });


    }

    /**
     * Initiates Window (Programme) close
     */
    public void closeWindowRequest(){
        System.out.println("I got a request to close window");
        window.close();
        System.exit(0);
    }

    /**
     * @param message Info popup view  window for list/listAll command
     */
    public static void infoScene(String message){
        Label label= new Label(message);
        ScrollPane scroll2= new ScrollPane(label);
        Scene scene2 = new Scene(scroll2, scene_width, scene_height);
        Stage secondStage= new Stage();
        secondStage.setScene(scene2);
        secondStage.show();


    }


    /**
     * @param args Main class
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Updates the screen to show changes ( if any)
     * @param primaryStage Primary Window for view/interface
     * @param items List of nodes(shapes) to be viewed
     * @param pane White view bar where shapes are drawn
     */
    public static void update(Stage primaryStage,ArrayList<Node> items,Pane pane){
        pane.getChildren().clear();
        for (Node item:items  ) {
            pane.getChildren().add(item);
        }
        primaryStage.show();

    }

    /**
     * @param message message to be displayed into GUI
     */
    public static void  displayPrompt(String message){
        viewLabel.setText(message);
    }

    /**
     * Inititates update of screen on request from other classes
     */
    public void refresh(){
        Clevis.update(window,Controller.getNodesList(),Clevis.root);

    }

    /**
     * Initiates programme exit
     */
    public  void exitRequest(){
        exitScreen(window);

    }





}


