/*
HELLO!
 */
import java.util.*;
import java.io.*;

public class Main {
    // Search Criteria...Contains Name and Gender used to return Name and Gender from another a method
    static final class searchOBJ{
        private final String name;
        private final String gender;

        public searchOBJ(String name, String gender){
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

    public static void main(String[] args) {

        //DrawingPanel panel = new DrawingPanel(650,400);

        printIntro();

        searchOBJ nameAndGender = getSearchCriteria();
        String results = searchFile(nameAndGender, "names.txt", false);
        System.out.print(results);



    }

    public static void printIntro(){
        // made only one call for efficiency
        System.out.println("This program allows you to search through the\n" +
                "data from the Social Security Administration\n" +
                "to see how popular a particular name has been\n" +
                "since 1890\n");
    }

    public static searchOBJ getSearchCriteria(){
        Scanner console = new Scanner(System.in);
        System.out.print("Name: ");
        String name = console.next();
        name = name.toUpperCase().charAt(0) + name.toLowerCase().substring(1);
        System.out.print("Gender (M or F): ");
        String gender = console.next();
        //System.out.println("\n" + name + " " + gender);
        return new searchOBJ(name, gender);
    }

    public static String searchFile(searchOBJ nameAndGender, String fileName, boolean meaning){

        String wantedName = nameAndGender.getName().toLowerCase();
        String wantedGender = nameAndGender.getGender().toLowerCase();

        File names1 = new File (fileName);
        try {

            Scanner nameList = new Scanner(names1);
            while (nameList.hasNextLine()){
                String results = nameList.nextLine();
                Scanner wbw = new Scanner(results);
                String dataFromFile = wbw.next();
                dataFromFile = results.substring(0,wantedName.length()+1).toLowerCase();
                System.out.println(wantedName +" : " + dataFromFile);

                //wantedName+" "
                if (dataFromFile.contains(wantedName+ " ")){

                    String genderTry = wbw.next().toLowerCase();
                    System.out.println(wantedGender +" : " + genderTry);
                    if (genderTry.charAt(0) == wantedGender.charAt(0)) {
                        return (results);
                    }
                }

            }
            return ("Sorry \""+ wantedName + "\"was not found.");
        }
        catch (FileNotFoundException ex){
            return("File Not Found");
        }

    }




    /*
    public static void (){

    }
     */

}
