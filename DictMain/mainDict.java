package DictMain;

import DictController.*;

public class mainDict {
    public static void main(String[] args) {
        DictController controller = DictController.getInstance();
        try {
            controller.startApplication();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}