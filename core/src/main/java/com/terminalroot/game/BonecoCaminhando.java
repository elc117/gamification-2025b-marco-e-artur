package com.terminalroot.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.w3c.dom.Text;

public class BonecoCaminhando {
    private boolean cima;
    private boolean baixo;
    private boolean esquerda;
    private boolean direta;


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
        Texture spreedshetCaminhadaBaixo = new Texture("hero/Eni/baixo/RecortadaBaixoCerto.png");
        int frameColsBaixo = 4;
        int frameRowsBaixo = 4;

        int frameWidthBaixo = spreedshetCaminhadaBaixo.getWidth() / frameColsBaixo;
        int frameHeightBaixo = spreedshetCaminhadaBaixo.getHeight() / frameRowsBaixo;

        Texture spreedshetCaminhadaCima = new Texture("hero/Eni/cima/RecortadaCimaCerto.png");
        int frameColsCima = 4;
        int frameRowsCima = 4;

        int frameWidthCima = spreedshetCaminhadaCima.getWidth() / frameColsCima;
        int frameHeightCima = spreedshetCaminhadaCima.getHeight() / frameRowsCima;

        Texture spreedshetCaminhadaEsquerda = new Texture("hero/Eni/esquerda/RecortadaEsquerdaCerto.png");
        int frameColsEsquerda = 4;
        int frameRowsEsquerda = 4;

        int frameWidthEsquerda = spreedshetCaminhadaEsquerda.getWidth() / frameColsEsquerda;
        int frameHeightEsquerda = spreedshetCaminhadaEsquerda.getHeight() / frameRowsEsquerda;

        Texture spreedshetCaminhadaDireita = new Texture("hero/Eni/direita/RecortadaDireitaCerto.png");
        int frameColsDireita= 4;
        int frameRowsDireita = 4;

        int frameWidthDireita = spreedshetCaminhadaDireita.getWidth() / frameColsDireita;
        int frameHeightDireita = spreedshetCaminhadaDireita.getHeight() / frameRowsDireita;

        TextureRegion[][] MovimentoCima = TextureRegion.split(spreedshetCaminhadaCima, frameWidthCima, frameHeightCima);
        TextureRegion[][] MovimentoBaixo = TextureRegion.split(spreedshetCaminhadaBaixo, frameWidthBaixo, frameHeightBaixo);
        TextureRegion[][] MovimentoEsquerda = TextureRegion.split(spreedshetCaminhadaEsquerda,frameWidthEsquerda, frameHeightEsquerda);
        TextureRegion[][] MovimentoDireita = TextureRegion.split(spreedshetCaminhadaDireita, frameWidthDireita, frameHeightDireita);

        TextureRegion[] DirBaixo = new TextureRegion[16];
        TextureRegion[] DirEsq = new TextureRegion[16];
        TextureRegion[] DirDireita = new TextureRegion[16];
        TextureRegion[] DirCima = new TextureRegion[16];

        int index = 0;
        for (int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++){
                DirCima[index] = MovimentoCima[i][j];
                index++;
            }
        }

        int index1 = 0;
        for (int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++){
                DirBaixo[index1] = MovimentoBaixo[i][j];
                index1++;
            }
        }

        int index2 = 0;
        for (int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++){
                DirEsq[index2] = MovimentoEsquerda[i][j];
                index2++;
            }
        }

        int index3 = 0;
        for (int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++){
                DirDireita[index3] = MovimentoDireita[i][j];
                index3++;
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
