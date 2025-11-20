package com.terminalroot.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import java.util.*;

public class Quiz implements Screen {
    final Main game;
    final Controle_Diagrama_Estados controle;

    private int questaoAtual;
    private Texture imagemQuestao;
    private int acertos; // ainda não foi utilizado
    private Random ran = new Random();
    private Stage stage;

    public Quiz(final Main game, Controle_Diagrama_Estados controle) { // construtor
        this.game = game;
        this.controle = controle;
        this.questaoAtual = ran.nextInt(10); // numero aleatório de 1 a 10
        this.acertos = 0;
        game.viewport = new FillViewport(1280, 720, new OrthographicCamera());

    }

    private void carregarQuestao() {
        if (questaoAtual >= BancoQuestoes.questoes.length) {
            finalizarQuiz();
            return;
        }

        if (imagemQuestao != null) {
            imagemQuestao.dispose();
        }

        Questao questao = BancoQuestoes.questoes[questaoAtual]; // carrega as questões
        imagemQuestao = new Texture(Gdx.files.internal(questao.imagem));
    }

    private void novaquestaoaleatoria(){

        boolean testequestao = false;

        for(Questao q : BancoQuestoes.questoes){ // pra cada questão no banco, vemos se tem alguma que não foi respondida, se tiver, passa para a próxima etapa
            if(!q.getFlag()){
                testequestao = true;
                break;
            }
        }

        if (!testequestao){ // se não tiver, finaliza o quiz e volta pra tela principal
            finalizarQuiz();
            return;
        }

        int num = ran.nextInt(10);
        while(BancoQuestoes.questoes[num].getFlag()){ //
            num = ran.nextInt(10); // talvez não seja a forma mais inteligente de achar um número aleatório que não esteja respondido, mas é simples e funcional
        }
        questaoAtual = num;
        carregarQuestao();
        return;
    }

    private void verificaresposta(char resposta) { // verifica a resposta
        Questao questao = BancoQuestoes.questoes[questaoAtual];

        if (questao.estaCorreta(resposta)) {
            acertos++;
            System.out.println("Acertou");
        } else {
            System.out.println("Errou");
        }

        questao.setFlag(); // seta a flag pra avisar q a questao foi respondida

        novaquestaoaleatoria(); // pega outra aleatória

        System.out.println(acertos + "/10");

    }

    private void finalizarQuiz() { // volta pro menu principal quando o quiz é terminado
        controle.Trocar_estado(Controle_Diagrama_Estados.State.MENU_PRINCIPAL);
    }


    public void show() {
        System.out.println("Tela: " + this.getClass().getSimpleName() + " foi mostrada!");
        carregarQuestao();

        Skin skin = new Skin(Gdx.files.internal("ui/ui_skin.json"));
        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        float h = game.viewport.getWorldHeight();

        float bw = 200;
        float bh = 30;
        float bx = 300;

        // Botão A
        Botao botA = new Botao("", skin, () -> {verificaresposta('A'); System.out.println('A');});
        botA.setBounds(bx, h * 0.35f, bw, bh);
        stage.addActor(botA);

        botA.getStyle().up = null; // por algum motivo isso transforma todos botões da tela em transparente, mas pelo menos não preciso transformar 1 por 1
        botA.getStyle().down = null;
        botA.getStyle().over = null;

        // Botão B
        Botao botB = new Botao("", skin, () -> {verificaresposta('B'); System.out.println('B');});
        botB.setBounds(bx, h * 0.30f, bw, bh);
        stage.addActor(botB);

        // Botão C
        Botao botC = new Botao("", skin, () -> {verificaresposta('C'); System.out.println('C');});
        botC.setBounds(bx, h * 0.25f, bw, bh);
        stage.addActor(botC);

        // Botão D
        Botao botD = new Botao("", skin, () -> {verificaresposta('D'); System.out.println('D');});
        botD.setBounds(bx, h * 0.20f, bw, bh);
        stage.addActor(botD);
    }

    public void render(float delta) { // input das respostas e renderização da questao
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        ScreenUtils.clear(Color.BLACK);
        game.batch.begin();

        if (imagemQuestao != null) { // isso serve só pra aparecer na tela e dar um resize caso tu aumente o tamanho da janela.
            float scale = Math.min( // aconselho aumentar o tamanho na janela pq se não fica ruim de ler
                game.viewport.getWorldWidth() / (float) imagemQuestao.getWidth(),
                game.viewport.getWorldHeight() / (float) imagemQuestao.getHeight());

            float width = imagemQuestao.getWidth() * scale;
            float height = imagemQuestao.getHeight() * scale;
            float x = (game.viewport.getWorldWidth() - width) / 2;
            float y = (game.viewport.getWorldHeight() - height) / 2;

            game.batch.draw(imagemQuestao, x, y, width, height);
        }

        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
