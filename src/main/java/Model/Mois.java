package Model;

import data.Data;

import java.util.ArrayList;
import java.util.Hashtable;

/**[Modele de donnees] Gere un mois et tous les OT des equipements surveilles
 * @author Sean Graux
 * @version 1.0
 */
public class Mois { //Décris un mois

    //Liste de famille d'équipement
    //private Hashtable<String, FamilleEquipement> hashEquipements = new Hashtable<String, FamilleEquipement>();
    private Data data = new Data();
    private FamilleEquipement[] listeEquipements = new FamilleEquipement[data.getTabEquip().length];
    private int overallOTsDEBUG;

    public Mois() {
        /*for(String currentFamille : data.getTabEquip()){
            // hashEquipements.put(currentFamille, new FamilleEquipement());
        }*/
        for(int i = 0; i < data.getTabEquip().length; i++ ){
            listeEquipements[i] = new FamilleEquipement();
        }
        overallOTsDEBUG = 0;
    }

    //Gère la famille d'équipement
    public void manageEquipement(String parCodeEquipement, String parClient, String parDescOT){
        /*String famille = data.getFamilleFromCodeTEMP(parCodeEquipement);
        if(!famille.equals("")) {
                hashEquipements.get(famille).manageOT(parClient);
        }*/
        String famille = data.getFamilleFromCodeTEMP(parCodeEquipement);
        int indiceFamille = data.getIndexFamille(famille);
        if(!famille.equals("")) {
            if (famille.contains("Commande")) {
                if (parDescOT.contains("COMMANDE"))
                    listeEquipements[indiceFamille].manageOT(parClient);
            }
            else listeEquipements[indiceFamille].manageOT(parClient);
        }

        if(!parClient.equals("NA")) overallOTsDEBUG ++;
    }

    public int getOverallOTsDEBUG() {
        return overallOTsDEBUG;
    }

    public FamilleEquipement getEquipement(String parEquipement){
        //return hashEquipements.get(parEquipement);
        return listeEquipements[data.getIndexFamille(parEquipement)];
    }

    public int[] getOTEquipLigne(String parLigne){
        Data data = new Data();
        int[] nbOtEquip = new int[data.getTabEquip().length];

        for(int i = 0; i < data.getTabEquip().length; i++){
            nbOtEquip[i] = this.getEquipement(data.getTabEquip()[i]).getOTsLigne(parLigne);
        }
        return nbOtEquip;
    }

    public Data getData() {
        return data;
    }

    public int getOverallOts(){
        int res = 0;
        /*for(FamilleEquipement famille : hashEquipements.values()){
            res = famille.getNbTotalOT();
        }*/
        for(FamilleEquipement famille : listeEquipements){
            res = famille.getNbTotalOT();
        }
        return res;
    }

    public int getOverallOTsLignesSpe(){
        int res = 0;
        /*for(FamilleEquipement famille : hashEquipements.values()){
            res = famille.getNbOTLignesSpe();
        }*/
        for(FamilleEquipement famille : listeEquipements){
            res = famille.getNbOTLignesSpe();
        }
        return res;
    }

    @Override
    public String toString() {
        return "";
    }
}
