package com.terminalroot.game;

import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class InsereObjArray {
    private ArrayList<Rectangle> Objretangular;
    private ArrayList<ObstaculoCirculo> ObjCircular;
    private ArrayList<Ellipse> ObjElipse;

    public InsereObjArray(ArrayList<Rectangle> Objretangular, ArrayList<ObstaculoCirculo> ObjCircular, ArrayList<Ellipse>ObjElipse){
        this.Objretangular = Objretangular;
        this.ObjCircular = ObjCircular;
        this.ObjElipse = ObjElipse;
    }

    public void InserirObjcircular(ObstaculoCirculo Objetocircular){
        ObjCircular.add(Objetocircular);
    }

    public void InserirObjretangular(float x, float y, float largura, float altura){
        Rectangle Objetoretangular = new Rectangle(x,y,largura,altura);
        Objretangular.add(Objetoretangular);
    }

    public void InserirObjElipse(float x, float y, float largura, float altura){
        Ellipse ObjetoElipse = new Ellipse(x,y,largura,altura);
        ObjElipse.add(ObjetoElipse);
    }

    public ArrayList<Rectangle> getObjRetangular(){
        return Objretangular;
    }

    public ArrayList<ObstaculoCirculo> getObjCircular(){
        return ObjCircular;
    }

    public ArrayList<Ellipse> getObjElipse(){return ObjElipse;}
}
