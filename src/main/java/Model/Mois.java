package Model;

import data.Data;

/**[Modele de donnees] Gere un mois et tous les OT des equipements surveilles
 * @author Sean Graux
 * @version 1.0
 */
public class Mois {

    private FamilleEquipement armoireForte;
    private FamilleEquipement centralesAlarmes;
    private FamilleEquipement teleSono;
    private FamilleEquipement videoSurveillance;
    private FamilleEquipement sono;
    private FamilleEquipement interphones;
    private FamilleEquipement superviseur;
    private FamilleEquipement trottoirRoulant;
    private FamilleEquipement escalierMecanique;
    private FamilleEquipement ascenseur;
    private FamilleEquipement grilles;
    private FamilleEquipement fermetureAutomatique;
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
        this.trottoirRoulant = new FamilleEquipement();
        this.escalierMecanique = new FamilleEquipement();
        this.ascenseur = new FamilleEquipement();
        this.grilles = new FamilleEquipement();
        this.fermetureAutomatique = new FamilleEquipement();
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
        else if(parEquipement.equals("Trottoir roulant"))
            return getTrottoirRoulant();
        else if(parEquipement.equals("Escalier mécanique"))
            return getEscalierMecanique();
        else if(parEquipement.equals("Ascenseur"))
            return getAscenseur();
        else if(parEquipement.equals("Grilles"))
            return getGrilles();
        else if(parEquipement.equals("Fermeture automatique"))
            return getFermetureAutomatique();
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

    public void manageEquipement(String parEquipment, String parClient){
        String[] centrales = {"CEAS", "PEAS", "DEAS","PEAL"}; //TODO: change that with data class
        String[] telesono = {"ARTS", "MITS", "RTIP", "RTCL", "TSONO"};
        String[] video = {"ARVS", "CAVS", "RAVS", "MOVS"};
        String[] son = {"SONO", "SONOR", "PUPI", "PUSO", "HPSO"};
        String[] phones = {"IVOY", "IVDO", "PUIN", "BUIN", "CAIN", "BMIN", "MPIN"};
        String[] superviseur = {"MISC", "SCESU"};
        String[] escalier = data.getEscalierMecanique();
        String[] ascenseur = data.getAscenseur();
        String[] grilles = data.getGrilles();
        String[] fermeture = data.getFermeture();

        if(parEquipment.equals("ARFO"))
            armoireForte.manageOT(parClient);
        else if(parEquipment.equals("PALE"))
            trottoirRoulant.manageOT(parClient);
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
                if (escalier[i].equals(parEquipment))
                    this.escalierMecanique.manageOT(parClient);

            for(int i = 0; i < ascenseur.length; i++)
                if (ascenseur[i].equals(parEquipment))
                    this.ascenseur.manageOT(parClient);

            for(int i = 0; i < grilles.length; i++)
                if (grilles[i].equals(parEquipment))
                    this.grilles.manageOT(parClient);

            for(int i = 0; i < fermeture.length; i++)
                if (fermeture[i].equals(parEquipment))
                    this.fermetureAutomatique.manageOT(parClient);
        }

        if(!parClient.equals("NA")) overallOTsDEBUG ++;

    }

    public int getOverallOTsDEBUG() {
        return overallOTsDEBUG;
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

    public FamilleEquipement getTrottoirRoulant() {
        return trottoirRoulant;
    }

    public FamilleEquipement getEscalierMecanique() {
        return escalierMecanique;
    }

    public FamilleEquipement getAscenseur() {
        return ascenseur;
    }

    public FamilleEquipement getGrilles() {
        return grilles;
    }

    public FamilleEquipement getFermetureAutomatique() {
        return fermetureAutomatique;
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
