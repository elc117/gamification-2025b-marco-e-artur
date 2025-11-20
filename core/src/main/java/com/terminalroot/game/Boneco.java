package com.terminalroot.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Boneco extends Sprite implements InputProcessor {
    private boolean cima;
    private boolean baixo;
    private boolean esquerda;
    private boolean direita;

    private BonecoCaminhando animation;
    private String direcao = "baixo";
    private float velocidade = 0.01f;

    // Teste
    private CriaConstrucao criaConstrucao;
    private ChamaColisao chamaColisao;

    // Teste Controle
    final Controle_Diagrama_Estados controle;
    private boolean TesteErro = false;

    public Boneco(Sprite sprite, CriaConstrucao criaConstrucao, Controle_Diagrama_Estados controle) {
        super(sprite);
        setSize(getWidth(), getHeight());
        animation = new BonecoCaminhando();
        this.criaConstrucao = criaConstrucao;
        this.controle = controle;
        chamaColisao = new ChamaColisao(criaConstrucao.insereObjArray);
    }
    // adicionar função pra pegar o retangulo do boneco pra usar nas colisoes
    // PRECISA ALTERAR ISSO !!! TÁ MUITO GRANDE O QUADRADO // alteração efetuada, testar depois
    public Rectangle limitesRetangulo(){
        float margemX = getWidth() * 0.25f;
        float margemY = getHeight() * 0.25f;

        float largura  = getWidth() - 2 * margemX;
        float altura = getHeight() - 2 * margemY;

        return new Rectangle(getX() + margemX, getY() + margemY, largura, altura);
    }

    public void draw(Batch spriteBatch) {
        update();
        spriteBatch.enableBlending();
        animation.draw(spriteBatch, getX(), getY(), getWidth(), getHeight());
    }

    public void update() {
        if(TesteErro) return;

        boolean movimento = false;

        float posicaoanteriorX = getX();
        float posicaoanteriorY = getY();

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

        boolean colidiuRetangulo = false;
        boolean colidiuElipse = false;
        colidiuRetangulo = chamaColisao.ChamaColisaoRetangulo(limitesRetangulo());
        colidiuElipse = chamaColisao.ChamaColisaoEllipse(limitesRetangulo());

        if(colidiuRetangulo || colidiuElipse){
            this.setX(posicaoanteriorX);
            this.setY(posicaoanteriorY);
        }

        ObstaculoCirculo obstaculoColidido = chamaColisao.ChamaColisaoCirculo(limitesRetangulo());

        if(obstaculoColidido != null && !TesteErro){
            TesteErro = true;
            Controle_Diagrama_Estados.State estadoDestino = Controle_Diagrama_Estados.State.valueOf(obstaculoColidido.nome);
            controle.Trocar_estado(estadoDestino);
        }

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
