import java.util.Scanner;

public class ExplosionContainer extends HeavyContainer{
    float maximumAmbientTemperature;

    public ExplosionContainer(){
        super();
    }
    public void createExplosionContainer(){
        super.createNewHeavyContainer();
        Scanner input = new Scanner(System.in);
        System.out.println("Maximum Ambient Temperature: ");
        this.maximumAmbientTemperature = Float.parseFloat(input.nextLine());
    }
}
