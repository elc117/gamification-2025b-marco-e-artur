package com.terminalroot.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;

// atributos basicos do usuario
public class Usuario {
    private int HP;
    private int HP_MAXIMO;
    private int FORCA;
    private int INTELIGENCIA;
    private int VITALIDADE = 20;
    private int STAMINA;
    private final int STAMINA_MAXIMA = 100;

    private Animation<TextureRegion> animIdle, animAtaque, animCast;
    private Texture texIdle, texAtaque, texCast;

    public enum Estado {
        IDLE, ATAQUE, CAST
    }

    private Estado estadoAtual;
    private float stateTime;

    private float x, y, w, h;

    public Usuario(String pastaBase, int[] colunas, int[] linhas) { // construtor normalzinho
        this.VITALIDADE = 20;
        this.HP_MAXIMO = VITALIDADE * 2;
        this.HP = HP_MAXIMO;
        this.FORCA = Main.forca;
        this.INTELIGENCIA = Main.inteligencia;
        this.STAMINA = STAMINA_MAXIMA;
        this.estadoAtual = Estado.IDLE;
        this.stateTime = 0f;

        this.animIdle = criarAnimacao(pastaBase + "/idle.png", colunas[0], linhas[0], 0.15f, true, 21 );
        this.animAtaque = criarAnimacao(pastaBase + "/ataque.png", colunas[1], linhas[1], 0.10f, false,29);
        this.animCast = criarAnimacao(pastaBase + "/cast.png", colunas[2], linhas[2], 0.12f, false,21);
    }

    private Animation<TextureRegion> criarAnimacao(String caminho, int colunas, int linhas, float tempo, boolean loop, int totalFrames) {
        // protecao de linhas e colunas
        if (colunas <= 0)
            colunas = 1;
        if (linhas <= 0)
            linhas = 1;

        Texture sheet = new Texture(Gdx.files.internal(caminho));  // carrega o sheet

        if (caminho.contains("idle")) // se for idle vai para o texIdle e assim vai
            texIdle = sheet;
        else if (caminho.contains("ataque"))
            texAtaque = sheet;
        else if (caminho.contains("cast"))
            texCast = sheet;

        TextureRegion[][] tmp = TextureRegion.split(
                sheet,
                sheet.getWidth() / colunas,
                sheet.getHeight() / linhas); // divide a imagem pegando o tamanho da sprite sheet e dividindo pelo numero de colunas e linhas

        // cria array com todos os frames 
        TextureRegion[] frames = new TextureRegion[totalFrames]; // aqui estava dando erro pq algumas animações não tinham todos os frames, sem saber oq fazer perguntei ao chatgpt
        // percebi que o que estava acontecendo era que ele estava tentando acessar indices invalidos do array, a solução foi criar uma condição para parar de preencher quando o indice fosse igual ou maior que o total de frames
        int index = 0; // indice para preencher o array
        outerLoop:
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if (index >= totalFrames) {
                break outerLoop; // para quando atingir o total de frames
            }
                frames[index++] = tmp[i][j]; // preenche o array com os frames
            }
        }

        Animation<TextureRegion> anim = new Animation<>(tempo, frames); // cria a animação
        anim.setPlayMode(loop ? Animation.PlayMode.LOOP : Animation.PlayMode.NORMAL); // define o modo dele, se vai ser loop ou não, iddle no caso é loop e ataque e cast não
        return anim;
    }

    public void update(float delta) {
        stateTime += delta; // acumula o tempo de animação

        if (estadoAtual == Estado.ATAQUE && animAtaque != null && animAtaque.isAnimationFinished(stateTime)) {
            setEstado(Estado.IDLE); // volta para idle depois de atacar
        }
        if (estadoAtual == Estado.CAST && animCast != null && animCast.isAnimationFinished(stateTime)) {
            setEstado(Estado.IDLE); // mesma coisa só que pra quando castar
        }
    }

    public void draw(Batch batch) {
        Animation<TextureRegion> animacaoParaTocar = animIdle;

        // escolhe a animação com base no estado
        switch (estadoAtual) {
            case IDLE:
                animacaoParaTocar = animIdle;
                break;
            case ATAQUE:
                animacaoParaTocar = animAtaque;
                break;
            case CAST:
                animacaoParaTocar = animCast;
                break;
        }

        if (animacaoParaTocar != null) { // desenha a animação
            TextureRegion frame = animacaoParaTocar.getKeyFrame(stateTime);
            batch.draw(frame, x, y, w, h); 
        }
    }

    public void setEstado(Estado novo) { // muda o estado do usuario
        if (this.estadoAtual != novo) {
            this.estadoAtual = novo;
            this.stateTime = 0f; // reseta o tempo de animação
        }
    }

    public boolean animacaoAtaqueTerminou() { // verifica se a animação de ataque terminou
        boolean terminou = false;
        if (estadoAtual == Estado.ATAQUE && animAtaque != null) { // se estiver atacando e a animação não for nula
            terminou = animAtaque.isAnimationFinished(stateTime); // verifica pelo statetime
        } else {
            terminou = true; // se não estiver atacando, considera que terminou
        }
        return terminou; // retorna se terminou ou não
    }

    public boolean animacaoCastTerminou() { // verifica se a animação de cast terminou mesma logica do ataque
        boolean terminou = false;
        if (estadoAtual == Estado.CAST && animCast != null) {
            terminou = animCast.isAnimationFinished(stateTime);
        } else {
            terminou = true;
        }
        return terminou;
    }

    // para ataques corpo a corpo
    public void atacar() {
        if (HP > 0) {
            setEstado(Estado.ATAQUE);
        }
    }

    // para magias/cast
    public void castar() {
        if (HP > 0){
            setEstado(Estado.CAST);
        }
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

    // getters e setters

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
        if (this.STAMINA < 0) {
            this.STAMINA = 0;
        }
    }

    public void recuperaStamina() {
        this.STAMINA += 15;
        if (this.STAMINA > STAMINA_MAXIMA) {
            this.STAMINA = STAMINA_MAXIMA;
        }
    }

    public void dispose() {
        if (texIdle != null)
            texIdle.dispose();
        if (texAtaque != null)
            texAtaque.dispose();
        if (texCast != null)
            texCast.dispose();
    }

}
