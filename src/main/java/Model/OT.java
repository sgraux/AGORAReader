package Model;

import java.util.Date;
import data.Data;

public class OT implements Comparable<OT>{

    private Date dateOT;
    private int annee;
    private int mois;
    private String familleBM;
    private String lieu;
    private String client;
    private int idClient;
    private Data data = new Data();

    public OT(Date parDate, int parAnnee, int parMois,  String parCodeFamille, String parLieu, String parClient){
        dateOT = parDate;
        annee = parAnnee;
        mois = parMois;
        familleBM = getFamilleFromCode(parCodeFamille);
        lieu = parLieu;
        client = parClient;
        idClient = getIdFromClient(parClient);
    }

    public String getFamilleFromCode(String parCodeFamille){
        return data.whichEquip(parCodeFamille);
    }

    public int getIdFromClient(String parClient){
        return data.getIDLigne(parClient);
    }

    public Date getDateOT() {
        return dateOT;
    }

    public void setDateOT(Date dateOT) {
        this.dateOT = dateOT;
    }

    public String getFamilleBM() {
        return familleBM;
    }

    public void setFamilleBM(String familleBM) {
        this.familleBM = familleBM;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public int getMois() {
        return mois;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }

    public int compareTo(OT parOT) {
        return this.dateOT.compareTo(parOT.getDateOT());
    }

}
