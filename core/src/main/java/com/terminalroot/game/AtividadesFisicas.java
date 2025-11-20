package com.terminalroot.game;

import com.badlogic.gdx.Screen;

public class AtividadesFisicas implements Screen {
    final Main game;
    final Controle_Diagrama_Estados controle;

    public AtividadesFisicas(final Main game, Controle_Diagrama_Estados controle){
        this.game = game;
        this.controle = controle;
    }

    public void show(){
    }

    public void render(float delta){
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
