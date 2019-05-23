package Model;

import data.Data;

import java.util.ArrayList;

/**
 * [Modele de donnees] Gere une annee
 *
 * @author Sean Graux
 * @version 1.0
 */
public class Annee implements Comparable<Annee> {

    private int anneeInt;
    //private Semaine[] tabSemainesAnnee = new Semaine[52];
    private ArrayList<OT> listOTs = new ArrayList<OT>();
    private Data data = new Data();

    private int[][][] tabEquipLignesMois = new int[6][15][12];
    private int[][] moisFamilleBM = new int[12][6];

    public Annee(int parAnneeInt) {
        anneeInt = parAnneeInt;
        /*for(int i = 0; i < 12; i++){
            tabSemainesAnnee[i] = new Semaine();
        }*/
    }

    public void iterateOTs() {

        int[][][] tabEquipLignesMois = new int[6][15][12];

        int[][] moisEquip = new int[12][6];

        for (int i = 0; i < 6; i++) {//set everything to 0
            for(int j = 0; j < 15; j++ ) {
                for(int h = 0; h < 12; h++) {
                    tabEquipLignesMois[i][j][h] = 0;
                }
            }
        }

        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 6; j++){
                moisEquip[i][j] = 0;
            }
        }



        for (OT currentOT : listOTs) {
            ///////////////////////// SUM OTS LINES /////////////////////////////
            if (currentOT.getIdClient() != -1) {
                if (currentOT.getFamilleBM().equals("Armoires Fortes")) {
                    tabEquipLignesMois[0][currentOT.getIdClient()][currentOT.getMois()-1]++;
                    moisEquip[currentOT.getMois()-1][0] ++;
                }
                else if (currentOT.getFamilleBM().equals("Centrales d'alarmes")) {
                    tabEquipLignesMois[1][currentOT.getIdClient()][currentOT.getMois()-1]++;
                    moisEquip[currentOT.getMois()-1][1] ++;
                }
                else if (currentOT.getFamilleBM().equals("Telesonorisation")) {
                    tabEquipLignesMois[2][currentOT.getIdClient()][currentOT.getMois()-1]++;
                    moisEquip[currentOT.getMois()-1][2] ++;
                }
                else if (currentOT.getFamilleBM().equals("Caméras")) {
                    tabEquipLignesMois[3][currentOT.getIdClient()][currentOT.getMois()-1]++;
                    moisEquip[currentOT.getMois()-1][3] ++;
                }
                else if (currentOT.getFamilleBM().equals("Sonorisation")) {
                    tabEquipLignesMois[4][currentOT.getIdClient()][currentOT.getMois()-1]++;
                    moisEquip[currentOT.getMois()-1][4] ++;
                }
                else if (currentOT.getFamilleBM().equals("Interphones")) {
                    tabEquipLignesMois[5][currentOT.getIdClient()][currentOT.getMois()-1]++;
                    moisEquip[currentOT.getMois()-1][5] ++;
                }
            }
        }

        this.tabEquipLignesMois = tabEquipLignesMois;
        this.moisFamilleBM = moisEquip;
    }

    /*public int[] getSumOTsLines(String parEquipement){
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
    }*/

    /*
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
    */

    public int getAnneeInt() {
        return anneeInt;
    }

    public void setAnneeInt(int anneeInt) {
        this.anneeInt = anneeInt;
    }

    /*public Mois[] getMois() {
        return mois;
    }*/

    /*public void setMois(Mois[] mois) {
        this.mois = mois;
    }*/

    /*public String toString(){
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
    }*/

    public int[] getSumOTsLines(int numFamilleEquip) {
        int sommeH = 0; //somme des mois
        int[] sommeOtLigne = new int[15];

        for(int j = 0; j < 15; j ++){
            for(int h = 0; h < 12; h ++){
                sommeH += tabEquipLignesMois[numFamilleEquip][j][h];
            }
            sommeOtLigne[j] = sommeH;
            sommeH = 0;
        }
        return sommeOtLigne;
    }

    public int compareTo(Annee parAnnee) {
        return this.getAnneeInt() - parAnnee.getAnneeInt();
    }

    public ArrayList<OT> getListOTs() {
        return listOTs;
    }

    public void setListOTs(ArrayList<OT> listOTs) {
        this.listOTs = listOTs;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int[][] getMoisFamilleBM() {
        return moisFamilleBM;
    }

    public void setMoisFamilleBM(int[][] moisFamilleBM) {
        this.moisFamilleBM = moisFamilleBM;
    }

    public int getTabMoisFamilleIndex(int indexI, int indexJ){
        return moisFamilleBM[indexI][indexJ];
    }
}
