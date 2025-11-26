package com.terminalroot.game;

import com.badlogic.gdx.Screen;

public class Controle_Diagrama_Estados {
    private Screen tela_atual;
    private final Main game;

    public enum State{
        MENU_INICIAL,
        MENU_PRINCIPAL,
        MENU_QUIZ,
        MENU_ATIVIDADES_FISICAS,
        MENU_CARACTERISTICA,
        MENU_INTERFACE_BATALHA,
        MENU_BATALHA,
        MENU_MOCHILA,
        MENU_ATIVIDADES_ESTUDOS,
        MENU_FINALJOGO
    }

    private State estadoAtual;

    public Controle_Diagrama_Estados(Main game){
        this.game = game;
        Trocar_estado(State.MENU_INICIAL);
    }

    public void Trocar_estado(State novoEstado){
        System.out.println("troca tela paras: " + novoEstado);
        this.estadoAtual = novoEstado;

        switch (novoEstado){
            case MENU_INICIAL:
                tela_atual = new MenuInicial(game,this);
                break;

            case MENU_PRINCIPAL:
                tela_atual = new MainMenuScreen(game,this);
                break;

            case MENU_QUIZ:
                tela_atual = new Quiz(game,this);
                break;

            case MENU_ATIVIDADES_FISICAS:
                tela_atual = new AtividadesFisicas(game, this);
                break;

            case MENU_CARACTERISTICA:
                tela_atual = new Caracteristica(game,this);
                break;

            case MENU_INTERFACE_BATALHA:
                tela_atual = new MenuInterfaceBatalha(game,this);
                break;

            case MENU_BATALHA:
                tela_atual = new Batalha(game,this);
                break;

            case MENU_MOCHILA:
                tela_atual = new Mochila(game,this);
                break;

            case MENU_ATIVIDADES_ESTUDOS:
                tela_atual = new AtividadesEstudos(game, this);
                break;

            case MENU_FINALJOGO:
                tela_atual = new MenuFinalJogo(game,this);
                break;
        }
        game.setScreen(tela_atual);
    }

    public State getEstadoAtual(){
        return estadoAtual;
    }
}
