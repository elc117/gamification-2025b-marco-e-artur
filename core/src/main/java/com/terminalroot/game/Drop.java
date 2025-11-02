package com.terminalroot.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;

public class Drop extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public FillViewport viewport;
    public Controle_Diagrama_Estados controle;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        viewport = new FillViewport(8, 5);
        controle = new Controle_Diagrama_Estados(this);

        font.setUseIntegerPositions(false);
        font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());
    }

    @Override
    public void render() {
        super.render(); //  atualiza e desenha a tela atual
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
