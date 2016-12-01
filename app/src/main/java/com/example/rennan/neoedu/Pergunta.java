package com.example.rennan.neoedu;

/**
 * Created by Rennan on 01/12/2016.
 */

public class Pergunta {
    private String enunciado;
    private String LA;
    private String LB;
    private String LC;
    private String LD;
    private String LE;
    private int certo;

    public Pergunta (String enunciado, String LA, String LB, String LC, String LD, String LE, int certo){
        this.enunciado = enunciado;
        this.LA = LA;
        this.LB = LB;
        this.LC = LC;
        this.LD = LD;
        this.LE = LE;
        this.certo = certo;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public String getLA() {
        return LA;
    }

    public String getLB() {
        return LB;
    }

    public String getLC() {
        return LC;
    }

    public String getLD() {
        return LD;
    }

    public String getLE() {
        return LE;
    }

    public boolean isCerto(int cd) {
        return certo==cd;
    }
}
