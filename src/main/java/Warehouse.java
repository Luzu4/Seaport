import java.util.ArrayList;

public class Warehouse {
    String nameOfWarHouse;
    public Warehouse(){}
    public Warehouse(String name){
        this.nameOfWarHouse = name;
    }
    ArrayList<BasicContainer> listOfStoredContainers;

    @Override
    public String toString(){
        return ("Name of Warhouse: " + nameOfWarHouse );
    }
}
