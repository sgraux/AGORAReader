package Model;

public class Annee {

    private int anneeInt;
    private Mois[] mois = new Mois[12];

    public Annee(int parAnneeInt){
        anneeInt = parAnneeInt;
        for(int i = 0; i < 12; i++){
            mois[i] = new Mois();
        }
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
        return "Year : " + anneeInt;
    }
}
