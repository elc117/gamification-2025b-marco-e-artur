package com.terminalroot.game;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class Colisoes {
    public boolean ColisaoRetangulo(Rectangle bonecoRetangulo, ArrayList<Rectangle> ObjRetangular){
        for(Rectangle objeto : ObjRetangular){
            if(bonecoRetangulo.overlaps(objeto)){
                return true;
            }
        }
        return false;
    }

    public ObstaculoCirculo ColisaoCirculo(Rectangle boneco, ArrayList<ObstaculoCirculo> obstaculos) {
        for (ObstaculoCirculo obstaculo : obstaculos) {
            if (Intersector.overlaps(obstaculo.circle, boneco)) {
                return obstaculo;
            }
        }
        return null;
    }

    public boolean ColisaoElipse(Rectangle boneco, ArrayList<Ellipse> ObjEliptico){
        for(Ellipse objetoEliptico : ObjEliptico){
            float centroX = objetoEliptico.x + objetoEliptico.width / 2;  // CORRIGIDO
            float centroY = objetoEliptico.y + objetoEliptico.height / 2; // CORRIGIDO
            float raioX = objetoEliptico.width / 2;
            float raioY = objetoEliptico.height / 2;

            float pontoProximoX = Math.max(boneco.x, Math.min(centroX, boneco.x + boneco.width));
            float pontoProximoY = Math.max(boneco.y, Math.min(centroY, boneco.y + boneco.height));

            float dx = pontoProximoX - centroX;
            float dy = pontoProximoY - centroY;

            float distanciaNorm = (dx * dx) / (raioX * raioX) + (dy * dy) / (raioY * raioY);

            if (distanciaNorm <= 1){
                return true;
            }
        }
        return false;
    }
}
