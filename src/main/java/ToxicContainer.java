import java.util.Scanner;

class ToxicContainer extends HeavyContainer{
    String certificate;

    public ToxicContainer(){
        super();
        Scanner input = new Scanner(System.in);
        System.out.println("Certificate: ");
        certificate = input.nextLine();

    }

}
