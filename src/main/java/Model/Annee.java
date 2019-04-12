package Model;

/**[Modele de donnees] Gere une annee
 * @author Sean Graux
 * @version 1.0
 */
public class Annee {

    private int anneeInt;
    private Mois[] mois = new Mois[12];

    public Annee(int parAnneeInt){
        anneeInt = parAnneeInt;
        for(int i = 0; i < 12; i++){
            mois[i] = new Mois();
        }
    }

    public int[] getSumOTsLines(String parEquipement){
        int[] tabOTsLines = new int[15];
        for(int i = 0; i < 12; i++) tabOTsLines[i] = 0;

        int[] OTsLinesEquipement = new int[15];

        for(int i = 0; i < 12; i++) {
            if(parEquipement.equals("Armoires Fortes"))
                OTsLinesEquipement = this.mois[i].getArmoireForte().getOTsLines();
            else if(parEquipement.equals("Centrales d'alarmes"))
                OTsLinesEquipement = this.mois[i].getCentralesAlarmes().getOTsLines();
            else if(parEquipement.equals("Telesonorisation"))
                OTsLinesEquipement = this.mois[i].getTeleSono().getOTsLines();
            else if(parEquipement.equals("Caméras"))
                OTsLinesEquipement = this.mois[i].getVideoSurveillance().getOTsLines();
            else if(parEquipement.equals("Sonorisation"))
                OTsLinesEquipement = this.mois[i].getSono().getOTsLines();
            else if(parEquipement.equals("Interphones"))
                OTsLinesEquipement = this.mois[i].getInterphones().getOTsLines();

            for (int j = 0; j < 15; j++) {
                tabOTsLines[j] += OTsLinesEquipement[j];
            }
        }
        return tabOTsLines;
    }

    public int getSumOverallOTs(){
        int sum = 0;
        for(int i = 0; i < 12; i++){
            sum += mois[i].getOverallOts();
        }
        return sum;
    }

    public int getSumOverallOTsDEBUG(){
        int sum = 0;
        for(int i = 0; i < 12; i++){
            sum += mois[i].getOverallOTsDEBUG();
        }
        return sum;
    }

    public Mois getMoisIndex(int parIndex){
        return mois[parIndex];
    }

    public int getAnneeInt() {
        return anneeInt;
    }

    public void setAnneeInt(int anneeInt) {
        this.anneeInt = anneeInt;
    }

    public Mois[] getMois() {
        return mois;
    }

    public void setMois(Mois[] mois) {
        this.mois = mois;
    }

    public String toString(){
        return "----- " + anneeInt + " -----\n"
                +"Janvier : " + mois[0].getOverallOts() + " --- "       + mois[0].getOverallOTsLignesSpe() +"\n"
                + "Février : " + mois[1].getOverallOts() +  " --- "     + mois[1].getOverallOTsLignesSpe() +"\n"
                + "Mars : " + mois[2].getOverallOts() +  " --- "        + mois[2].getOverallOTsLignesSpe() +"\n"
                + "Avril : " + mois[3].getOverallOts() +  " --- "       + mois[3].getOverallOTsLignesSpe() +"\n"
                + "Mai : " + mois[4].getOverallOts() +  " --- "         + mois[4].getOverallOTsLignesSpe() +"\n"
                + "Juin : " + mois[5].getOverallOts() +  " --- "        + mois[5].getOverallOTsLignesSpe() +"\n"
                + "Juillet : " + mois[6].getOverallOts() +  " --- "     + mois[6].getOverallOTsLignesSpe() +"\n"
                + "Août : " + mois[7].getOverallOts() +  " --- "        + mois[7].getOverallOTsLignesSpe() +"\n"
                + "Septembre : " + mois[8].getOverallOts() +  " --- "   + mois[8].getOverallOTsLignesSpe() +"\n"
                + "Octobre : " + mois[9].getOverallOts() +  " --- "     + mois[9].getOverallOTsLignesSpe() +"\n"
                + "Novembre : " + mois[10].getOverallOts() +  " --- "   + mois[10].getOverallOTsLignesSpe() +"\n"
                + "Décembre : " + mois[11].getOverallOts() +  " --- "   + mois[11].getOverallOTsLignesSpe() +"\n";
    }
}
