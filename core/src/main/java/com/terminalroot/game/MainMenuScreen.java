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
    private SpriteBatch batch;
    Texture tela_principal;

    // Implementação do personagem, dá pra trocar isso depois
    private Boneco boneco;

    public MainMenuScreen(final Drop game, Controle_Diagrama_Estados controle) {
        this.game = game;
        this.controle = controle;
        this.batch = new SpriteBatch();
    }

    @Override
    public void show() {
        tela_principal = new Texture("mapafinal.png");
        Texture bonecoTexture = new Texture("bucket.png");
        Sprite bonecoSprite = new Sprite(bonecoTexture);

        boneco = new Boneco(bonecoSprite);

        boneco.setPosition(Gdx.graphics.getWidth() / 2f,Gdx.graphics.getHeight() /2f);
        boneco.setSize(64,64);
        Gdx.input.setInputProcessor(boneco);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.DARK_GRAY);

        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        batch.begin();
        batch.draw(tela_principal, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        boneco.draw(batch);
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
    public void dispose() {
        tela_principal.dispose();
    }
}
