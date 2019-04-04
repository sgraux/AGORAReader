package Model;

import java.util.ArrayList;
import java.util.Arrays;

public class FamilleEquipement {

    private int nbTotalOT; //total OT pour un équipement pendant un mois
    private int[] nbOTMetro = new int[13]; //répartition sur les lignes de métro
    private int nbOTRERB;
    private int nbOTRERA;

    public FamilleEquipement() {
        for (int i = 0; i < nbOTMetro.length; i++)
            nbOTMetro[i] = 0;
        nbOTRERB = 0;
        nbOTRERA = 0;
    }

    public void manageOT(String parClient) {
        if(parClient.equals("RER A")) nbOTRERA++;
        else if(parClient.equals("RER B")) nbOTRERB++;
        else if(parClient.equals("M01")) nbOTMetro[0]++;
        else if(parClient.equals("M02")) nbOTMetro[1]++;
        else if(parClient.equals("M03")) nbOTMetro[2]++;
        else if(parClient.equals("M04")) nbOTMetro[3]++;
        else if(parClient.equals("M05")) nbOTMetro[4]++;
        else if(parClient.equals("M06")) nbOTMetro[5]++;
        else if(parClient.equals("M07")) nbOTMetro[6]++;
        else if(parClient.equals("M08")) nbOTMetro[7]++;
        else if(parClient.equals("M09")) nbOTMetro[8]++;
        else if(parClient.equals("M10")) nbOTMetro[9]++;
        else if(parClient.equals("M11")) nbOTMetro[10]++;
        else if(parClient.equals("M12")) nbOTMetro[11]++;
        else if(parClient.equals("M13")) nbOTMetro[12]++;

        nbTotalOT ++;

    }

    public int getNbTotalOT() {
        return nbTotalOT;
    }

    public void setNbTotalOT(int nbTotalOT) {
        this.nbTotalOT = nbTotalOT;
    }

    @Override
    public String toString() {
        return "FamilleEquipement{" +
                "nbTotalOT=" + nbTotalOT +
                ", nbOTMetro=" + Arrays.toString(nbOTMetro) +
                ", nbOTRERB=" + nbOTRERB +
                ", nbOTRERA=" + nbOTRERA +
                '}';
    }
}