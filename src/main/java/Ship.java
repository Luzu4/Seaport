import java.util.*;

public class Ship {

    private int maxNumberOfToxicOrExplosionContainers;
    private int maxNumberOfHeavyContainers;
    private int maxNumberOfElectricContainers;
    private int maxNumberOfContainers;
    public int shipID;
    private float maxCapacityInKilograms;
    private String nameOfShip;
    private String sourceLocationOfTheTransport;
    private String transportDestination;
    ArrayList<BasicContainer> loadedContainers;


    public Ship(String nameOfShip, String sourceLocationOfTheTransport,
                String transportDestination, float maxCapacityInKilograms,
                int maxNumberOfToxicOrExplosionContainers, int maxNumberOfHeavyContainers,
                int maxNumberOfElectricContainers, int maxNumberOfContainers, int shipID){
        this.nameOfShip = nameOfShip;
        this.sourceLocationOfTheTransport = sourceLocationOfTheTransport;
        this.transportDestination = transportDestination;
        this.maxCapacityInKilograms = maxCapacityInKilograms;
        this.maxNumberOfToxicOrExplosionContainers = maxNumberOfToxicOrExplosionContainers;
        this.maxNumberOfHeavyContainers = maxNumberOfHeavyContainers;
        this.maxNumberOfElectricContainers = maxNumberOfElectricContainers;
        this.maxNumberOfContainers = maxNumberOfContainers;
        this.shipID = shipID;
    }
    int numberOfToxicExplosionContainers = 0,
            numberOfHeavyContainers = 0,
            numberOfElectricContainers =0,
            numberOfContainers = 0;
    float currentCapacityInKilograms = 0;

    public Ship(){}
    void createNewShip(){
        Scanner input = new Scanner(System.in);
        Random rand = new Random();
        System.out.println("To make a new ship please input necessary data.");
        System.out.println("Name of new ship: ");
        this.nameOfShip = input.nextLine();
        System.out.println("Destination of the transport: ");
        this.transportDestination = input.nextLine();
        System.out.println("Source Location Of The Trasnport: ");
        this.sourceLocationOfTheTransport = input.nextLine();
        System.out.println("Maximum number of containers: ");
        this.maxNumberOfContainers = Integer.parseInt(input.nextLine());
        System.out.println("Maximum number of Electric containers: ");
        this.maxNumberOfElectricContainers = Integer.parseInt(input.nextLine());
        System.out.println("Maximum number of Toxic or Explosion containers: ");
        this.maxNumberOfToxicOrExplosionContainers = Integer.parseInt(input.nextLine());
        System.out.println("Maximum number of  Heavy containers: ");
        this.maxNumberOfHeavyContainers =Integer.parseInt(input.nextLine());
        System.out.println("Maximum weight of all containers: ");
        this.maxCapacityInKilograms =  Float.parseFloat(input.nextLine());

        this.shipID = rand.nextInt(5000);
    }



    public void loadContainer(BasicContainer container){
        if (maxNumberOfContainers > numberOfContainers && currentCapacityInKilograms < maxCapacityInKilograms) {
            if (container.getClass().equals(HeavyContainer.class) && numberOfHeavyContainers < maxNumberOfHeavyContainers){
                numberOfContainers += 1;
                numberOfContainers += 1;
                currentCapacityInKilograms += container.weightOfTheContainerGross;
                loadedContainers.add(container);
            } else if (container.getClass().equals(CoolingContainer.class) &&
                    numberOfHeavyContainers < maxNumberOfHeavyContainers &&
                    numberOfElectricContainers < maxNumberOfElectricContainers) {
                numberOfElectricContainers += 1;
                numberOfHeavyContainers += 1;
                numberOfContainers += 1;
                currentCapacityInKilograms += container.weightOfTheContainerGross;
                loadedContainers.add(container);
            } else if (container.getClass().equals(BasicContainer.class) || container.getClass().equals(LiquidContainer.class)) {
                numberOfContainers += 1;
                currentCapacityInKilograms += container.weightOfTheContainerGross;
                loadedContainers.add(container);
            } else if (container.getClass().equals(ExplosionContainer.class) &&
                    numberOfToxicExplosionContainers < maxNumberOfToxicOrExplosionContainers &&
                    numberOfHeavyContainers < maxNumberOfHeavyContainers){
                numberOfContainers += 1;
                numberOfToxicExplosionContainers += 1;
                numberOfHeavyContainers += 1;
                currentCapacityInKilograms += container.weightOfTheContainerGross;
                loadedContainers.add(container);
            }else{
                System.out.println("Class Of Container are UNKNOWN");
            }
            System.out.println("Current Number of Container at Ship: " + numberOfContainers);
        }
    }

    @Override
    public String toString(){
        return ("Name of Ship: " + nameOfShip + ", Ship ID: " + shipID );
    }
}
