import java.time.LocalDate;
import java.util.ArrayList;

public class Warehouse {
    String nameOfWarHouse;
    public Warehouse(){}
    public Warehouse(String name){
        this.nameOfWarHouse = name;
    }
    ArrayList<BasicContainer> listOfStoredContainers;

    public void checkWarehouseAndDeleteTooOldContainers(LocalDate currentDate){
        for (BasicContainer temp : listOfStoredContainers){
            if (currentDate.isAfter(temp.dateWhenContainerWentToWarehouse.plusDays(30))){
                listOfStoredContainers.remove(temp);
                System.out.println("Container with ID : " + temp.containerID + " has been removed");
            }
        }
    }
    @Override
    public String toString(){
        return ("Name of Warhouse: " + nameOfWarHouse );
    }
}
