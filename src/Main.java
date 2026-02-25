import persistence.BankAccountFileRepository;
import view.Gui;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args)  {

        try {
            Gui.start();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}