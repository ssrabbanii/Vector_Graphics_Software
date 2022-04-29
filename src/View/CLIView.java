package hk.edu.polyu.comp.comp2021.Main.View;

import hk.edu.polyu.comp.comp2021.Main.Controller.Controller;
import hk.edu.polyu.comp.comp2021.Main.Controller.Logger;
import javafx.application.Platform;

import java.io.IOException;
import java.util.Scanner;

/**
 * CLI view to handle command line interface
 */
public class CLIView implements Runnable {

    /**
     * Sleep duration before taking input
     */
    public static final int MILLIS = 150;

    /**
     * @return switches between file input/command input
     */
    public static Boolean getToggleFlag() {
        return toggleFlag;
    }

    /**
     * @param toggleFlag sets toggle flag to true/false
     */
    public static void setToggleFlag(Boolean toggleFlag) {
        CLIView.toggleFlag = toggleFlag;
    }

    private  static volatile    Boolean toggleFlag=false;

    /**
     *  instance of Controller class stored which will be used to send inputs
     */
    private Controller controller;
    /**
     * Instance of Main view Clevis stored which will be used to keep both views in sync
     */
    private Clevis clevis;








    /**
     * REMAINING
     */



    private Boolean shouldStop=false;

    /**
     * @param controller R
     * @param clevis R
     */
    public CLIView(Controller controller, Clevis clevis) {
        this.controller = controller;
        this.clevis=clevis;
        Thread thread= new Thread(this,"InputHandler");
        thread.start();


    }

    /**
     * REM
     */
    public void interrupt(){
        shouldStop=true;
    }




//    Latest Version 10 Nov 1:52
    @Override
    public void run() {


            Scanner scanner= new Scanner(System.in);

            while (!Controller.getExitFlag()){
                if (shouldStop) break;
                System.out.print("Enter Command: ");
                String message=scanner.nextLine();
                if (!Controller.getExitFlag()){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            // Update UI here.
                            controller.parseInput(message);
                            clevis.refresh();
                        }
                    });
                }
                else{
                    Logger.setHtmlFileName(message);

                    break;

                }
                try {
                    Thread.sleep(MILLIS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
            if (shouldStop){return;}
            if (!CLIView.getToggleFlag()){
                System.out.println("Enter HTML: ");
                Logger.setHtmlFileName(scanner.nextLine());
            }

            if (shouldStop){
                System.out.println("Restored Condition, type command again"); return;}
            System.out.println("Enter Text: ");
            Logger.setTxtFileName(scanner.nextLine());
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    // Update UI here.
                    try {
                        Logger.outPut();
                        clevis.closeWindowRequest();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        }















        }




