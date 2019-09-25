package Model;

import data.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

/**[Modele de donnees] Gère les OT pour un type d'équipement
 * @author Sean Graux
 * @version 1.0
 */
public class FamilleEquipement {//Décris une famille d'équipement

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

    //Gère la ligne (le client)
    public void manageOT(String parClient) {
        if(data.validateClient(parClient)) {
            hashLignes.put(parClient, hashLignes.get(parClient) + 1);
            nbOTLignesSpe ++;
        }
        nbTotalOT++;
    }

    //Retourne un tableau contenant les sommes des OTS pour chaque ligne
    public int[] getOTsLines(){
        int[] tabOTsLines = new int[15];
        int compteur = 0;
        for(Integer currentInteger : hashLignes.values()) {
            tabOTsLines[compteur] = currentInteger;
            compteur ++;
        }
        return tabOTsLines;
    }

    //Retourne la somme d'OTs des lignes 1,3,4 et 13
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

    public int getOTsLigne(String parLigne){
        return hashLignes.get(parLigne);
    }

    public int getNbOTLignesSpe(){
        return nbOTLignesSpe;
    }

    @Override
    public String toString() {
        return "FamilleEquipement{";
    }
}