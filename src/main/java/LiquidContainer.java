import java.util.Scanner;

public class LiquidContainer extends BasicContainer{
    String densityOfTheLiquid;


    public LiquidContainer(){
        super();

    }
    public void createLiquidContainer(){
        super.createNewContainer();
        Scanner input = new Scanner(System.in);
        System.out.println("Density Of The Liquid: ");
        this.densityOfTheLiquid = input.nextLine();
    }
}
