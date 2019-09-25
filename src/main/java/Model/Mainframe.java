package Model;

import javax.swing.*;

public class Mainframe extends JFrame { //Fenêtre mère de l'IHM

    public Mainframe()throws Exception{
        this.setTitle("AGORA Analyser");
        this.setSize(500, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(new MainPanel());
        this.setVisible(true);
    }

    public static void main(String[] args) throws Exception{
        Mainframe frame = new Mainframe();
    }

}
