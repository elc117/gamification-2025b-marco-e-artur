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

    
    private Texture textura;
    private float x, y, w, h;

    public Usuario(String texturaPath) {
        VITALIDADE = 20;
        HP_MAXIMO = VITALIDADE * 2;
        HP = HP_MAXIMO;
        FORCA = 5;
        INTELIGENCIA = 5;
        textura = new Texture(texturaPath);
}

    public void draw(Batch batch) {
        batch.draw(textura, x, y, w, h);
    }
    // funcao que aplica dano ao usuario
    public void TOMA_DANO(int dano) {
        HP -= dano;
        if (HP < 0) HP = 0;
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


    public void dispose() {
        if (textura != null) textura.dispose();
    }
}
