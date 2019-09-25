package data;

public class Data {//Contient les données utilisées par l'outil

    //liste des codes par famille d'équipement (une liste de code par famille)
    private final String[] armoires = {"ARFO"};
    private final String[] centrales = {"CEAS", "PEAS", "DEAS", "PEAL"};
    private final String[] telesono = {"ARTS", "MITS", "RTIP", "RTCL", "TSONO", "PUTS"};
    private final String[] video = {"ARVS", "CAVS", "RAVS", "MOVS"};
    private final String[] son = {"SONO", "SONOR", "PUPI", "PUSO", "HPSO", "ARSO"};
    private final String[] phones = {"IVOY", "IVDO", "PUIN", "BUIN", "CAIN", "BMIN", "MPIN"};
    private final String[] superviseur = {"MISC", "SCESU"};
    private final String[] escalierMecaniqueEtTrottoir = {"CNI", "FUJ", "KON", "O.K", "OTI", "SCH", "SPEC", "THY", "PALE"};
    private final String[] ascenseur = {"A000","A001", "A002", "A003", "A004", "A005", "A007"};
    private final String[] grillesEtFermeture = {"BL", "BR", "CI", "GC", "GLE", "GR", "GVP", "PB", "PBH", "PTEBV", "PTEC", "PTLBV", "PTLC", "RM", "VR", "VRA", "GS"};
    private final String[] PISI = {"PISI"};

    //Tableau contenant les liste de code
    private final String[][] tabFamille = {armoires, centrales, telesono, video, son,phones, superviseur, escalierMecaniqueEtTrottoir, ascenseur, grillesEtFermeture, PISI};

    //private final String[] tabFamilleCommandeADistance = {"Commande à distance escalier mécanique et trottoir roulant", "Commande à distance ascenseur", "Commande à distance grilles et fermeture automatique"};

    private final String[] tabMois = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Decembre"};
    private final String[] tabLines = {"M01", "M02", "M03", "M04", "M05", "M06", "M07", "M08", "M09", "M10", "M11", "M12", "M13", "RER A", "RER B"};
    private final String[] tabLinesSAE = {"M01", "M03", "M04", "M13"};

    //Tableau contenant le nom des familles d'équipement
    private final String[] tabEquip = {"Armoires Fortes", "Centrales d'alarmes", "Telesonorisation", "Caméras", "Sonorisation", "Interphones", "Superviseur", "Commande à distance escalier mécanique et trottoir roulant", "Commande à distance ascenseur", "Commande à distance grilles et fermeture automatique", "PISI"};

    public int getIndexFamille(String parFamille){
        int i = 0;
        int index = -1;
        while(i < tabEquip.length && index < 0){
            if(parFamille.equals(tabEquip[i]))
                index = i;
            i++;
        }
        return index;
    }

    //Vérifie que le client est bien une ligne de transport
    public boolean validateClient(String parClient){
        boolean res = false;
        for(String curentLine : tabLines){
            if(parClient.equals(curentLine))
                res = true;
        }
        return res;
    }

    //Donne le nom d'une famille à partir d'un code d'équipement
    public String getFamilleFromCodeTEMP(String parCodeEquipement){
        String famille = "";
        for(int i = 0; i < tabEquip.length; i++){
            for(int j = 0; j < tabFamille[i].length; j++){
                if(tabFamille[i][j].equals(parCodeEquipement))
                    famille = tabEquip[i];
            }
        }
        return famille;
    }

    //Donne une chaine de caractère contenant tout les codes pour une famille d'équipement donné
    public String getCodesEquipement(String parEquipement) {
        if (parEquipement.equals("Centrales d'alarmes"))
            return tabToString(centrales);
        else if (parEquipement.equals("Telesonorisation"))
            return tabToString(telesono);
        else if (parEquipement.equals("Caméras"))
            return tabToString(video);
        else if (parEquipement.equals("Sonorisation"))
            return tabToString(son);
        else if (parEquipement.equals("Interphones"))
            return tabToString(phones);
        else if (parEquipement.equals("Armoires Fortes"))
            return "ARFO";
        else if (parEquipement.equals("Superviseur"))
            return tabToString(superviseur);
        else if (parEquipement.equals("Commande à distance escalier mécanique et trottoir roulant"))
            return tabToString(escalierMecaniqueEtTrottoir);
        else if (parEquipement.equals("Commande à distance ascenseur"))
            return tabToString(ascenseur);
        else if (parEquipement.equals("Commande à distance grilles et fermeture automatique"))
            return tabToString(grillesEtFermeture);
        else if (parEquipement.equals("PISI"))
            return tabToString(PISI);
        return null;
    }

    //ToString, Getters et Setters
    public String tabToString(String[] parTab) {
        String res = "[ ";
        for (int i = 0; i < parTab.length; i++) {
            res += parTab[i];
            if (i == parTab.length - 1)
                res += "]";
            else
                res += " , ";
        }
        return res;
    }

    public String[] getCentrales() {
        return centrales;
    }

    public String[] getTelesono() {
        return telesono;
    }

    public String[] getVideo() {
        return video;
    }

    public String[] getSon() {
        return son;
    }

    public String[] getPhones() {
        return phones;
    }

    public String[] getSuperviseur() {
        return superviseur;
    }

    public String[] getEscalierMecanique() {
        return escalierMecaniqueEtTrottoir;
    }

    public String[] getAscenseur() {
        return ascenseur;
    }

    public String[] getGrilles() {
        return grillesEtFermeture;
    }

    public String[] getTabMois() {
        return tabMois;
    }

    public String[] getTabLines() {
        return tabLines;
    }

    public String[] getTabEquip() {
        return tabEquip;
    }

    public String[] getTabLinesSAE() {
        return tabLinesSAE;
    }
}
