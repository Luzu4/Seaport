import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class YamlFileOperation {

    public static Seaport loadCurrentStatusOfSeaport() throws IOException {
        if(new File ("src/main/resources/seaport.yaml").isFile()){

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            File file = new File(classLoader.getResource("seaport.yaml").getFile());
            ObjectMapper om = new ObjectMapper(new YAMLFactory());
            om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            om.registerModule(new JavaTimeModule());
            om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            om.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Seaport status = om.readValue(file, Seaport.class);
            System.out.println("Current Status Loaded HF");
            return status;
        }else{

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            ObjectMapper om = new ObjectMapper(new YAMLFactory());
            om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            om.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Seaport seaPortStatus = new Seaport();
            om.writeValue(new File("src/main/resources/seaport.yaml"), seaPortStatus);
            //Comment it out after yaml file is created
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
            Wagon ourFirstWagon = new Wagon();
            ArrayList<BasicContainer> listOfContainersLoadedToWagon = new ArrayList<BasicContainer>();
            ourFirstWagon.listOfTransportingContainers = listOfContainersLoadedToWagon;
            seaPortStatus.wagons.add(ourFirstWagon);
            System.out.println("New Seaport was created");
            return seaPortStatus;
        }
    }

    public static String saveCurrentStatusOfSeaport(Seaport currentStatus) throws IOException{
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        om.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        om.writeValue(new File("src/main/resources/seaport.yaml"), currentStatus);
        return "Current Status Saved";
    }

}
