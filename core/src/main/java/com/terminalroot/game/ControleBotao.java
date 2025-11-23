package com.terminalroot.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;

public class ControleBotao {
    private Stage stage;
    private Skin skin;
    private ArrayList<Botao> botoes;

    public ControleBotao(Stage stage, Skin skin){
        this.stage = stage;
        this.skin = skin;
        this.botoes = new ArrayList<>();
    }
    public Botao criarBotao(String frase, float x, float y, float largura, float altura, Botao.AcaoBotao acao){
        Botao botao = new Botao(frase, skin, acao);
        botao.setPosition(x,y);
        botao.setSize(largura,altura);

        botao.getStyle().up = null;
        botao.getStyle().down = null;
        botao.getStyle().over = null;

        botoes.add(botao);
        stage.addActor(botao);

        return  botao;
    }

    public void adicionarBotao(Botao botao){
        botoes.add(botao);
        stage.addActor(botao);
    }
}
