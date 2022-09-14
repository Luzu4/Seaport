import java.util.ArrayList;

public class Wagon {
    int maxContainers = 10;
    int currentNumberOfContainers = 0;
    public Wagon (){}
    ArrayList<BasicContainer> listOfTransportingContainers;

    public boolean loadContainer(BasicContainer container){
        if (currentNumberOfContainers < maxContainers){
            listOfTransportingContainers.add(container);
            currentNumberOfContainers += 1;
            return true;
        }else{
            System.out.println("You need to w8 30 seconds, this wagon is overloaded.\n" +
                    "Try one more time after 30 seconds.");
            return false;
        }
    }
    public void clearWagon (){
        listOfTransportingContainers.clear();
        currentNumberOfContainers = 0;
        System.out.println("CLEARED WAGON BABABAB");
        System.out.println(currentNumberOfContainers);

    }

}
