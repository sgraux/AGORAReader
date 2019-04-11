package Model;

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
    private int overallOTsDEBUG;

    public Mois() {
        this.armoireForte = new FamilleEquipement();
        this.centralesAlarmes = new FamilleEquipement();
        this.teleSono = new FamilleEquipement();
        this.videoSurveillance = new FamilleEquipement();
        this.sono = new FamilleEquipement();
        this.interphones = new FamilleEquipement();
        overallOTsDEBUG = 0;
    }

    public FamilleEquipement getEquipement(String parEquipement){
        if(parEquipement.equals("armoire"))
            return getArmoireForte();
        else if(parEquipement.equals("centrale"))
            return getCentralesAlarmes();
        else if(parEquipement.equals("telesono"))
            return getTeleSono();
        else if(parEquipement.equals("video"))
            return getVideoSurveillance();
        else if(parEquipement.equals("sono"))
            return getSono();
        else if(parEquipement.equals("interphones"))
            return getInterphones();
        else
            return null;
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
