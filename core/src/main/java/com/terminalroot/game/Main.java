package com.terminalroot.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;

public class Main extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public FillViewport viewport;
    public Controle_Diagrama_Estados controle;

    // Skin do boneco, variavel global
    public static String SkinBoneco = "SkinSolomonk";

    public Usuario jogador;

    // true = anjos, false = demonios
    public boolean eanjo = true;

    // controle fase
    public int fase_atual = 1;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        viewport = new FillViewport(8, 5);
        controle = new Controle_Diagrama_Estados(this);


        font.setUseIntegerPositions(false);
        font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());
    }

    public void avancafase(){
        fase_atual++;
    }

    public int getFaseAtual(){
        return fase_atual;
    }

    public boolean isEAnjo(){
        return eanjo;
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
