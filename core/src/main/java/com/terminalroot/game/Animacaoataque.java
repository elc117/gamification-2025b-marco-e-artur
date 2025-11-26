package com.terminalroot.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animacaoataque {
    private Animation<TextureRegion> animacao; // animação de ataque
    private Texture textura; // textura da animação
    private float stateTime; // tempo de estado
    private float x, y, w, h; // cordenadas e dimensoes 
    private boolean ativo; // flag para saber se ta ativo ou não

    public Animacaoataque(String caminho, int cols, int rows, float tempo) { // construtor da classe de animação de ataque
        this.textura = new Texture(Gdx.files.internal(caminho)); // carrega a texxtura
        this.animacao = criarAnimacaoEfeito(this.textura, cols, rows, tempo); // cria a animação
        this.stateTime = 0f; // seta o tempo inicial
        this.ativo = false; // seta como inativo
    }

    public static Animation<TextureRegion> criarAnimacaoEfeito(Texture tex, int colunas, int linhas, float tempo) {

        int frameWidth = tex.getWidth() / linhas; // calcula o tamanho de cada frame do sprite sheet
        int frameHeight = tex.getHeight() / colunas; // calcula a altura de cada frame do sprite sheet

        TextureRegion[][] tmp = TextureRegion.split(tex, frameWidth, frameHeight); // divide a textura em uma matriz de texturas

        TextureRegion[] frames = new TextureRegion[linhas * colunas]; // cria um array para armazenar os frames
        int index = 0;

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                frames[index++] = tmp[i][j]; // adiciona cada frame ao array
            }
        }

        Animation<TextureRegion> anim = new Animation<>(tempo, frames); //
        anim.setPlayMode(Animation.PlayMode.NORMAL); // define o modo de reprodução da animação

        return anim;
    }

    public void iniciar(float x, float y, float w, float h) { // inicia a animação de ataque
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.ativo = true;
        this.stateTime = 0f;
    }

    public void update(float delta) { // atualiza a animacao de acordo com o delta time
        if (ativo) {
            stateTime += delta;
            if (animacao.isAnimationFinished(stateTime))
                ativo = false;
        }
    }

    public void draw(Batch batch) { // desenha a animacao
        if (ativo && animacao != null) {
            
            batch.enableBlending(); // isso é pra deixar o fundo transparente
            batch.setBlendFunction(com.badlogic.gdx.graphics.GL20.GL_SRC_ALPHA, // outro pra deixar o fundo transparente
                    com.badlogic.gdx.graphics.GL20.GL_ONE_MINUS_SRC_ALPHA);

            TextureRegion frame = animacao.getKeyFrame(stateTime); // pega o frame atual da animação
            batch.draw(frame, x, y, w, h); // desenha o frame na tela
        }
    }

    public boolean isAtivo() {
        return ativo; // retorna se a animação está ativa
    }

    public void dispose() {
        if (textura != null)
            textura.dispose();
    }
}
