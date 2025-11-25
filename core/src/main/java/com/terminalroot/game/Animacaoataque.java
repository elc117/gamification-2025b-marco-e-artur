package com.terminalroot.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animacaoataque {
    private Animation<TextureRegion> animacao;
    private Texture textura;
    private float stateTime;
    private float x, y, w, h;
    private boolean ativo;

    public Animacaoataque(String caminho, int cols, int rows, float tempo) {
        this.textura = new Texture(Gdx.files.internal(caminho));
        this.animacao = criarAnimacaoEfeito(this.textura, cols, rows, tempo);
        this.stateTime = 0f;
        this.ativo = false;
    }

    public static Animation<TextureRegion> criarAnimacaoEfeito(Texture tex, int cols, int rows, float tempo) {

        int frameWidth = tex.getWidth() / cols;
        int frameHeight = tex.getHeight() / rows;

        TextureRegion[][] tmp = TextureRegion.split(tex, frameWidth, frameHeight);

        TextureRegion[] frames = new TextureRegion[cols * rows];
        int index = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                frames[index++] = tmp[r][c];
            }
        }

        Animation<TextureRegion> anim = new Animation<>(tempo, frames);
        anim.setPlayMode(Animation.PlayMode.NORMAL);

        return anim;
    }

    public void iniciar(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.ativo = true;
        this.stateTime = 0f;
    }

    public void update(float delta) {
        if (ativo) {
            stateTime += delta;
            if (animacao.isAnimationFinished(stateTime))
                ativo = false;
        }
    }

    public void draw(Batch batch) {
        if (ativo && animacao != null) {
            
            batch.enableBlending();
            batch.setBlendFunction(com.badlogic.gdx.graphics.GL20.GL_SRC_ALPHA,
                    com.badlogic.gdx.graphics.GL20.GL_ONE_MINUS_SRC_ALPHA);

            TextureRegion frame = animacao.getKeyFrame(stateTime);
            batch.draw(frame, x, y, w, h);
        }
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void dispose() {
        if (textura != null)
            textura.dispose();
    }
}
