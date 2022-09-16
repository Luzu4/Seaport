import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Main extends Thread {
    public static LocalDate date = LocalDate.now();
    public static Seaport seaPortStatus;

    static {
        try {
            seaPortStatus = YamlFileOperation.loadCurrentStatusOfSeaport();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws IOException{
        System.out.println(date + "Current time");
        Thread thread1 = new Thread(() -> {
            Timer timer = new Timer();
            TimerTask changeDayAfterEveryFiveSeconds = new TimerTask() {
                @Override
                public void run() {
                    date = date.plusDays(1);
                    for (Warehouse temp : seaPortStatus.wareHouses) {
                        temp.checkWarehouseAndDeleteTooOldContainers(date);
                    }
                }
            };
            timer.schedule(changeDayAfterEveryFiveSeconds, 0, 5000);
        });
        thread1.start();
        Thread thread2 = new Thread (() -> {
            Timer timer2 = new Timer();
            TimerTask clearWagonTask = new TimerTask() {
                @Override
                public void run() {
                    for( Wagon tempWagon : seaPortStatus.wagons){
                        tempWagon.clearWagon();
                    }
                }
            };
            timer2.schedule(clearWagonTask,30000);
        });

        while (true){
            Menu.mainMenu(date,seaPortStatus,thread2);
        }
    }
}