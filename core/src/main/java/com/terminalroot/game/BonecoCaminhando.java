package com.terminalroot.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.w3c.dom.Text;

import static com.terminalroot.game.Main.SkinBoneco;

public class BonecoCaminhando {
    private Animation<TextureRegion> AnimacaoCaminhaCima;
    private Animation<TextureRegion> AnimacaoCaminhaBaixo;
    private Animation<TextureRegion> AnimacaoCaminhaEsquerda;
    private Animation<TextureRegion> AnimacaoCaminhaDireita;
    private Animation<TextureRegion> AnimacaoAtual;
    private float stateTime;
    private TextureRegion currentFrame;
    private String direcao;

    public BonecoCaminhando(){
        initAnimations();
        stateTime = 0f;
        direcao = "baixo";
    }

    private void initAnimations() {
        int frameCols = 4;
        int frameRows = 8;
        int frameTeste = 7;

        switch (SkinBoneco){

        }
        Texture spreedshetCaminhadaBaixo = new Texture("hero/Eni/baixo/InteiroBaixo.png");
        Texture spreedshetCaminhadaCima = new Texture("hero/Eni/cima/InteiroCima.png");
        Texture spreedshetCaminhadaEsquerda = new Texture("hero/Eni/esquerda/InteiroEsquerda.png");
        Texture spreedshetCaminhadaDireita = new Texture("hero/Eni/direita/InteiroDireita.png");

        int frameWidth = spreedshetCaminhadaBaixo.getWidth() / frameCols;
        int frameHeight = spreedshetCaminhadaBaixo.getHeight() / frameRows;

        TextureRegion[][] MovimentoCima = TextureRegion.split(spreedshetCaminhadaCima, frameWidth, frameHeight);
        TextureRegion[][] MovimentoBaixo = TextureRegion.split(spreedshetCaminhadaBaixo, frameWidth, frameHeight);
        TextureRegion[][] MovimentoEsquerda = TextureRegion.split(spreedshetCaminhadaEsquerda,frameWidth, frameHeight);
        TextureRegion[][] MovimentoDireita = TextureRegion.split(spreedshetCaminhadaDireita, frameWidth, frameHeight);

        // ainda tem algo de errado, mas eu vejo outra hora
        int Quant = frameCols * frameTeste;

        TextureRegion[] DirBaixo = new TextureRegion[Quant];
        TextureRegion[] DirEsq = new TextureRegion[Quant];
        TextureRegion[] DirDireita = new TextureRegion[Quant];
        TextureRegion[] DirCima = new TextureRegion[Quant];

        int index = 0;
        for (int i = 0; i < frameTeste; i++) {
            for (int j = 0; j < frameCols; j++) {
                DirCima[index] = MovimentoCima[i][j];
                DirBaixo[index] = MovimentoBaixo[i][j];
                DirEsq[index] = MovimentoEsquerda[i][j];
                DirDireita[index] = MovimentoDireita[i][j];
                index++;
            }
        }

        AnimacaoCaminhaCima = new Animation<>(0.10f,DirCima);
        AnimacaoCaminhaBaixo = new Animation<>(0.10f, DirBaixo);
        AnimacaoCaminhaEsquerda = new Animation<>(0.10f, DirEsq);
        AnimacaoCaminhaDireita = new Animation<>(0.10f, DirDireita);

        AnimacaoCaminhaCima.setPlayMode(Animation.PlayMode.LOOP);
        AnimacaoCaminhaBaixo.setPlayMode(Animation.PlayMode.LOOP);
        AnimacaoCaminhaEsquerda.setPlayMode(Animation.PlayMode.LOOP);
        AnimacaoCaminhaDireita.setPlayMode(Animation.PlayMode.LOOP);

        AnimacaoAtual = AnimacaoCaminhaBaixo;
        currentFrame = AnimacaoAtual.getKeyFrame(0);
    }

    public void update(float deltaTime, String direction, boolean movimento){
        if(!direction.equals(direcao)){
            direcao = direction;
            stateTime = 0;
            switch (direction){
                case "cima":
                    AnimacaoAtual = AnimacaoCaminhaCima;
                    break;
                case "baixo":
                    AnimacaoAtual = AnimacaoCaminhaBaixo;
                    break;
                case "esquerda":
                    AnimacaoAtual = AnimacaoCaminhaEsquerda;
                    break;
                case "direita":
                    AnimacaoAtual = AnimacaoCaminhaDireita;
                    break;
            }
        }

        if(movimento){
            stateTime += deltaTime;
            currentFrame = AnimacaoAtual.getKeyFrame(stateTime);
        } else{
            stateTime = 0;
            currentFrame = AnimacaoAtual.getKeyFrame(0);
        }
    }

    public void draw(Batch batch, float x, float y, float width, float height){
        batch.draw(currentFrame, x, y, width, height);
    }
}
