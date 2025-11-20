package com.terminalroot.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

import static com.terminalroot.game.MainMenuScreen.SkinBoneco;

public class Mochila implements Screen {
    final Main game;
    final Controle_Diagrama_Estados controle;
    Texture background;
    Texture boneco;

    public Mochila(final Main game, Controle_Diagrama_Estados controle){
        this.game = game;
        this.controle = controle;
    }

    public void show(){
        background = new Texture("Inventario/backgroundinventario.png");

        switch (SkinBoneco){
            case "SkinBasica":
                boneco = new Texture("hero/Eni/baixo.png");
                break;
            case "SkinSolomonk":
                boneco = new Texture("hero/EniSkin1/Semfundo.png");
                break;
            case "SkinLast":
                boneco = new Texture("hero/EniSkin2/SemFundo.png");
                break;
        }
    }

    public void render(float delta){
        ScreenUtils.clear(Color.DARK_GRAY);
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(background,0,0,game.viewport.getWorldWidth(),game.viewport.getWorldHeight());
        game.batch.draw(boneco, 2, 1, game.viewport.getWorldWidth() / 2, game.viewport.getWorldHeight() / 2);
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
    public void dispose() {}
}
