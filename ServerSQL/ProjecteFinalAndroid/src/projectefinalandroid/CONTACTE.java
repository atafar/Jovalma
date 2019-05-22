/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectefinalandroid;

/**
 *
 * @author Aleix
 */
public class CONTACTE {

    private int TELEFON;
    private String NOM;
    private String DIRECTORA;
    private String SERVEI;
    private String ADRECA;
    private double LONGITUT;
    private double LATITUT;

    public CONTACTE(int TELEFON, String NOM, String DIRECTORA, String SERVEI, String ADRECA, double LONGITUT, double LATITUT) {
        this.TELEFON = TELEFON;
        this.NOM = NOM;
        this.DIRECTORA = DIRECTORA;
        this.SERVEI = SERVEI;
        this.ADRECA = ADRECA;
        this.LONGITUT = LONGITUT;
        this.LATITUT = LATITUT;
    }

    public int getTELEFON() {
        return TELEFON;
    }

    public String getNOM() {
        return NOM;
    }

    public String getDIRECTORA() {
        return DIRECTORA;
    }

    public String getSERVEI() {
        return SERVEI;
    }

    public String getADRECA() {
        return ADRECA;
    }

    public double getLONGITUT() {
        return LONGITUT;
    }

    public double getLATITUT() {
        return LATITUT;
    }

}
