package com.terminalroot.game;

import com.badlogic.gdx.math.Rectangle;

public class ChamaColisao {
    private Colisoes colisoes;
    private InsereObjArray insereObjArray;

    public ChamaColisao(InsereObjArray insereObjArray){
        colisoes = new Colisoes();
        this.insereObjArray = insereObjArray;
    }

    public boolean ChamaColisaoRetangulo(Rectangle personagemRetangulo){
        return colisoes.ColisaoRetangulo(personagemRetangulo, insereObjArray.getObjRetangular());
    }

    public ObstaculoCirculo ChamaColisaoCirculo(Rectangle personagemRetangulo){
        return colisoes.ColisaoCirculo(personagemRetangulo, insereObjArray.getObjCircular());
    }

    public boolean ChamaColisaoEllipse(Rectangle personagemRetangulo){
        return colisoes.ColisaoElipse(personagemRetangulo, insereObjArray.getObjElipse());
    }
}
