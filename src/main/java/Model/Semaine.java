package Model;

import data.Data;

public class Semaine {

    private int numeroSemaine;
    private int numeroMois;
    //private Jour[] tabJours = new Jour[7];

    private FamilleEquipement armoireForte;
    private FamilleEquipement centralesAlarmes;
    private FamilleEquipement teleSono;
    private FamilleEquipement videoSurveillance;
    private FamilleEquipement sono;
    private FamilleEquipement interphones;
    private int overallOTsDEBUG;

    public Semaine(int parNumeroSemaine, int parNumeroMois) {

        numeroSemaine = parNumeroSemaine;
        numeroMois = parNumeroMois;
        this.armoireForte = new FamilleEquipement();
        this.centralesAlarmes = new FamilleEquipement();
        this.teleSono = new FamilleEquipement();
        this.videoSurveillance = new FamilleEquipement();
        this.sono = new FamilleEquipement();
        this.interphones = new FamilleEquipement();
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
        String[] centrales = {"CEAS", "PEAS", "DEAS","PEAL"};
        String[] telesono = {"ARTS", "MITS", "RTIP", "RTCL", "TSONO"};
        String[] video = {"ARVS", "CAVS", "RAVS", "MOVS"};
        String[] son = {"SONO", "SONOR", "PUPI", "PUSO", "HPSO"};
        String[] phones = {"IVOY", "IVDO", "PUIN", "BUIN", "CAIN", "BMIN", "MPIN"};

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

    public int getOverallOts(){
        return armoireForte.getNbTotalOT() + centralesAlarmes.getNbTotalOT() + teleSono.getNbTotalOT()
                + videoSurveillance.getNbTotalOT() + sono.getNbTotalOT() + interphones.getNbTotalOT();
    }

    public int getOverallOTsLignesSpe(){
        return armoireForte.getNbOTLignesSpe() + centralesAlarmes.getNbOTLignesSpe() + teleSono.getNbOTLignesSpe()
                + videoSurveillance.getNbOTLignesSpe() + sono.getNbOTLignesSpe() + interphones.getNbOTLignesSpe();
    }

    @Override
    public String toString() {
        return armoireForte.toString() + "\n" + centralesAlarmes.toString() + "\n" +
                teleSono.toString() + "\n" + videoSurveillance.toString() + "\n" +
                sono.toString() + "\n" + interphones.toString() + "\n";
    }
}