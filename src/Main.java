
import view.Gui;

public class Main {

    public static void main(String[] args)  {

        try {
            Gui.start();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}