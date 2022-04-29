package hk.edu.polyu.comp.comp2021.Main.Controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Logs commands and outputs files during exit
 */
public class Logger {
    private static ArrayList<String> log= new ArrayList<>();
    private static String lastCommand="";

    /**
     * Fetches the last command
     * @return last command calue
     */
    public static String getLastCommand() {
        return lastCommand;
    }

    /**
     * @param lastCommand Updates last command upon new inputs
     */
    public static void setLastCommand(String lastCommand) {
        Logger.lastCommand = lastCommand;
    }

    /**
     * @param htmlFileName HTML file name obtained WITHOUT format
     */
    public static void setHtmlFileName(String htmlFileName) {
        Logger.htmlFileName = htmlFileName;
    }

    /**
     * html filename to be outputted
     */
    private static String htmlFileName=null;

    /**
     * @param txtFileName updates txt filename to be outputted
     */
    public static void setTxtFileName(String txtFileName) {
        Logger.txtFileName = txtFileName;
    }

    /**
     * txt filename to be outputted
     */
    private static String txtFileName=null;

    /**
     * Outputs the files in appropriate name and format
     * @throws IOException REMAINING
     */
    public static void outPut() throws IOException {
        System.out.println(log);

        if ((htmlFileName==null) || (txtFileName)==null) throw new IllegalArgumentException();



        create_file(htmlFileName,".html");

        //Writing Text File
        String txtLog="";
        for (String item:log){
            txtLog= txtLog +item+"\n";
        }
        write_file(create_file(txtFileName,".txt"),txtLog);

        //Writing HTML FIle
        write_html(htmlFileName+".html",log);




    }

    /**
     * Logs input
     * @param command command to be logged in
     */
    public static void log (String command){
        log.add(command);
        setLastCommand(command);
    }



    private static String create_file(String name,String type){
        try {
            File file = new File(name+type);
            file.createNewFile();
            return name+type;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }
    private static void write_file(String fileName,String message){
        try {
            FileWriter write = new FileWriter(fileName);
            write.write(message);
            write.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * @param fileName name of HTML file
     * @param list log list
     * @throws IOException if error in writing
     */
    private static void write_html(String fileName,ArrayList<String> list) throws IOException {
        File f = new File(fileName);
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        bw.write("<html><body><h1>Blah, Blah!</h1>");
        bw.write("<style>\n" +
                "table, th, td {\n" +
                "  border: 1px solid black;\n" +
                "  border-collapse: collapse;\n" +
                "}\n" +
                "</style>");
        bw.write("<table>");
        bw.write("<tr>"+"<th>"+"Index"+"</th>"+"<th>"+"Command"+"</th>"+"</tr>");
        for (int i=0;i<list.size();i++) {
            bw.write("<tr>"+"<td>"+(i+1)+"</td>"+"<td>"+list.get(i)+"</td>"+"</tr>");
        }
        bw.write("</table>");
        bw.write("</body></html>");
        bw.close();
    }


}
