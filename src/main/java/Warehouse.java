import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public class Warehouse {
    String nameOfWarHouse;
    public Warehouse(){}
    public Warehouse(String name){
        this.nameOfWarHouse = name;
    }
    ArrayList<BasicContainer> listOfStoredContainers;

    public void checkWarehouseAndDeleteTooOldContainers(LocalDate currentDate){
        for (Iterator<BasicContainer> iterator1 = listOfStoredContainers.iterator(); iterator1.hasNext();){
            BasicContainer container123 = iterator1.next();
            if (currentDate.isAfter(container123.dateWhenContainerWentToWarehouse.plusDays(30))){
                System.out.println("Container with ID: " + container123.containerID + " has been removed.");
                iterator1.remove();
            }
        }
    }
    @Override
    public String toString(){
        return ("Name of Warehouse: " + nameOfWarHouse );
    }
}
