package data;

public class Data {

    private final String[] centrales = {"CEAS", "PEAS", "DEAS", "PEAL"};
    private final String[] telesono = {"ARTS", "MITS", "RTIP", "RTCL", "TSONO", "PUTS"};
    private final String[] video = {"ARVS", "CAVS", "RAVS", "MOVS"};
    private final String[] son = {"SONO", "SONOR", "PUPI", "PUSO", "HPSO", "ARSO"};
    private final String[] phones = {"IVOY", "IVDO", "PUIN", "BUIN", "CAIN", "BMIN", "MPIN"};
    private final String[] superviseur = {"MISC", "SCESU"};
    private final String[] escalierMecanique = {"CNI", "FUJ", "KON", "O.K", "OTI", "SCH", "SPEC", "THY"};
    private final String[] ascenseur = {"A000","A001", "A002", "A003", "A004", "A005", "A007"};
    private final String[] grilles = {"BL", "BR", "CI", "GC", "GLE", "GR", "GVP", "PB", "PBH", "PTEBV", "PTEC", "PTLBV", "PTLC", "RM", "VR", "VRA"};
    private final String[] fermeture = {"GS", "GR"};

    private final String[] tabMois = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Decembre"};
    private final String[] tabLines = {"M01", "M02", "M03", "M04", "M05", "M06", "M07", "M08", "M09", "M10", "M11", "M12", "M13", "RER A", "RER B"};
    private final String[] tabEquip = {"Armoires Fortes", "Centrales d'alarmes", "Telesonorisation", "Caméras", "Sonorisation", "Interphones", "Superviseur", "Trottoir roulant", "Escalier mécanique", "Ascenseur", "Grilles", "Fermeture automatique"};

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
        else if (parEquipement.equals("Trottoir roulant"))
            return "PALE";
        else if (parEquipement.equals("Escalier mécanique"))
            return tabToString(escalierMecanique);
        else if (parEquipement.equals("Ascenseur"))
            return tabToString(ascenseur);
        else if (parEquipement.equals("Grilles"))
            return tabToString(grilles);
        else if (parEquipement.equals("Fermeture automatique"))
            return tabToString(fermeture);
        return null;
    }

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
        return escalierMecanique;
    }

    public String[] getAscenseur() {
        return ascenseur;
    }

    public String[] getGrilles() {
        return grilles;
    }

    public String[] getFermeture() {
        return fermeture;
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


}
