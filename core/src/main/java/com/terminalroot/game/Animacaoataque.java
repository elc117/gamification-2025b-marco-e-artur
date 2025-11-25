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

    public Animacaoataque(String caminho, int colunas, float tempoPorFrame) {
        textura = new Texture(Gdx.files.internal(caminho));
        TextureRegion[][] tmp = TextureRegion.split(textura, textura.getWidth() / colunas, textura.getHeight());
        TextureRegion[] frames = new TextureRegion[colunas];
        for (int i = 0; i < colunas; i++) {
            frames[i] = tmp[0][i];
        }
        animacao = new Animation<>(tempoPorFrame, frames);
        animacao.setPlayMode(Animation.PlayMode.NORMAL);
        ativo = false;
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
