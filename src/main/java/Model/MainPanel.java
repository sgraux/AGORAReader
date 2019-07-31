package Model;


import Model.PDFCreator;
import data.Data;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class MainPanel extends JPanel implements ActionListener {

    private String absoluteInputPath;
    private String absoluteOutputPath;
    private Data data = new Data();

    private final JLabel labelSelectInput = new JLabel("Selectionner un extract Agora : (format .xlsx) ");
    //private final JLabel labelFormat = new JLabel("Le fichier doit contenir les colonnes suivantes dans l'ordre suivant : ")
    private JTextField fieldInputPath = new JTextField();
    private JButton buttonInput = new JButton("Ouvrir Input");

    private final JLabel labelSelectOuput = new JLabel("Selectionner un dossier de destination :");
    private JTextField fieldOutputPath = new JTextField();
    private JButton buttonOutput = new JButton("Ouvrir Output");

    private JLabel labelComboBoxFamille = new JLabel("Choisir un mode :");
    private ArrayList<String> list = new ArrayList<String>();
    private JComboBox comboBoxFamille;

    private JButton buttonValidate = new JButton("Valider");

    final JFXPanel fxPanel = new JFXPanel();

    ChartEngine engine;

    public MainPanel() throws Exception{
        super();
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        list.add("SAE");
        for(String s : data.getTabEquip()){
            list.add(s);
        }
        list.add("Global");
        comboBoxFamille = new JComboBox(list.toArray());

        this.add(labelSelectInput);
        this.add(fieldInputPath);
        this.add(buttonInput);
        buttonInput.addActionListener(this);

        this.add(labelSelectOuput);
        this.add(fieldOutputPath);
        this.add(buttonOutput);
        buttonOutput.addActionListener(this);

        this.add(labelComboBoxFamille);
        this.add(comboBoxFamille);

        this.add(buttonValidate);
        buttonValidate.addActionListener(this);

    }

    private void initFX(JFXPanel fxPanel) {
        // This method is invoked on the JavaFX thread
        Stage stage = new Stage();
        engine = new ChartEngine(comboBoxFamille.getSelectedItem().toString(), absoluteInputPath, absoluteOutputPath);
        try{engine.start(stage);}
        catch (Exception e){}
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == buttonInput){
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            int returnValue = jfc.showOpenDialog(null);
            // int returnValue = jfc.showSaveDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                absoluteInputPath = selectedFile.getAbsolutePath();
                System.out.println(absoluteInputPath);
                //labelSelectInput.setText(labelSelectInput.getText() + absoluteInputPath);
                fieldInputPath.setText(absoluteInputPath);
            }
        }
        else if(e.getSource() == buttonOutput){
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jfc.setAcceptAllFileFilterUsed(false);

            int returnValue = jfc.showOpenDialog(null);
            // int returnValue = jfc.showSaveDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                absoluteOutputPath = selectedFile.getAbsolutePath();
                System.out.println(absoluteOutputPath);
                fieldOutputPath.setText(absoluteOutputPath);

            }
        }
        else if(e.getSource() == buttonValidate){
            if(absoluteInputPath == null || absoluteOutputPath == null){
                JOptionPane.showMessageDialog(this.getParent(),
                        "Veuillez sélectionner un fichier d'entrée et un dossier de sortie",
                        "Erreur de saisie",
                        JOptionPane.ERROR_MESSAGE);
            }
            else {
                try {
                    Platform.runLater(new Runnable() {
                        public void run() {
                            initFX(fxPanel);
                        }
                    });
                    JOptionPane.showMessageDialog(this,
                            "Exécution en cours",
                            "Execution",
                            JOptionPane.INFORMATION_MESSAGE);

                    /*else {
                        JOptionPane.showMessageDialog(this.getParent(),
                                "Erreur rencontrée ! Vérifiez qu'un ancien rapport généré n'est pas ouvert",
                                "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                    }*/
                } catch (Exception exception) {
                }
            }
        }
    }
}
