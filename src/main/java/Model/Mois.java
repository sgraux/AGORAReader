package Model;

import data.Data;

import java.util.Hashtable;

/**[Modele de donnees] Gere un mois et tous les OT des equipements surveilles
 * @author Sean Graux
 * @version 1.0
 */
public class Mois {

    //TODO: put equipment into an arraylist
    private FamilleEquipement armoireForte;
    private FamilleEquipement centralesAlarmes;
    private FamilleEquipement teleSono;
    private FamilleEquipement videoSurveillance;
    private FamilleEquipement sono;
    private FamilleEquipement interphones;
    private FamilleEquipement superviseur;
    private FamilleEquipement escalierMecaniqueEtTrottoir;
    private FamilleEquipement ascenseur;
    private FamilleEquipement grillesEtFermeture;

    private Hashtable<String, Integer> hashLieu = new Hashtable<String, Integer>();

    private Data data = new Data();

    private int overallOTsDEBUG;

    public Mois() {
        this.armoireForte = new FamilleEquipement();
        this.centralesAlarmes = new FamilleEquipement();
        this.teleSono = new FamilleEquipement();
        this.videoSurveillance = new FamilleEquipement();
        this.sono = new FamilleEquipement();
        this.interphones = new FamilleEquipement();
        this.superviseur = new FamilleEquipement();
        this.escalierMecaniqueEtTrottoir = new FamilleEquipement();
        this.ascenseur = new FamilleEquipement();
        this.grillesEtFermeture = new FamilleEquipement();
        overallOTsDEBUG = 0;
    }

    public FamilleEquipement getEquipement(String parEquipement){
        if(parEquipement.equals("Armoires Fortes"))
            return getArmoireForte();
        else if(parEquipement.equals("Centrales d'alarmes"))
            return getCentralesAlarmes();
        else if(parEquipement.equals("Telesonorisation"))
            return getTeleSono();
        else if(parEquipement.equals("Caméras"))
            return getVideoSurveillance();
        else if(parEquipement.equals("Sonorisation"))
            return getSono();
        else if(parEquipement.equals("Interphones"))
            return getInterphones();
        else if(parEquipement.equals("Superviseur"))
            return getSuperviseur();
        else if(parEquipement.equals("Commande à distance escalier mécanique et trottoir roulant"))
            return getEscalierMecaniqueEtTrottoir();
        else if(parEquipement.equals("Commande à distance grilles et fermeture automatique"))
            return getGrillesEtFermeture();
        else if(parEquipement.equals("Commande à distance ascenseur"))
            return getAscenseur();
        else
            return null;
    }

    public int[] getOTEquipLigne(int parLigneMetro){
        Data data = new Data();
        int[] nbOtEquip = new int[data.getTabEquip().length];

        for(int i = 0; i < data.getTabEquip().length; i++){
            nbOtEquip[i] = this.getEquipement(data.getTabEquip()[i]).getOTLigneMetro(parLigneMetro);
        }
        return nbOtEquip;
    }

    public void manageEquipement(String parEquipment, String parClient, String parLieu, String parDescOT){
        String[] centrales = {"CEAS", "PEAS", "DEAS","PEAL"}; //TODO: change that with data class
        String[] telesono = {"ARTS", "MITS", "RTIP", "RTCL", "TSONO"}; //TODO: use data.getEquipement
        String[] video = {"ARVS", "CAVS", "RAVS", "MOVS"};
        String[] son = {"SONO", "SONOR", "PUPI", "PUSO", "HPSO"};
        String[] phones = {"IVOY", "IVDO", "PUIN", "BUIN", "CAIN", "BMIN", "MPIN"};
        String[] superviseur = {"MISC", "SCESU"};
        String[] escalier = data.getEscalierMecanique();
        String[] ascenseur = data.getAscenseur();
        String[] grilles = data.getGrilles();

        if(parEquipment.equals("ARFO"))
            armoireForte.manageOT(parClient);
        else{
            for(int i = 0; i < centrales.length; i++)
                if (centrales[i].equals(parEquipment))
                    centralesAlarmes.manageOT(parClient);

            for(int i = 0; i < telesono.length; i++)
                if (telesono[i].equals(parEquipment))
                    teleSono.manageOT(parClient);

            for(int i = 0; i < video.length; i++)
                if (video[i].equals(parEquipment))
                    videoSurveillance.manageOT(parClient);

            for(int i = 0; i < son.length; i++)
                if (son[i].equals(parEquipment))
                    sono.manageOT(parClient);

            for(int i = 0; i < phones.length; i++)
                if (phones[i].equals(parEquipment))
                    interphones.manageOT(parClient);

            for(int i = 0; i < superviseur.length; i++)
                if (superviseur[i].equals(parEquipment))
                    this.superviseur.manageOT(parClient);

            for(int i = 0; i < escalier.length; i++)
                if (escalier[i].equals(parEquipment) && parDescOT.contains("DISTANCE"))
                    this.escalierMecaniqueEtTrottoir.manageOT(parClient);

            for(int i = 0; i < ascenseur.length; i++)
                if (ascenseur[i].equals(parEquipment) && parDescOT.contains("DISTANCE"))
                    this.ascenseur.manageOT(parClient);

            for(int i = 0; i < grilles.length; i++)
                if (grilles[i].equals(parEquipment) && parDescOT.contains("DISTANCE"))
                    this.grillesEtFermeture.manageOT(parClient);
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

    public String getMaxLieu(){
        int max = 0;
        String nomMax = "";
        for(String key : hashLieu.keySet()) {
            if (hashLieu.get(key) > max){
                max = hashLieu.get(key);
            nomMax = key;
            }
        }
        return nomMax;
    }

    public FamilleEquipement getArmoireForte() {
        return armoireForte;
    }

    public void setArmoireForte(FamilleEquipement armoireForte) {
        this.armoireForte = armoireForte;
    }

    public FamilleEquipement getCentralesAlarmes() {
        return centralesAlarmes;
    }

    public void setCentralesAlarmes(FamilleEquipement centralesAlarmes) {
        this.centralesAlarmes = centralesAlarmes;
    }

    public FamilleEquipement getTeleSono() {
        return teleSono;
    }

    public void setTeleSono(FamilleEquipement teleSono) {
        this.teleSono = teleSono;
    }

    public FamilleEquipement getVideoSurveillance() {
        return videoSurveillance;
    }

    public void setVideoSurveillance(FamilleEquipement videoSurveillance) {
        this.videoSurveillance = videoSurveillance;
    }

    public FamilleEquipement getSono() {
        return sono;
    }

    public void setSono(FamilleEquipement sono) {
        this.sono = sono;
    }

    public FamilleEquipement getInterphones() {
        return interphones;
    }

    public void setInterphones(FamilleEquipement interphones) {
        this.interphones = interphones;
    }

    public FamilleEquipement getSuperviseur() {
        return superviseur;
    }

    public void setSuperviseur(FamilleEquipement superviseur) {
        this.superviseur = superviseur;
    }

    public void setOverallOTsDEBUG(int overallOTsDEBUG) {
        this.overallOTsDEBUG = overallOTsDEBUG;
    }

    public FamilleEquipement getEscalierMecaniqueEtTrottoir() {
        return escalierMecaniqueEtTrottoir;
    }

    public FamilleEquipement getAscenseur() {
        return ascenseur;
    }

    public FamilleEquipement getGrillesEtFermeture() {
        return grillesEtFermeture;
    }

    public Data getData() {
        return data;
    }

    public int getOverallOts(){
        return armoireForte.getNbTotalOT() + centralesAlarmes.getNbTotalOT() + teleSono.getNbTotalOT()
                + videoSurveillance.getNbTotalOT() + sono.getNbTotalOT() + interphones.getNbTotalOT()
                + superviseur.getNbTotalOT();
    }

    public int getOverallOTsLignesSpe(){
        return armoireForte.getNbOTLignesSpe() + centralesAlarmes.getNbOTLignesSpe() + teleSono.getNbOTLignesSpe()
                + videoSurveillance.getNbOTLignesSpe() + sono.getNbOTLignesSpe() + interphones.getNbOTLignesSpe() + superviseur.getNbOTLignesSpe();
    }

    @Override
    public String toString() {
        return armoireForte.toString() + "\n" + centralesAlarmes.toString() + "\n" +
                teleSono.toString() + "\n" + videoSurveillance.toString() + "\n" +
                sono.toString() + "\n" + interphones.toString() + "\n";
    }
}
