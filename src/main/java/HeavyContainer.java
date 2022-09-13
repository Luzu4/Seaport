import java.util.Scanner;

public class HeavyContainer extends BasicContainer{
    String typeOfCargo;

    public HeavyContainer(){
        super();
    }

    public void createNewHeavyContainer() {
        super.createNewContainer();
        Scanner input = new Scanner(System.in);
        System.out.println("Type of Cargo(RTV/GLASS/TOYS): ");
        this.typeOfCargo = input.nextLine();
    }
}
