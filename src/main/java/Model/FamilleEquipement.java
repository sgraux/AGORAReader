package Model;

import data.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

/**[Modele de donnees] Gère les OT pour un type d'équipement
 * @author Sean Graux
 * @version 1.0
 */
public class FamilleEquipement {

    private int nbTotalOT; //total OT pour un équipement pendant un mois
    private int nbOTLignesSpe;
    private Hashtable<String, Integer> hashLignes = new Hashtable<String, Integer>();
    private Data data = new Data();

    public FamilleEquipement() {
        nbOTLignesSpe = 0;
        for(String currentLigne : data.getTabLines()){
            hashLignes.put(currentLigne, 0);
        }
    }

    public void manageOT(String parClient) {
        if(data.validateClient(parClient)) {
            hashLignes.put(parClient, hashLignes.get(parClient) + 1);
            nbOTLignesSpe ++;
        }
        nbTotalOT++;

    }

    public int[] getOTsLines(){//TODO:change name
        int[] tabOTsLines = new int[15];
        int compteur = 0;
        for(Integer currentInteger : hashLignes.values()) {
            tabOTsLines[compteur] = currentInteger;
            compteur ++;
        }
        return tabOTsLines;
    }

    public int getNbOTLignesSpe(){
        return nbOTLignesSpe;
    }

    public int getNbOTLignesSAE(){
        int nb = 0;
        for(String currentLigne : data.getTabLinesSAE()){
            nb += hashLignes.get(currentLigne);
        }
        return nb;
    }

    public int getMaxOT(){
        int max = 0;
        for(String currentLine : data.getTabLinesSAE()){
            if(hashLignes.get(currentLine) > max)
                max = hashLignes.get(currentLine);
        }
        return max;
    }

    public int getNbTotalOT() {
        return nbTotalOT;
    }

    public void setNbTotalOT(int nbTotalOT) {
        this.nbTotalOT = nbTotalOT;
    }

    public int getOTsLigne(String parLigne){//TODO: clean elses
        return hashLignes.get(parLigne);
    }

    @Override
    public String toString() {
        return "FamilleEquipement{";
    }
}