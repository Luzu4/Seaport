import java.util.Scanner;

public class CoolingContainer extends HeavyContainer{
    //necessary connect to electricity
    String expirationDate;

    public CoolingContainer(){
        super();
    }
    public void createNewCoolingContainer(){
        super.createNewHeavyContainer();
        Scanner input = new Scanner(System.in);
        System.out.println("Expiration Date: ");
        this.expirationDate = input.nextLine();
    }
}
