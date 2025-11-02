package com.terminalroot.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.terminalroot.game.Boneco;
import org.w3c.dom.Text;
public class MainMenuScreen implements Screen {

    final Drop game;
    final Controle_Diagrama_Estados controle;
    Texture tela_principal;

    // Implementação do personagem, dá pra trocar isso depois
    private Boneco boneco;

    public MainMenuScreen(final Drop game, Controle_Diagrama_Estados controle) {
        this.game = game;
        this.controle = controle;
    }

    @Override
    public void show() {
        tela_principal = new Texture("mapafinal.png");
        Texture bonecoTexture = new Texture("bucket.png");
        Sprite bonecoSprite = new Sprite(bonecoTexture);

        boneco = new Boneco(bonecoSprite);

        boneco.setPosition(game.viewport.getWorldWidth()/2f, game.viewport.getWorldHeight()/2f);
        boneco.setSize(0.5f,0.5f);
        Gdx.input.setInputProcessor(boneco);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.DARK_GRAY);

        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(tela_principal,0,0,game.viewport.getWorldWidth(),game.viewport.getWorldHeight());
        boneco.draw(game.batch);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {
        tela_principal.dispose();
    }
}
