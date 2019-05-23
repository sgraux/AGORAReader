package Model;

import data.Data;

import java.util.ArrayList;
import java.util.Arrays;

/**[Modele de donnees] Gère les OT pour un type d'équipement
 * @author Sean Graux
 * @version 1.0
 */
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

    public void manageOT(String parClient) {
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

    public int[] getOTsLines(){
        int[] tabOTsLines = new int[15];
        for(int i = 0; i < nbOTMetro.length; i++)
            tabOTsLines[i] = nbOTMetro[i];
        tabOTsLines[13] = nbOTRERA;
        tabOTsLines[14] = nbOTRERB;
        return tabOTsLines;
    }

    public int getNbOTLignesSpe(){
        return nbOTLignesSpe;
    }

    public int getNbOTLignesSAE(){
        return nbOTMetro[0] + nbOTMetro[2] + nbOTMetro[3] + nbOTMetro[12];
    }

    public int getNbTotalOT() {
        return nbTotalOT;
    }

    public void setNbTotalOT(int nbTotalOT) {
        this.nbTotalOT = nbTotalOT;
    }

    public int getOTsLigne(String parLigne){
        if(parLigne.equals("RER A")) return nbOTRERA;
        else if(parLigne.equals("RER B")) return nbOTRERB;
        else if(parLigne.equals("M01")) return nbOTMetro[0];
        else if(parLigne.equals("M02")) return nbOTMetro[1];
        else if(parLigne.equals("M03")) return nbOTMetro[2];
        else if(parLigne.equals("M04")) return nbOTMetro[3];
        else if(parLigne.equals("M05")) return nbOTMetro[4];
        else if(parLigne.equals("M06")) return nbOTMetro[5];
        else if(parLigne.equals("M07")) return nbOTMetro[6];
        else if(parLigne.equals("M08")) return nbOTMetro[7];
        else if(parLigne.equals("M09")) return nbOTMetro[8];
        else if(parLigne.equals("M10")) return nbOTMetro[9];
        else if(parLigne.equals("M11")) return nbOTMetro[10];
        else if(parLigne.equals("M12")) return nbOTMetro[11];
        else if(parLigne.equals("M13")) return nbOTMetro[12];
        else return 0;

    }

    public int getOTLigneMetro(int parNumeroLigne){
        return nbOTMetro[parNumeroLigne - 1];
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