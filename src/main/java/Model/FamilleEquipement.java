package Model;

import java.util.ArrayList;

public class FamilleEquipement {

    private int nbTotalOT; //total OT pour un équipement pendant un mois
    private ArrayList<Integer> nbOTMetro; //répartition sur les lignes de métro
    private ArrayList<Integer> nbOTRER; //répartition sur les lignes de RER

    public int getNbTotalOT() {
        return nbTotalOT;
    }

    public void setNbTotalOT(int nbTotalOT) {
        this.nbTotalOT = nbTotalOT;
    }

    public ArrayList<Integer> getNbOTMetro() {
        return nbOTMetro;
    }

    public void setNbOTMetro(ArrayList<Integer> nbOTMetro) {
        this.nbOTMetro = nbOTMetro;
    }

    public ArrayList<Integer> getNbOTRER() {
        return nbOTRER;
    }

    public void setNbOTRER(ArrayList<Integer> nbOTRER) {
        this.nbOTRER = nbOTRER;
    }
}
