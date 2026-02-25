import persistence.BankAccountFileRepository;
import view.Gui;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args)  {

        try {
            BankAccountFileRepository bk = new BankAccountFileRepository();
            ArrayList<String> arr = bk.load();

            for(String s : arr) {
                System.out.println(s);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}