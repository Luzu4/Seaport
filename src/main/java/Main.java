import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.*;
import java.util.*;

public class Main extends Thread {

    public static void main(String[] args) throws IOException {


        //After creat 1st seaport comment it out
        //This is code to create yaml file at 1st time
        /*
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        om.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Seaport seaPortStatus = new Seaport();
        om.writeValue(new File("src/main/resources/seaport.yaml"), seaPortStatus);
         */



        //Load Current status of our seaport
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file = new File(classLoader.getResource("seaport.yaml").getFile());
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        om.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Seaport seaPortStatus = om.readValue(file, Seaport.class);

        //Comment it out after yaml file is created
        /*
        ArrayList<Warehouse> listOfCreatedWareHousesInSeaport = new ArrayList<Warehouse>();
        ArrayList<BasicContainer> listOfContainersLoadedToWareHouse = new ArrayList<BasicContainer>();
        ArrayList<Ship> listOfShipsInSeaport = new ArrayList<Ship>();
        seaPortStatus.seaportShips = listOfShipsInSeaport;
        seaPortStatus.wareHouses = listOfCreatedWareHousesInSeaport;
        Warehouse ourFirstWarHouse = new Warehouse("The First and Last Warehouse of this Seaport");
        ArrayList<BasicContainer> listOfStoredContainers = new ArrayList<BasicContainer>();
        ourFirstWarHouse.listOfStoredContainers = listOfStoredContainers;
        seaPortStatus.wareHouses.add(ourFirstWarHouse);
        ArrayList<Wagon> listOfWagons = new ArrayList<Wagon>();
        seaPortStatus.wagons = listOfWagons;
         */

        //I dont know what it is for
        ArrayList<BasicContainer> listOfContainersLoadedToWagon = new ArrayList<BasicContainer>();



        Main thread = new Main();
        thread.start();
        int amount = 0;
        while(thread.isAlive()){
            System.out.println("Waiting..." + amount);
            amount += 1;
        }


        while(true) {
            System.out.println("""
                    -----------------------------------------
                    How can i help You?
                    1.Check Whats Going on in seaport
                    2.Create new ship
                    3.Create new container
                    4.Save\s
                    -----------------------------------------
                    """);
            Scanner input = new Scanner(System.in);
            int inputFromUser = Integer.parseInt(input.nextLine());
            switch(inputFromUser){
                case 1:{
                    System.out.println("-----------------------------------------\n");
                    System.out.println("List of Ships in Seaport: \n");
                    System.out.println("-----------------------------------------\n");
                    for (Ship temp : seaPortStatus.seaportShips){
                        System.out.println("Ship: " + temp.toString());
                        System.out.println("List of loaded containers: \n");
                        for(BasicContainer loadedcontainer : temp.loadedContainers){
                            System.out.println("Sender: " + loadedcontainer.sender + " Container id: " + loadedcontainer.containerID);
                        }
                        System.out.println("-----------------------------------------\n");
                    }
                    System.out.println("-----------------------------------------\n");
                    System.out.println("List of WarHouses in Seaport: \n");
                    System.out.println("-----------------------------------------\n");
                    for (Warehouse temp : seaPortStatus.wareHouses){
                        System.out.println("Warhouse: " + temp.toString());
                        System.out.println("List of loaded containers: \n");
                        for(BasicContainer loadedContainer : temp.listOfStoredContainers){
                            System.out.println("Sender: " + loadedContainer.sender + " Container id: " + loadedContainer.containerID);
                        }
                        System.out.println("-----------------------------------------\n");
                    }
                    break;
                }
                case 2: {
                    Ship newShip = new Ship();
                    newShip.createNewShip();
                    ArrayList<BasicContainer> listOfLoadedContainers = new ArrayList<>();
                    newShip.loadedContainers = listOfLoadedContainers;
                    seaPortStatus.seaportShips.add(newShip);
                    break;
                }
                case 3:{
                    System.out.println("-----------------------------------------\n" +
                            "What Type of Container you want to load: \n" +
                            "1.Basic Container\n" +
                            "2.Heavy Container\n" +
                            "3.Cooling Container\n" +
                            "4.Liquid Container\n" +
                            "5.Explosion container\n" +
                            "6.Toxic Container\n" +
                            "-----------------------------------------\n"+
                            "User choice: ");
                    int userChoice = Integer.parseInt(input.nextLine());
                    BasicContainer newContainer = null;
                    switch (userChoice) {
                        case 1: {
                            newContainer = new BasicContainer();
                            newContainer.createNewContainer();
                            break;
                        }
                        case 2: {
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
                    }
                    System.out.println("""
                            ----------------------------------------
                            Where You Want to load Created Container?
                            1.Warehouse
                            2.Ships
                            3.Wagon
                            4.Quit and delete current creating container.
                            ----------------------------------------\s
                            """);
                    int userChoiceWhereToLoad = 0;
                    userChoiceWhereToLoad = Integer.parseInt(input.nextLine());
                    switch (userChoiceWhereToLoad){
                        case 1:
                            for (Warehouse temp : seaPortStatus.wareHouses){
                                temp.listOfStoredContainers.add(newContainer);
                                System.out.println("Container loaded to WareHouse " + temp.nameOfWarHouse);
                            }
                            break;
                        case 2:
                            System.out.println("At which one ship you want to load it: ");
                            for (Ship temp : seaPortStatus.seaportShips){
                                System.out.println("Ship: " + temp.toString());
                            }
                            System.out.println("Ship ID: ");
                            int idOfShipToLoadThisContainer = Integer.parseInt(input.nextLine());
                            for (Ship temp : seaPortStatus.seaportShips){
                                if (temp.shipID == idOfShipToLoadThisContainer){
                                    temp.loadContainer(newContainer);
                                }else{
                                    System.out.println("Wrong Ship ID");
                                }
                            }
                            break;
                        case 3:

                            System.out.println("Container loaded to Wagon");
                            break;
                    }
                }
                case 4:{
                    om.writeValue(new File("src/main/resources/seaport.yaml"), seaPortStatus);
                    break;
                }
            }
        }
    }
}


