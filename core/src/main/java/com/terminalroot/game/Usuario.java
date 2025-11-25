package com.terminalroot.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

// atributos basicos do usuario
public class Usuario {
    private int HP;
    private int HP_MAXIMO;
    private int FORCA;
    private int INTELIGENCIA;
    private int VITALIDADE = 20;
    private int STAMINA;
    private final int STAMINA_MAXIMA = 100;

    private Texture textura;
    private float x, y, w, h;

    public Usuario(String texturaPath) {
        VITALIDADE = 20;
        HP_MAXIMO = VITALIDADE * 2;
        HP = HP_MAXIMO;
        FORCA = 5;
        INTELIGENCIA = 5;
        STAMINA = STAMINA_MAXIMA;
        textura = new Texture(texturaPath);
    }

    public void draw(Batch batch) {
        batch.draw(textura, x, y, w, h);
    }

    // funcao que aplica dano ao usuario
    public void TOMA_DANO(int dano) {
        HP -= dano;
        if (HP < 0)
            HP = 0;
        System.out.println("Jogador levou " + dano + " de dano! HP: " + HP);
    }

    // verifica se o usuario esta vivo
    public boolean ESTADO() {
        return HP > 0;
    }

    public void setPosicao(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setTamanho(float w, float h) {
        this.w = w;
        this.h = h;
    }

    public int getHP() {
        return HP;

    }

    public int getHP_MAXIMO() {
        return HP_MAXIMO;
    }

    public int getFORCA() {
        return FORCA;
    }

    public int getINTELIGENCIA() {
        return INTELIGENCIA;
    }

    public int getVITALIDADE() {
        return VITALIDADE;
    }

    public int getSTAMINA() {
        return this.STAMINA;
    }

    public int getSTAMINA_MAXIMA() {
        return this.STAMINA_MAXIMA;
    }

    public boolean temStamina(int custo) {
        return this.STAMINA >= custo;
    }

    public void gastaStamina(int custo) {
        this.STAMINA -= custo;
        if (this.STAMINA < 0){
            this.STAMINA = 0;
        }
    }

    public void recuperaStamina() {
        this.STAMINA += 15;
        if (this.STAMINA > STAMINA_MAXIMA){
            this.STAMINA = STAMINA_MAXIMA;
        }
    }

    public void dispose() {
        if (textura != null)
            textura.dispose();
    }
}
