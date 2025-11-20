package com.terminalroot.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class MenuInterfaceBatalha implements Screen {
    final Main game;
    final Controle_Diagrama_Estados controle;

    public MenuInterfaceBatalha(final Main game, Controle_Diagrama_Estados controle){
        this.game = game;
        this.controle = controle;
    }

    public void show(){
    }

    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.font.draw(game.batch, "Tela de Batalha!", 100, 100);
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
