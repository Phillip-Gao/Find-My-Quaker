package FindMyQuaker;

import FindMyQuaker.network.RunFindMyQuaker;

import javax.swing.*;

public class GUI {

    public static void main(String[] args) {
        Runnable gui = new RunFindMyQuaker();

        SwingUtilities.invokeLater(gui);
    }
}
