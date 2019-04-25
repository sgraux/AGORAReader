package data;

public class Data {

    private final String[] centrales = {"CEAS", "PEAS", "DEAS","PEAL"};
    private final String[] telesono = {"ARTS", "MITS", "RTIP", "RTCL", "TSONO"};
    private final String[] video = {"ARVS", "CAVS", "RAVS", "MOVS"};
    private final String[] son = {"SONO", "SONOR", "PUPI", "PUSO", "HPSO"};
    private final String[] phones = {"IVOY", "IVDO", "PUIN", "BUIN", "CAIN", "BMIN", "MPIN"};
    private final String[] tabMois = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Decembre"};
    private final String[] tabLines = {"M01", "M02", "M03", "M04", "M05", "M06", "M07", "M08", "M09", "M10", "M11", "M12", "M13", "RER A", "RER B"};
    private final String[] tabEquip = {"Armoires Fortes", "Centrales d'alarmes", "Telesonorisation", "Caméras", "Sonorisation", "Interphones"};

    public String getCodesEquipement(String parEquipement){
        if(parEquipement.equals("Centrales d'alarmes"))
            return tabToString(centrales);
        else if(parEquipement.equals("Telesonorisation"))
            return tabToString(telesono);
        else if(parEquipement.equals("Caméras"))
            return tabToString(video);
        else if(parEquipement.equals("Sonorisation"))
            return tabToString(son);
        else if(parEquipement.equals("Interphones"))
            return tabToString(phones);
        else if(parEquipement.equals("Armoires Fortes"))
            return "ARFO";
        return null;
    }

    public String tabToString(String[] parTab){
        String res = "[ ";
        for(int i = 0; i < parTab.length; i++){
            res += parTab[i];
            if(i == parTab.length-1)
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
