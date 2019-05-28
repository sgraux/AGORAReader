package Model;

import data.Data;

import java.util.ArrayList;
import java.util.Hashtable;

/**[Modele de donnees] Gere un mois et tous les OT des equipements surveilles
 * @author Sean Graux
 * @version 1.0
 */
public class Mois {

    private Hashtable<String, FamilleEquipement> hashEquipements = new Hashtable<String, FamilleEquipement>();
    private Hashtable<String, Integer> hashLieu = new Hashtable<String, Integer>();
    private Data data = new Data();

    private int overallOTsDEBUG;

    public Mois() {
        for(String currentFamille : data.getTabEquip()){
            hashEquipements.put(currentFamille, new FamilleEquipement());
        }
        overallOTsDEBUG = 0;
    }

    public FamilleEquipement getEquipement(String parEquipement){
        return hashEquipements.get(parEquipement);
    }

    public int[] getOTEquipLigne(String parLigne){
        Data data = new Data();
        int[] nbOtEquip = new int[data.getTabEquip().length];

        for(int i = 0; i < data.getTabEquip().length; i++){
            nbOtEquip[i] = this.getEquipement(data.getTabEquip()[i]).getOTsLigne(parLigne);
        }
        return nbOtEquip;
    }

    public void manageEquipement(String parCodeEquipement, String parClient, String parLieu, String parDescOT){
        String famille = data.getFamilleFromCodeTEMP(parCodeEquipement);
        if(!famille.equals("")) {
            if (famille.contains("escalier") || famille.contains("ascenseur") || famille.contains("grilles")) {
                if (parDescOT.contains("COMMANDE"))
                    hashEquipements.get(famille).manageOT(parClient);

            } else hashEquipements.get(famille).manageOT(parClient);
        }

        if(!parClient.equals("NA")) overallOTsDEBUG ++;

        if(hashLieu.get(parLieu) != null)
            hashLieu.put(parLieu, hashLieu.get(parLieu)+1);
        else
            hashLieu.put(parLieu, 1);

    }

    public int getOverallOTsDEBUG() {
        return overallOTsDEBUG;
    }

    public String[] getMaxLieu(){//todo top 3 lieux
        int max = 0;
        String[] res = {"",""};
        for(String key : hashLieu.keySet()) {
            if (hashLieu.get(key) > max){
                max = hashLieu.get(key);
            res[0] = key;
            res[1] = hashLieu.get(key).toString();
            }
        }
        return res;
    }

    public Data getData() {
        return data;
    }

    public int getOverallOts(){
        int res = 0;
        for(FamilleEquipement famille : hashEquipements.values()){
            res = famille.getNbTotalOT();
        }
        return res;
    }

    public int getOverallOTsLignesSpe(){
        int res = 0;
        for(FamilleEquipement famille : hashEquipements.values()){
            res = famille.getNbOTLignesSpe();
        }
        return res;
    }

    public Hashtable<String, Integer> getHashLieu() {
        return hashLieu;
    }

    @Override
    public String toString() {
        return "";
    }
}
