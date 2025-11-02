package com.terminalroot.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.terminalroot.game.BonecoCaminhando;

public class Boneco extends Sprite implements InputProcessor {
    private boolean cima;
    private boolean baixo;
    private boolean esquerda;
    private boolean direita;

    private BonecoCaminhando animation;
    private String direcao = "baixo";
    private float velocidade = 0.01f;

    public Boneco(Sprite sprite) {
        super(sprite);
        setSize(getWidth(), getHeight());
        animation = new BonecoCaminhando();
    }

    public void draw(Batch spriteBatch) {
        update();
        spriteBatch.enableBlending();
        animation.draw(spriteBatch, getX(), getY(), getWidth(), getHeight());
    }

    public void update() {
        boolean movimento = false;

        if (cima) {
            setY(getY() + velocidade);
            direcao = "cima";
            movimento = true;
        }
        if (baixo) {
            setY(getY() - velocidade);
            direcao = "baixo";
            movimento = true;
        }
        if (direita) {
            setX(getX() + velocidade);
            direcao = "direita";
            movimento = true;
        }
        if (esquerda) {
            setX(getX() - velocidade);
            direcao = "esquerda";
            movimento = true;
        }
        animation.update(Gdx.graphics.getDeltaTime(), direcao, movimento);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                cima = true;
                break;
            case Input.Keys.A:
                esquerda = true;
                break;
            case Input.Keys.D:
                direita = true;
                break;
            case Input.Keys.S:
                baixo = true;
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int tecla) {
        switch (tecla) {
            case Input.Keys.W:
                cima = false;
                break;
            case Input.Keys.S:
                baixo = false;
                break;
            case Input.Keys.A:
                esquerda = false;
                break;
            case Input.Keys.D:
                direita = false;
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
