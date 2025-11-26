package com.terminalroot.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Monstro {

    public enum Estado { IDLE, ATAQUE, DANO, MORTE }
    private Estado estadoAtual;
    private float stateTime;

    private int HP, DANO, HP_MAXIMO;
    private String NOME;
    private float x, y, w, h;

    private Animation<TextureRegion> animIdle, animAtaque, animDano, animMorte;
    private Texture texIdle, texAtaque, texDano, texMorte;
    // construtor do monstro
    public Monstro(String nome, int hp, int dano, String pastaBase, int[] configFrames) {
        this.NOME = nome;
        this.HP_MAXIMO = hp;
        this.HP = hp;
        this.DANO = dano;
        this.estadoAtual = Estado.IDLE;
        this.stateTime = 0f;
        // cria as animações
        this.animIdle   = criarAnimacao(pastaBase + "/idle.png",   configFrames[0], 0.15f, true);
        this.animAtaque = criarAnimacao(pastaBase + "/ataque.png", configFrames[1], 0.10f, false);
        this.animDano   = criarAnimacao(pastaBase + "/hit.png",    configFrames[2], 0.10f, false);
        this.animMorte  = criarAnimacao(pastaBase + "/death.png",  configFrames[3], 0.15f, false);
    }

    private Animation<TextureRegion> criarAnimacao(String caminho, int colunas, float tempo, boolean loop) {
        // protecao de colunas
        if (colunas <= 0) colunas = 1; 

        Texture sheet = new Texture(Gdx.files.internal(caminho));
        
        // guarda a textura para descarte posterior
        if(caminho.contains("idle")) texIdle = sheet;
        else if(caminho.contains("ataque")) texAtaque = sheet;
        else if(caminho.contains("hit")) texDano = sheet;
        else if(caminho.contains("death")) texMorte = sheet;

        // divide a imagem pelo número EXATO de colunas que veio da tabela no caso cada animação tem apenas uma linha e varias colunas que tive que contar uma a uma
        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth() / colunas, sheet.getHeight()); // a unica diferença desse pro outro é que aqui os frames estão separados em uma linha só
        TextureRegion[] frames = new TextureRegion[colunas]; // fiz assim pq pensei que fazer com colunas era mais dificil, mas até que não é
        
        for (int i = 0; i < colunas; i++) {
            frames[i] = tmp[0][i]; // pega a primeira linha e todas as colunas
        }

        Animation<TextureRegion> anim = new Animation<>(tempo, frames);
        anim.setPlayMode(loop ? Animation.PlayMode.LOOP : Animation.PlayMode.NORMAL);
        return anim;
    }

    
    public void update(float delta) {
        stateTime += delta; // acumula o tempo de animação
        if (estadoAtual == Estado.ATAQUE && animAtaque != null && animAtaque.isAnimationFinished(stateTime)) {
            setEstado(Estado.IDLE); // volta para o estado de iddle dps de atacar
        }
        if (estadoAtual == Estado.DANO && animDano != null && animDano.isAnimationFinished(stateTime)) {
            setEstado(Estado.IDLE); // volta para o estado de iddle dps de receber dano
        }
    }

    public void draw(Batch batch, float x, float y, float w, float h) {
        Animation<TextureRegion> animacaoParaTocar = animIdle;

         // escolhe a animação com base no estado

        switch (estadoAtual) {
            case IDLE:   animacaoParaTocar = animIdle; break;
            case ATAQUE: animacaoParaTocar = animAtaque; break;
            case DANO:   animacaoParaTocar = animDano; break;
            case MORTE:  animacaoParaTocar = animMorte; break;
        }

        if (animacaoParaTocar != null) {
            TextureRegion frame = animacaoParaTocar.getKeyFrame(stateTime);
            batch.draw(frame, x, y, w, h); // desenha o frame
        }
    }

    public void setEstado(Estado novo) { // muda o estado do monstro 
        if (this.estadoAtual != novo) {
            this.estadoAtual = novo;
            this.stateTime = 0f; // reseta o tempo de animação
        }
    }

    public boolean animacaoAtaqueTerminou() {
    boolean terminou = false;
    // verifica a animação de ataque
    if (estadoAtual == Estado.ATAQUE && animAtaque != null) {
        terminou = animAtaque.isAnimationFinished(stateTime); // verifica pelo statetime
    } else { // se não estiver atacando, considera que terminou
        terminou = true;
    }
    
    return terminou;
}
    // funcao que aplica dano ao monstro
    public void TOMA_DANO(int dano){
        this.HP -= dano;
        if (this.HP <= 0){
            this.HP = 0;
            setEstado(Estado.MORTE);
        } else {
            setEstado(Estado.DANO);
        }
    }

    public void atacar() {
        if (HP > 0) setEstado(Estado.ATAQUE);
    }

    public boolean ESTADO(){ 
        return this.HP > 0; 
    }
    public int getDANO(){ 
        return this.DANO; 
    }
    public int getHP(){ 
        return this.HP; 
    }
    public int getHP_MAXIMO(){ 
        return this.HP_MAXIMO; 
    }
    public String getNOME(){ 
        return this.NOME; 
    }

    public void setPosicao(float x, float y) { 
        this.x = x; 
        this.y = y; 
    }
    
    public void setTamanho(float w, float h) { 
        this.w = w; 
        this.h = h; 
    }

    public float getX() { 
        return x; 
    }
    public float getY() { 
        return y; 
    }
    public float getW() {
        return w; 
    }
    public float getH() {
        return h; 
    }

    public void dispose() {
        if(texIdle != null) texIdle.dispose();
        if(texAtaque != null) texAtaque.dispose();
        if(texDano != null) texDano.dispose();
        if(texMorte != null) texMorte.dispose();
    }
}