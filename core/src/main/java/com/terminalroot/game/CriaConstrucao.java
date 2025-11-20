package com.terminalroot.game;

import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;


import java.util.ArrayList;

public class CriaConstrucao {
    public InsereObjArray insereObjArray;
    private Colisoes colisoes;
    final Main game;
    private ArrayList<Rectangle> Objretangular;
    private ArrayList<ObstaculoCirculo> ObjCircular;
    private ArrayList<Ellipse> ObjElipse;

    public CriaConstrucao(final Main game, ArrayList<Rectangle> Objretangular, ArrayList<ObstaculoCirculo> ObjCircular, ArrayList<Ellipse>ObjElipse) {
        insereObjArray = new InsereObjArray(Objretangular, ObjCircular, ObjElipse);
        colisoes = new Colisoes();
        this.game = game;
        this.Objretangular = Objretangular;
        this.ObjCircular = ObjCircular;
        this.ObjElipse = ObjElipse;
    }

    public void adicionarRetangulo(float x, float y, float largura, float altura) {
        insereObjArray.InserirObjretangular(x, y, largura, altura);
    }

    public void adicionarElipse(float x, float y, float largura, float altura){
        insereObjArray.InserirObjElipse(x,y,largura,altura);
    }

    public void CriarPrediosTelaInicial(float larguraimagem, float alturaimagem, float posicaoHorizontal,
                                        float posicaoVertical, float larguraObstaculo,
                                        float alturaObstaculo, ArrayList<Rectangle> objetos){
        float larguramundo = game.viewport.getWorldWidth();
        float alturamundo = game.viewport.getWorldHeight();

        float escalaX = larguramundo / larguraimagem;
        float escalaY = alturamundo / alturaimagem;

        float conversao = alturaimagem - posicaoVertical - alturaObstaculo;

        float posicaoX = posicaoHorizontal * escalaX;
        float posicaoY = conversao * escalaY;
        float largura = larguraObstaculo * escalaX;
        float altura = alturaObstaculo * escalaY;

        adicionarRetangulo(posicaoX, posicaoY, largura, altura);
        objetos.add(new Rectangle(posicaoX, posicaoY, largura, altura));
    }

    public void CriarCirculos(float larguraimagem, float alturaimagem, float posicaoHorizontal, float posicaoVertical,
                              float raio, String lugar){
        float larguramundo = game.viewport.getWorldWidth();
        float alturamundo = game.viewport.getWorldHeight();

        float escalaX = larguramundo / larguraimagem;
        float escalaY = alturamundo / alturaimagem;

        float centroX_img = posicaoHorizontal + raio;
        float centroY_img = posicaoVertical + raio;

        float conversao = alturaimagem - centroY_img;

        float centroX = centroX_img * escalaX;
        float centroY = conversao * escalaY;

        float escala = Math.min(escalaX, escalaY);
        float raioCescala = raio * escala;

        ObstaculoCirculo obstaculo = new ObstaculoCirculo(lugar, centroX, centroY, raioCescala);
        insereObjArray.InserirObjcircular(obstaculo);
    }

    public void CriarElipse(float larguraimagem, float alturaimagem, float posicaoHorizontal, float posicaoVertical,
                            float raioX, float raioY, ArrayList<Ellipse> objetos){
        float larguramundo = game.viewport.getWorldWidth();
        float alturamundo = game.viewport.getWorldHeight();

        float escalaX = larguramundo / larguraimagem;
        float escalaY = alturamundo / alturaimagem;

        float centroX_img = posicaoHorizontal + raioX;
        float centroY_img = posicaoVertical + raioY;

        float conversao = alturaimagem - centroY_img;

        float centroX = centroX_img * escalaX;
        float centroY = conversao * escalaY;

        float raioXescala = raioX * escalaX;
        float raioYescala = raioY * escalaY;

        adicionarElipse(centroX, centroY, raioXescala * 2, raioYescala * 2);
        objetos.add(new Ellipse(centroX, centroY, raioXescala * 2, raioYescala * 2));
    }
}
