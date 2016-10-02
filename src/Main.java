/*
//This program takes user a name and gender from the user,
//searches the given files for that name and outputs the data as console text and a histogram
// Created by Aaron Renfroe 10/1/2016
//CalBaptist University SE HW 3
*/

import java.util.*;
import java.io.*;
import java.awt.*;

public class Main {
    // NameObject...Contains Name and Gender used to return Name and Gender from another a method
    static final class NameObject{
        private final String name;
        private final String gender;

        public NameObject(String name, String gender){
            this.name = name;
            this.gender = gender;
        }

        public String getName(){
            return name;
        }

        public String getGender(){
            return gender;
        }

    }
    
    static final int HEIGHT = 560;
    static final int WIDTH = 780;
    static final int STARTING_YEAR = 1890;

    public static void main(String[] args) {

        printIntro();

        NameObject nameAndGender = getSearchCriteria();
        String results = searchFile(nameAndGender, "names.txt", false);
        System.out.println(results);
        if (!results.contains("Sorry ")){

            String meaning = searchFile(nameAndGender, "meanings.txt", true);
            System.out.println(meaning);
            grapher(results,meaning);
        }

    }

    // prints the intro paragraph
    public static void printIntro(){
        // made only one call for efficiency
        System.out.println("This program allows you to search through the\n" +
                "data from the Social Security Administration\n" +
                "to see how popular a particular name has been\n" +
                "since 1890\n");
    }
    // searches a file for a name and/or gender
    public static NameObject getSearchCriteria(){
        Scanner console = new Scanner(System.in);
        System.out.print("Name: ");
        String name = console.next();
        name = name.toUpperCase().charAt(0) + name.toLowerCase().substring(1);
        System.out.print("Gender (M or F): ");
        String gender = console.next();
        //System.out.println("\n" + name + " " + gender);
        return new NameObject(name, gender);
    }
    // searches the given file for name and returns the results
    public static String searchFile(NameObject nameAndGender, String fileName, boolean meaning){

        String wantedName = nameAndGender.getName().toLowerCase();
        String wantedGender = nameAndGender.getGender().toLowerCase();

        File names1 = new File (fileName);
        try {

            Scanner nameList = new Scanner(names1);
            while (nameList.hasNextLine()){
                String results = nameList.nextLine();
                Scanner wbw = new Scanner(results);
                String dataFromFile = wbw.next().toLowerCase();

                if ((dataFromFile.contains(wantedName)) && (dataFromFile.length() == wantedName.length())){

                    String genderTry = wbw.next().toLowerCase();

                    if (meaning){
                        return results;
                    }
                    else if (genderTry.charAt(0) == wantedGender.charAt(0)) {
                        return (results);
                    }
                }

            }
            return ("Sorry \""+ wantedName + "\" was not found.");
        }
        catch (FileNotFoundException ex){
            return("File Not Found");
        }

    }
    // Graphs the data from the results using two helper methods
    public static void grapher(String results, String meaning){

        Graphics g = graphSetUp(meaning);
        drawBars(g, results);
    }
    // sets the drawing panel and returns Graphics
    public static Graphics graphSetUp( String meaning){
        DrawingPanel panel = new DrawingPanel(WIDTH,HEIGHT);
        Graphics g = panel.getGraphics();
        panel.setBackground(Color.WHITE);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0,0,WIDTH,30);
        g.fillRect(0,HEIGHT-30,WIDTH,30);
        g.setColor(Color.BLACK);
        g.drawString(meaning,0,16);
        g.drawLine(0,30,WIDTH,30);
        g.drawLine(0,HEIGHT-30,WIDTH,HEIGHT-30);
        //g.drawLine(0,HEIGHT,WIDTH,HEIGHT);


        return g;
    }
    //Draws the Bars on the Histogram
    public static void drawBars(Graphics g, String results){
        int count = 0;
        Scanner s = new Scanner(results);
        s.next();
        s.next();
        while (s.hasNext()){

            int rank = Integer.parseInt(s.next());

            g.setColor(Color.DARK_GRAY);
            if (rank == 0){
                g.drawString("0",count * 60,HEIGHT-30);
            }

            else{         // start x,    start y,        R->, Down v //
                double y = 30 +((rank/1000.0)*(HEIGHT-30));
                double length = (HEIGHT-29)-y;


                g.fillRect(count * 60, (int) y,30, (int) length);
                g.drawString(Integer.toString(rank),count*60,(int) y);
            }
            g.setColor(Color.BLACK);
            g.drawString(Integer.toString(STARTING_YEAR + count*10), count*60,HEIGHT-10);
            count ++;

        }

    }


}
