package com.terminalroot.game;

import com.badlogic.gdx.math.Circle;

public class ObstaculoCirculo {
    String nome;
    Circle circle;

    public ObstaculoCirculo(String nome, float centroX, float centroY, float raioCescala) {
        this.nome = nome;
        this.circle = new Circle(centroX, centroY, raioCescala);
    }

    public Circle getCircle(){
        return circle;
    }

    public String getNome(){
        return nome;
    }
}
