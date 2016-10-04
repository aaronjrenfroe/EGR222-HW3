/**
 * Created by AaronR on 10/1/16.
 *
 *static final class NameObject{}
 *
 */
// NameObject...Contains Name and Gender used to return Name and Gender from another a method
public final class NameObject {
    private String name;
    private String gender;

    public NameObject(){
        this.name = "";
        this.gender = "";
    }


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

    public Boolean setName(String newName){
        this.name = newName;
        return true;
    }

    public Boolean setGender(String newGender){
        this.gender = newGender;
        return true;
    }
}
