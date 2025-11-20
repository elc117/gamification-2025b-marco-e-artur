package com.terminalroot.game;

public class Questao {
    public String imagem; // caminho para imagem
    public char respostacorreta;
    private boolean flag; // a flag serve pra ver se a resposta ja foi respondida, já que queremos uma por "dia", precisa ser uma que ainda não foi respondida


    public Questao(String imagem, char respostacorreta) { // construtor
        this.imagem = imagem;
        this.respostacorreta = respostacorreta;
        this.flag = false;
    }

    public boolean estaCorreta(char respostausuario) { // verifica resposta
        return respostausuario ==  respostacorreta;
    }

    public boolean getFlag(){
        return flag;
    }

    public void setFlag(){ // seta pra true, usada quando a questão for respondida
        this.flag = true;
    }






}
