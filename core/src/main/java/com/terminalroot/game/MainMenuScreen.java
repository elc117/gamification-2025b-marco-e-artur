package com.terminalroot.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import org.w3c.dom.Text;
import com.badlogic.gdx.graphics.

public class MainMenuScreen implements Screen {

    final Drop game;
    final Controle_Diagrama_Estados controle;
    private SpriteBatch batch;
    Texture tela_principal;

    // Implementação do personagem, dá pra trocar isso depois


    public MainMenuScreen(final Drop game, Controle_Diagrama_Estados controle) {
        this.game = game;
        this.controle = controle;
        this.batch = new SpriteBatch();
    }

    @Override
    public void show() {
        tela_principal = new Texture("mapafinal.png");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.DARK_GRAY);

        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        batch.begin();
        batch.draw(tela_principal, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
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
    public void dispose() {}
}
