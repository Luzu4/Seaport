import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Menu {

    public static Scanner input = new Scanner (System.in);

    public static int takeInputFromUserFromTo(int from , int to){
        int userChoice = 0;
        while(userChoice < from || userChoice > to){
            try{
                userChoice = Integer.parseInt(input.nextLine());
                if (userChoice > to || userChoice < from) {
                    System.out.println("Please input number from " + from + " to " + to);
                }
            }
            catch (Exception ex){
                System.out.println("Please input integer from " + from + " to " + to);
            }
        }
        return userChoice;
    }

    public static int takeInputFromUserWithCorrectShipId(Seaport seaport){
        int correctShipId = 0;
        List shipsId = new ArrayList();
        for(Ship temp: seaport.seaportShips){
            shipsId.add(temp.shipID);
        }
        while (!shipsId.contains(correctShipId)){
            try{
                correctShipId = Integer.parseInt(input.nextLine());
                if(!shipsId.contains(correctShipId)){
                    System.out.println("Please Input Correct Ship ID");
                }
            }catch(Exception ex){
                System.out.println("Please Input Correct Ship ID");
            }
        }
        return correctShipId;
    }

    public static int takeInputFromUserWithCorrectContainerId(Seaport seaport, int shipId){
        int correctContainerId = 0;
        List containersId = new ArrayList();
        for(Ship temp : seaport.seaportShips){
            if (temp.shipID == shipId){
                for(BasicContainer container : temp.loadedContainers){
                    containersId.add(container.containerID);
                }
            }
        }
        while (!containersId.contains(correctContainerId)){
            try{
                correctContainerId = Integer.parseInt(input.nextLine());
                if(!containersId.contains(correctContainerId)){
                    System.out.println("Please Input Correct Container ID");
                }
            }catch(Exception ex){
                System.out.println("Please Input Correct Container ID");
            }
        }
        return correctContainerId;
    }


    public static int takeInputFromUserWithCorrectContainerIdFromWarehouse(Seaport seaport){
        int correctContainerId = 0;
        List containersId = new ArrayList();
        for(Warehouse temp : seaport.wareHouses){
            for(BasicContainer container : temp.listOfStoredContainers){
                containersId.add(container.containerID);
            }
        }
        while (!containersId.contains(correctContainerId)){
            try{
                correctContainerId = Integer.parseInt(input.nextLine());
                if(!containersId.contains(correctContainerId)){
                    System.out.println("Please Input Correct Container ID");
                }
            }catch(Exception ex){
                System.out.println("Please Input Correct Container ID");
            }
        }
        return correctContainerId;
    }



    public static void mainMenu(LocalDate currentDate, Seaport seaPortStatus, Thread thread2) throws IOException {
        System.out.println("Today is " + currentDate + "\n" +
                "You are in Menu Of The Best Seaport in this little World!\n" +
                "Main Menu:\n" +
                "1.Ship Management\n" +
                "2.Warehouse Management\n" +
                "3.Create Container\n" +
                "4.Save Current Status Of Seaport");



        int userChoice = takeInputFromUserFromTo(1,4);

        switch (userChoice) {
            case 1 -> menuShipManagement(seaPortStatus, thread2);
            case 2 -> menuWarehouseManagement(seaPortStatus);
            case 3 -> menuOfCreatingContainer(thread2, seaPortStatus);
            case 4 -> System.out.println(YamlFileOperation.saveCurrentStatusOfSeaport(seaPortStatus));
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

        int userChoice = takeInputFromUserFromTo(1,7);
        BasicContainer newContainer = null;
        switch (userChoice) {
            case 1 -> {
                newContainer = new BasicContainer();
                newContainer.createNewContainer();
            }
            case 2 -> {
                newContainer = new HeavyContainer();
                ((HeavyContainer) newContainer).createNewHeavyContainer();
            }
            case 3 -> {
                newContainer = new CoolingContainer();
                ((CoolingContainer) newContainer).createNewCoolingContainer();
            }
            case 4 -> {
                newContainer = new LiquidContainer();
                ((LiquidContainer) newContainer).createLiquidContainer();
            }
            case 5 -> {
                newContainer = new ExplosionContainer();
                ((ExplosionContainer) newContainer).createExplosionContainer();
            }
            case 6 -> System.out.println("At this moment you cant create those type of Container.");
            case 7 -> {
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
        int userChoiceWhereToLoad = takeInputFromUserFromTo(1,4);
        switch (userChoiceWhereToLoad) {
            case 1 -> {
                for (Warehouse temp : seaportStatus.wareHouses) {
                    newContainer.dateWhenContainerWentToWarehouse = LocalDate.now();
                    temp.listOfStoredContainers.add(newContainer);
                    System.out.println("Container loaded to WareHouse " + temp.nameOfWarHouse);
                }
            }
            case 2 -> {
                System.out.println("At which one ship you want to load it: ");
                for (Ship temp : seaportStatus.seaportShips) {
                    System.out.println("Ship: " + temp.toString());
                }
                System.out.println("Ship ID: ");
                int idOfShipToLoadThisContainer = takeInputFromUserWithCorrectShipId(seaportStatus);

                for (Ship temp : seaportStatus.seaportShips) {
                    if (temp.shipID == idOfShipToLoadThisContainer) {
                        temp.loadContainer(newContainer);
                    }
                }
            }
            case 3 -> {
                for (Wagon temp : seaportStatus.wagons) {
                    if (temp.loadContainer(newContainer)) {
                        System.out.println("Container loaded to Wagon");
                    } else {
                        thread2.run();
                    }
                }
            }
            case 4 -> newContainer = null;
        }

    }
    public static void menuShipManagement(Seaport seaportStatus,Thread thread2){
        System.out.println("""
                1.Unload Container From Ship
                2.Print Info About Ship And Already Loaded Containers
                3.Create Ship
                4.Back To Main MenuYour Decision:""");
        int userChoice = takeInputFromUserFromTo(1,4);
        switch (userChoice) {
            case 1 -> {

                System.out.println("From Which One Ship You Want To Unload Containers?");
                for (Ship temp : seaportStatus.seaportShips) {
                    System.out.println(temp.toString());
                }
                System.out.println("Ship ID: ");
                int idOfShipThatUserWantUnload = takeInputFromUserWithCorrectShipId(seaportStatus);
                seaportStatus.printInfoAboutShip(idOfShipThatUserWantUnload);

                System.out.println("Container ID To Unload:");
                int idOfContainerToUnload = takeInputFromUserWithCorrectContainerId(seaportStatus, idOfShipThatUserWantUnload);
                for (Ship ship : seaportStatus.seaportShips) {
                    if (ship.shipID == idOfShipThatUserWantUnload) {
                        for (BasicContainer loadedContainer : ship.loadedContainers) {
                            if (loadedContainer.containerID == idOfContainerToUnload) {
                                System.out.println("Where You Want To Unload This Container: \n" +
                                        "1.Warehouse\n" +
                                        "2.Wagon");
                                int userChoiceOfWhereToUnloadContainer = takeInputFromUserFromTo(1,2);
                                switch (userChoiceOfWhereToUnloadContainer) {
                                    case 1 -> {
                                        for (Warehouse wareHouse : seaportStatus.wareHouses) {
                                            loadedContainer.dateWhenContainerWentToWarehouse = LocalDate.now();
                                            wareHouse.listOfStoredContainers.add(loadedContainer);
                                            ship.loadedContainers.remove(loadedContainer);
                                            System.out.println("Container loaded to WareHouse " + wareHouse.nameOfWarHouse);
                                        }
                                    }
                                    case 2 -> {
                                        for (Wagon wagon : seaportStatus.wagons) {
                                            if (wagon.loadContainer(loadedContainer)) {
                                                System.out.println("Container unloaded from ship and loaded to Wagon");
                                            } else {
                                                thread2.run();
                                            }
                                        }
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
            case 2 -> {
                System.out.println("About Which One You Want Information?");
                for (Ship temp : seaportStatus.seaportShips) {
                    System.out.println(temp.toString());
                }
                System.out.println("Ship ID: ");
                int idOfShipThatUserWantPrintSomeInformation = takeInputFromUserWithCorrectShipId(seaportStatus);
                seaportStatus.printInfoAboutShip(idOfShipThatUserWantPrintSomeInformation);

            }
            case 3 -> {
                Ship newShip = new Ship();
                newShip.createNewShip();
                newShip.loadedContainers = new ArrayList<>();
                seaportStatus.seaportShips.add(newShip);
            }
            case 4 -> {
            }
        }
    }

    public static void menuWarehouseManagement(Seaport seaportStatus){
        System.out.println("""
                What Type Of Container You Want To create
                1.Show Warehouse Status With Dates Of Containers To Utilization
                2.Utilize Container
                3.Load Container At Ship
                4.quit
                Type Your Choice:""");
        int userChoice = takeInputFromUserFromTo(1,4);
        switch (userChoice) {
            case 1 -> {
                for (Warehouse temp : seaportStatus.wareHouses) {
                    System.out.println("Warehouse: " + temp.toString());
                    System.out.println("List of containers in Warehouse: \n");
                    for (BasicContainer loadedContainer : temp.listOfStoredContainers) {
                        System.out.println("Sender: " + loadedContainer.sender + " Container id: " + loadedContainer.containerID);
                    }
                }
            }
            case 2 -> {
                for (Warehouse temp : seaportStatus.wareHouses) {
                    System.out.println("Warehouse: " + temp.toString());
                    System.out.println("List of containers in warehouse: \n");
                    for (BasicContainer loadedContainer : temp.listOfStoredContainers) {
                        System.out.println(loadedContainer.toString());
                    }
                }
                System.out.println("Which One Container You Want To Utilize Container ID: ");
                int userChoiceOfContainerToUtilize = takeInputFromUserWithCorrectContainerIdFromWarehouse(seaportStatus);
                for (Warehouse temp : seaportStatus.wareHouses) {
                    for (Iterator<BasicContainer> iterator1 = temp.listOfStoredContainers.iterator(); iterator1.hasNext(); ) {
                        if (iterator1.next().containerID == userChoiceOfContainerToUtilize) {
                            System.out.println("Container with ID: " +
                                    userChoiceOfContainerToUtilize +
                                    " Has Been Utilize");
                            iterator1.remove();
                        }
                    }
                }
            }
            case 3 -> {
                for (Warehouse temp : seaportStatus.wareHouses) {
                    System.out.println("Warehouse: " + temp.toString());
                    System.out.println("List of containers in warehouse: \n");
                    for (BasicContainer loadedContainer : temp.listOfStoredContainers) {
                        System.out.println(loadedContainer.toString());
                    }
                }
                System.out.println("Which One Container You Want To Load At Ship, Container ID: ");
                int userChoiceOfContainerToLoadAtShip = takeInputFromUserWithCorrectContainerIdFromWarehouse(seaportStatus);
                for (Warehouse temp : seaportStatus.wareHouses) {
                    for (Iterator<BasicContainer> iterator1 = temp.listOfStoredContainers.iterator();iterator1.hasNext();){
                        BasicContainer loadedContainer = iterator1.next();
                        if (loadedContainer.containerID == userChoiceOfContainerToLoadAtShip) {
                            for (Ship ship : seaportStatus.seaportShips) {
                                System.out.println(ship.toString());
                            }
                            System.out.println("At Which One Ship You Want To Load It, Ship ID: ");
                            System.out.println("Ship ID: ");
                            int idShipToLoadContainer = takeInputFromUserWithCorrectShipId(seaportStatus);
                            for (Ship ship : seaportStatus.seaportShips) {
                                if (ship.shipID == idShipToLoadContainer) {
                                    ship.loadContainer(loadedContainer);
                                    iterator1.remove();
                                }
                            }
                        }
                    }
                }
            }
            case 4 -> {
            }
        }
    }
}
