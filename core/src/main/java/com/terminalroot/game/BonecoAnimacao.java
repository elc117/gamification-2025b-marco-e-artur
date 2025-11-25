package com.terminalroot.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.terminalroot.game.Main.SkinBoneco;

public class BonecoAnimacao {
    private Animation<TextureRegion> AnimacaoCaminhaCima;
    private Animation<TextureRegion> AnimacaoCaminhaBaixo;
    private Animation<TextureRegion> AnimacaoCaminhaEsquerda;
    private Animation<TextureRegion> AnimacaoCaminhaDireita;
    private Animation<TextureRegion> AnimacaoAtual;
    private float stateTime;
    private TextureRegion currentFrame;
    private String direcao;

    // Mudança de Skins -> Implementação
    Texture spreedshetCaminhadaBaixo;
    Texture spreedshetCaminhadaCima;
    Texture spreedshetCaminhadaEsquerda;
    Texture spreedshetCaminhadaDireita;

    // Implementaçao modo treino combate
    private Animation<TextureRegion> AnimacaoTreinoForca;
    Texture spreedsheetTreinoForca;

    public BonecoAnimacao(){
        initAnimations();
        stateTime = 0f;
        direcao = "baixo";
    }

    private void initAnimations() {
        int frameCols = 4;
        int frameRows = 8;
        int frameTeste = 7;

        switch (SkinBoneco){
            case "SkinBasica":
                spreedshetCaminhadaBaixo = new Texture("hero/Eni/baixo/InteiroBaixo.png");
                spreedshetCaminhadaCima = new Texture("hero/Eni/cima/InteiroCima.png");
                spreedshetCaminhadaEsquerda = new Texture("hero/Eni/esquerda/InteiroEsquerda.png");
                spreedshetCaminhadaDireita = new Texture("hero/Eni/direita/InteiroDireita.png");
                spreedsheetTreinoForca = new Texture("hero/Eni/Treinoforcaeni.png");
                break;
            case "SkinSolomonk":
                spreedshetCaminhadaBaixo = new Texture("hero/EniSkin1/baixo/HeroBaixo.png");
                spreedshetCaminhadaCima = new Texture("hero/EniSkin1/cima/Herocima.png");
                spreedshetCaminhadaEsquerda = new Texture("hero/EniSkin1/esquerda/heroesquerda.png");
                spreedshetCaminhadaDireita = new Texture("hero/EniSkin1/direita/herodireita.png");
                spreedsheetTreinoForca = new Texture("hero/EniSkin1/treinoforcaeni1.png");
                break;
            case "SkinLast":
                spreedshetCaminhadaBaixo = new Texture("hero/EniSkin2/baixo/HeroBaixo.png");
                spreedshetCaminhadaCima = new Texture("hero/EniSkin2/cima/Herocima.png");
                spreedshetCaminhadaEsquerda = new Texture("hero/EniSkin2/esquerda/heroesquerda.png");
                spreedshetCaminhadaDireita = new Texture("hero/EniSkin2/direita/herodireita.png");
                spreedsheetTreinoForca = new Texture("hero/EniSkin2/treinoforcaeni2.png");
                break;
        }

        int frameWidth = spreedshetCaminhadaBaixo.getWidth() / frameCols;
        int frameHeight = spreedshetCaminhadaBaixo.getHeight() / frameRows;

        TextureRegion[][] MovimentoCima = TextureRegion.split(spreedshetCaminhadaCima, frameWidth, frameHeight);
        TextureRegion[][] MovimentoBaixo = TextureRegion.split(spreedshetCaminhadaBaixo, frameWidth, frameHeight);
        TextureRegion[][] MovimentoEsquerda = TextureRegion.split(spreedshetCaminhadaEsquerda,frameWidth, frameHeight);
        TextureRegion[][] MovimentoDireita = TextureRegion.split(spreedshetCaminhadaDireita, frameWidth, frameHeight);
        TextureRegion[][] Treinoforca = TextureRegion.split(spreedsheetTreinoForca, frameWidth, frameHeight);

        // ainda tem algo de errado, mas eu vejo outra hora
        int Quant = frameCols * frameTeste;

        TextureRegion[] DirBaixo = new TextureRegion[Quant];
        TextureRegion[] DirEsq = new TextureRegion[Quant];
        TextureRegion[] DirDireita = new TextureRegion[Quant];
        TextureRegion[] DirCima = new TextureRegion[Quant];
        TextureRegion[] Luta = new TextureRegion[Quant];

        int index = 0;
        for (int i = 0; i < frameTeste; i++) {
            for (int j = 0; j < frameCols; j++) {
                DirCima[index] = MovimentoCima[i][j];
                DirBaixo[index] = MovimentoBaixo[i][j];
                DirEsq[index] = MovimentoEsquerda[i][j];
                DirDireita[index] = MovimentoDireita[i][j];
                Luta[index] = Treinoforca[i][j];
                index++;
            }
        }

        AnimacaoCaminhaCima = new Animation<>(0.10f,DirCima);
        AnimacaoCaminhaBaixo = new Animation<>(0.10f, DirBaixo);
        AnimacaoCaminhaEsquerda = new Animation<>(0.10f, DirEsq);
        AnimacaoCaminhaDireita = new Animation<>(0.10f, DirDireita);
        AnimacaoTreinoForca = new Animation<>(0.10f,Luta);

        AnimacaoCaminhaCima.setPlayMode(Animation.PlayMode.LOOP);
        AnimacaoCaminhaBaixo.setPlayMode(Animation.PlayMode.LOOP);
        AnimacaoCaminhaEsquerda.setPlayMode(Animation.PlayMode.LOOP);
        AnimacaoCaminhaDireita.setPlayMode(Animation.PlayMode.LOOP);
        AnimacaoTreinoForca.setPlayMode(Animation.PlayMode.LOOP);

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
                case "luta":
                    AnimacaoAtual = AnimacaoTreinoForca;
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
