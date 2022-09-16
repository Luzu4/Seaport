import java.util.ArrayList;

public class Seaport {

    public Seaport(){}

    ArrayList<Ship> seaportShips;
    ArrayList<Warehouse> wareHouses;
    ArrayList<Wagon> wagons;


    public void printInfoAboutShip(int idOfShipThatUserWantPrintSomeInformation){
        for (Ship temp : this.seaportShips) {
            if (temp.shipID == idOfShipThatUserWantPrintSomeInformation){
                System.out.println(temp);
                System.out.println("List of loaded containers: ");
                for (BasicContainer loadedContainer : temp.loadedContainers) {
                    System.out.println(loadedContainer.toString());
                }
            }
        }
    }




}
