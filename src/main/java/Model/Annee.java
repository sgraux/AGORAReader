package Model;

import data.Data;

/**[Modele de donnees] Gere une annee
 * @author Sean Graux
 * @version 1.0
 */
public class Annee implements Comparable<Annee>{

    private int anneeInt;
    private Mois[] mois = new Mois[12];
    private Data data = new Data();

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
            else if(parEquipement.equals("Superviseur"))
                OTsLinesEquipement = this.mois[i].getSuperviseur().getOTsLines();
            else if(parEquipement.equals("Trottoir roulant"))
                OTsLinesEquipement = this.mois[i].getTrottoirRoulant().getOTsLines();
            else if(parEquipement.equals("Escalier mécanique"))
                OTsLinesEquipement = this.mois[i].getEscalierMecanique().getOTsLines();
            else if(parEquipement.equals("Ascenseur"))
                OTsLinesEquipement = this.mois[i].getAscenseur().getOTsLines();
            else if(parEquipement.equals("Grilles"))
                OTsLinesEquipement = this.mois[i].getGrilles().getOTsLines();
            else if(parEquipement.equals("Fermeture automatique"))
                OTsLinesEquipement = this.mois[i].getFermetureAutomatique().getOTsLines();

            for (int j = 0; j < 15; j++) {
                tabOTsLines[j] += OTsLinesEquipement[j];
            }
        }
        return tabOTsLines;
    }

    public String[] getTopPanneEquipLigne(int parLigne){
        String[] top5PanneEquip = new String[5];
        int[] sommeOTEquipLigne = new int[data.getTabEquip().length];
        int[] temp;
        int[] indicesTop = new int[5];
        int current;
        for(int i = 0; i < mois.length; i++){
            temp = mois[i].getOTEquipLigne(parLigne);
            for(int j = 0; j < temp.length; j ++){
                sommeOTEquipLigne[j] += temp[j];
            }
        }
        for(int i = 0; i < indicesTop.length; i++){
            indicesTop[i] = 0;
        }

        for(int i = 0; i < sommeOTEquipLigne.length; i++){
            current = sommeOTEquipLigne[i];

            if(current >= sommeOTEquipLigne[indicesTop[0]]){
                indicesTop[4] = indicesTop[3];
                indicesTop[3] = indicesTop[2];
                indicesTop[2] = indicesTop[1];
                indicesTop[1] = indicesTop[0];
                indicesTop[0] = i;
            }
            else if(current >= sommeOTEquipLigne[indicesTop[1]]){
                indicesTop[4] = indicesTop[3];
                indicesTop[3] = indicesTop[2];
                indicesTop[2] = indicesTop[1];
                indicesTop[1] = i;
            }
            else if(current >= sommeOTEquipLigne[indicesTop[2]]){
                indicesTop[4] = indicesTop[3];
                indicesTop[3] = indicesTop[2];
                indicesTop[2] = i;
            }
            else if(current >= sommeOTEquipLigne[indicesTop[3]]){
                indicesTop[4] = indicesTop[3];
                indicesTop[3] = i;
            }
            else if(current >= sommeOTEquipLigne[indicesTop[4]]){
                indicesTop[4] = i;
            }
        }

        for(int i = 0; i < top5PanneEquip.length; i++){
            top5PanneEquip[i] = data.getTabEquip()[indicesTop[i]] + "-"+ sommeOTEquipLigne[indicesTop[i]];
        }

        return top5PanneEquip;
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

    public int compareTo(Annee parAnnee) {
        return this.getAnneeInt() - parAnnee.getAnneeInt();
    }
}
