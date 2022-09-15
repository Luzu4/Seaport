import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    public static Scanner input = new Scanner (System.in);
    public static void mainMenu(LocalDate currentDate, Seaport seaPortStatus, Thread thread2) throws IOException {

            System.out.println("Today is " + currentDate + "\n" +
                    "You are in Menu Of The Best Seaport in this little World!\n" +
                    "Main Menu:\n" +
                    "1.Ship Management\n" +
                    "2.Warehouse Management\n" +
                    "3.Create Container\n" +
                    "4.Save Current Status Of Seaport");

            int userChoice = Integer.parseInt(input.nextLine());
            switch (userChoice){
                case 1:{
                    menuShipManagement(seaPortStatus, thread2);
                    break;
                }
                case 2:{
                    /*Main Menu:
                    2.Warehouse Management
                    1.Show Warehouse status with dates of containers to utilization
                    2.Utilize Container
                    3.Load Container at ship
                     */
                    break;
                }
                case 3:{
                    menuOfCreatingContainer(thread2,seaPortStatus);
                    break;
                }
                case 4:{
                    System.out.println(YamlFileOperation.saveCurrentStatusOfSeaport(seaPortStatus));
                    break;
                }


            }

    }
    public static void menuOfCreatingContainer(Thread thread2, Seaport seaportStatus){
        System.out.println("""
                What Type Of Container You Want To create
                1.Basic
                2.Heavy
                3.Cooling
                4.Liquid
                5.Explosion
                6.Toxic
                7.Back To Main Menu
                Type Your Choice:""");
        int userChoice = Integer.parseInt(input.nextLine());
        BasicContainer newContainer = null;
        switch (userChoice){
            case 1:{
                newContainer = new BasicContainer();
                newContainer.createNewContainer();
                break;
            }
            case 2:{
                newContainer = new HeavyContainer();
                ((HeavyContainer) newContainer).createNewHeavyContainer();
                break;
            }
            case 3: {
                newContainer = new CoolingContainer();
                ((CoolingContainer) newContainer).createNewCoolingContainer();
                break;
            }
            case 4: {
                newContainer = new LiquidContainer();
                ((LiquidContainer) newContainer).createLiquidContainer();
                break;
            }
            case 5: {
                newContainer = new ExplosionContainer();
                ((ExplosionContainer) newContainer).createExplosionContainer();
                break;
            }
            case 6: {
                System.out.println("At this moment you cant create those type of Container.");
                break;
            }
            case 7:{
                return;
            }
        }
        System.out.println("""
                            ----------------------------------------------
                            Where You Want to load Created Container?
                            1.Warehouse
                            2.Ships
                            3.Wagon
                            4.Quit and delete current creating container.
                            ----------------------------------------------\s
                            """);
        int userChoiceWhereToLoad = Integer.parseInt(input.nextLine());
        switch (userChoiceWhereToLoad){
            case 1:{
                for (Warehouse temp : seaportStatus.wareHouses) {
                    newContainer.dateWhenContainerWentToWarehouse = LocalDate.now();
                    temp.listOfStoredContainers.add(newContainer);
                    System.out.println("Container loaded to WareHouse " + temp.nameOfWarHouse);
                }
                break;
            }
            case 2: {
                System.out.println("At which one ship you want to load it: ");
                for (Ship temp : seaportStatus.seaportShips) {
                    System.out.println("Ship: " + temp.toString());
                }
                System.out.println("Ship ID: ");
                int idOfShipToLoadThisContainer = Integer.parseInt(input.nextLine());
                for (Ship temp : seaportStatus.seaportShips) {
                    if (temp.shipID == idOfShipToLoadThisContainer) {
                        temp.loadContainer(newContainer);
                    }
                }
                break;
            }
            case 3: {
                for (Wagon temp : seaportStatus.wagons) {
                    if (temp.loadContainer(newContainer)) {
                        System.out.println("Container loaded to Wagon");
                    } else {
                        thread2.run();
                    }
                }
                break;
            }
            case 4: {
                newContainer = null;
                break;
            }
        }

    }
    public static void menuShipManagement(Seaport seaportStatus,Thread thread2){
        System.out.println("1.Unload Container From Ship\n" +
                "2.Print Info About Ship And Already Loaded Containers\n" +
                "3.Create Ship\n" +
                "4.Back To Main Menu" +
                "Your Decision:");
        int userChoice = Integer.parseInt(input.nextLine());
        switch(userChoice){
            case 1:{
                System.out.println("From Which One Ship You Want To Unload Containers?");
                for (Ship temp : seaportStatus.seaportShips) {
                    System.out.println(temp.toString());
                }
                System.out.println("Ship ID: ");
                int idOfShipThatUserWantPrintSomeInformation = Integer.parseInt(input.nextLine());
                for (Ship temp : seaportStatus.seaportShips) {
                    if (temp.shipID == idOfShipThatUserWantPrintSomeInformation){
                        System.out.println(temp);
                        System.out.println("List of loaded containers: ");
                        for (BasicContainer loadedContainer : temp.loadedContainers) {
                            System.out.println(loadedContainer.toString());
                        }
                    }
                }
                System.out.println("Container ID To Unload:");
                int idOfContainerToUnload = Integer.parseInt(input.nextLine());
                for (Ship ship : seaportStatus.seaportShips) {
                    if (ship.shipID == idOfShipThatUserWantPrintSomeInformation){
                        System.out.println(ship);
                        for (BasicContainer loadedContainer : ship.loadedContainers) {
                            if(loadedContainer.containerID == idOfContainerToUnload){
                                int userChoiceOfWhereToUnloadContainer = Integer.parseInt(input.nextLine());
                                switch (userChoiceOfWhereToUnloadContainer){
                                    case 1:{
                                        for (Warehouse wareHouse : seaportStatus.wareHouses) {
                                            loadedContainer.dateWhenContainerWentToWarehouse = LocalDate.now();
                                            wareHouse.listOfStoredContainers.add(loadedContainer);
                                            ship.loadedContainers.remove(loadedContainer);
                                            System.out.println("Container loaded to WareHouse " + wareHouse.nameOfWarHouse);
                                        }
                                        break;
                                    }
                                    case 2:{
                                        for (Wagon wagon : seaportStatus.wagons) {
                                            if (wagon.loadContainer(loadedContainer)) {
                                                System.out.println("Container unloaded from ship and loaded to Wagon");
                                            } else {
                                                thread2.run();
                                            }
                                        }
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
                break;
            }
            case 2:{
                System.out.println("About Which One You Want Information?");
                for (Ship temp : seaportStatus.seaportShips) {
                    System.out.println(temp.toString());
                }
                System.out.println("Ship ID: ");
                int idOfShipThatUserWantPrintSomeInformation = Integer.parseInt(input.nextLine());
                for (Ship temp : seaportStatus.seaportShips) {
                    if (temp.shipID == idOfShipThatUserWantPrintSomeInformation){
                        System.out.println(temp);
                        System.out.println("List of loaded containers: ");
                        for (BasicContainer loadedContainer : temp.loadedContainers) {
                            System.out.println(loadedContainer.toString());
                        }
                    }
                }
                break;
            }
            case 3:{
                Ship newShip = new Ship();
                newShip.createNewShip();
                ArrayList<BasicContainer> listOfLoadedContainers = new ArrayList<>();
                newShip.loadedContainers = listOfLoadedContainers;
                seaportStatus.seaportShips.add(newShip);
                break;
            }
            case 4:{
                break;
            }

        }
    }
}
