package Model;

import java.util.ArrayList;
import java.util.Arrays;

public class FamilleEquipement {

    private int nbTotalOT; //total OT pour un équipement pendant un mois
    private int nbOTLignesSpe;
    private int[] nbOTMetro = new int[13]; //répartition sur les lignes de métro
    private int nbOTRERB;
    private int nbOTRERA;

    public FamilleEquipement() {
        for (int i = 0; i < nbOTMetro.length; i++)
            nbOTMetro[i] = 0;
        nbOTRERB = 0;
        nbOTRERA = 0;
        nbOTLignesSpe = 0;
    }

    public void manageOT(String parClient) { //TODO: switch case
        if(parClient.equals("RER A")) {
            nbOTRERA++;
            nbOTLignesSpe++;
        }
        else if(parClient.equals("RER B")){
            nbOTRERB++;
            nbOTLignesSpe++;
        }
        else if(parClient.equals("M01")){
            nbOTMetro[0]++;
            nbOTLignesSpe++;
        }
        else if(parClient.equals("M02")){
            nbOTMetro[1]++;
            nbOTLignesSpe++;
        }
        else if(parClient.equals("M03")){
            nbOTMetro[2]++;
            nbOTLignesSpe++;
        }
        else if(parClient.equals("M04")){
            nbOTMetro[3]++;
            nbOTLignesSpe++;
        }
        else if(parClient.equals("M05")){
            nbOTMetro[4]++;
            nbOTLignesSpe++;
        }
        else if(parClient.equals("M06")){
            nbOTMetro[5]++;
            nbOTLignesSpe++;
        }
        else if(parClient.equals("M07")){
            nbOTMetro[6]++;
            nbOTLignesSpe++;
        }
        else if(parClient.equals("M08")){
            nbOTMetro[7]++;
            nbOTLignesSpe++;
        }
        else if(parClient.equals("M09")){
            nbOTMetro[8]++;
            nbOTLignesSpe++;
        }
        else if(parClient.equals("M10")){
            nbOTMetro[9]++;
            nbOTLignesSpe++;
        }
        else if(parClient.equals("M11")){
            nbOTMetro[10]++;
            nbOTLignesSpe++;
        }
        else if(parClient.equals("M12")){
            nbOTMetro[11]++;
            nbOTLignesSpe++;
        }
        else if(parClient.equals("M13")){
            nbOTMetro[12]++;
            nbOTLignesSpe++;
        }

        nbTotalOT ++;

    }


    public int getNbOTLignesSpe(){
        return nbOTLignesSpe;
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