import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;

public class BasicContainer {
    String sender, destination, originLocation;
    int containerID;
    float weightOfTheContainerGross, weightOfTheContainerNet;

    LocalDate dateWhenContainerWentToWarehouse;

    public BasicContainer() {
        Random rand = new Random();
        this.containerID = rand.nextInt(1000);
    }

    public void createNewContainer(){
        Scanner input = new Scanner(System.in);
        System.out.println("Name of sender: ");
        this.sender = input.nextLine();
        System.out.println("Destination: ");
        this.destination = input.nextLine();
        System.out.println("Origin Location: ");
        this.originLocation = input.nextLine();
        System.out.println("What is the gross weight of this container: ");
        this.weightOfTheContainerGross = Float.parseFloat(input.nextLine());
        System.out.println("What is the net weight of this container: ");
        this.weightOfTheContainerNet = Float.parseFloat(input.nextLine());
    }

}