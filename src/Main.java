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

    static final class NameObject {
        private String name;
        private String gender;

        private NameObject(String name, String gender){
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

    static final String NAME_FILE = "names.txt";
    static final int STARTING_YEAR = 1890;
    static final int BAR_SEP = 60;
    static final int NAV_H = 30;
    static final int PANEL_WIDTH = 780;
    static final int PANEL_HEIGHT = 500+2*NAV_H;


    //Main
    public static void main(String[] args) {

        NameObject nameAndGender = getSearchCriteria();
        String results = searchFile(nameAndGender, NAME_FILE);

        if (results != "" ){

            grapher(results,searchFile(nameAndGender, "meanings.txt"));

        }

    }

    // searches a file for a name and/or gender
    public static NameObject getSearchCriteria(){

        System.out.println("This program allows you to search through the\n" +
                "data from the Social Security Administration\n" +
                "to see how popular a particular name has been\n" +
                "since " + STARTING_YEAR+".\n");

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
    public static String searchFile(NameObject nameAndGender, String fileName){

        String wantedName = nameAndGender.getName().toLowerCase();
        String wantedGender = nameAndGender.getGender().toLowerCase();

        try {

            Scanner nameList = new Scanner(new File (fileName));
            String results;

            while (nameList.hasNextLine()){
                results = nameList.nextLine();
                Scanner singleWord = new Scanner(results);
                String dataFromFile = singleWord.next().toLowerCase();

                if ((dataFromFile.contains(wantedName)) && (dataFromFile.length() == wantedName.length())){

                    String genderTry = singleWord.next().toLowerCase();

                    if (genderTry.charAt(0) == wantedGender.charAt(0)) {
                        System.out.println(results);
                        return (results);
                    }
                }
            }

            System.out.println("Sorry \""+ wantedName + "\" was not found.");

            return "";
        }
        catch (FileNotFoundException ex){
            return("File Not Found" + ex);
        }

    }

    // Graphs the data from the results of the search
    public static void grapher(String results, String meaning){

        DrawingPanel panel = new DrawingPanel(PANEL_WIDTH,PANEL_HEIGHT);
        panel.setBackground(Color.WHITE);
        Graphics g = panel.getGraphics();

        graphSetup(g, meaning);

        drawBars(g, results, PANEL_HEIGHT);
        g.drawLine(0,PANEL_HEIGHT-NAV_H,PANEL_WIDTH,PANEL_HEIGHT-NAV_H);

    }

    // helper method used to set up the graph
    public static void graphSetup(Graphics g, String meaning){

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0,0,PANEL_WIDTH,NAV_H);
        g.fillRect(0,PANEL_HEIGHT-NAV_H,PANEL_WIDTH,NAV_H);
        g.setColor(Color.BLACK);
        g.drawString(meaning,0,16);
        g.drawLine(0,NAV_H,PANEL_WIDTH,NAV_H);
        g.drawLine(0,PANEL_HEIGHT-NAV_H,PANEL_WIDTH,PANEL_HEIGHT-NAV_H);
        
    }

    //Helper Method to Draw the Bars on the Histogram
    public static void drawBars(Graphics g, String results, int PANEL_HEIGHT){
        int decades = 0;
        int barWidth = BAR_SEP/2;

        Scanner s = new Scanner(results);
        s.next();
        s.next();
        while (s.hasNext()){

            int rank = Integer.parseInt(s.next());

            if (rank == 0){
                g.setColor(Color.BLACK);
                g.drawString("0",decades * BAR_SEP,PANEL_HEIGHT-NAV_H);
            }

            else{
                double y = NAV_H +((rank/1000.0)*(500));
                double length = (PANEL_HEIGHT-(NAV_H)+1)-y;

                g.setColor(Color.BLACK);
                g.drawString(Integer.toString(rank),decades*BAR_SEP,(int) y);
                g.setColor(Color.GREEN);
                g.fillRect(decades * BAR_SEP, (int) y,barWidth, (int) length);

            }
            g.setColor(Color.BLACK);
            g.drawString(Integer.toString(STARTING_YEAR + decades*10), decades*BAR_SEP,PANEL_HEIGHT-8);
            decades ++;

        }

    }


}
