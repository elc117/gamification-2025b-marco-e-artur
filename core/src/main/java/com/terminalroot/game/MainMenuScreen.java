package com.terminalroot.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

import static com.terminalroot.game.Controle_Diagrama_Estados.State.*;

public class MainMenuScreen implements Screen {

    final Main game;
    final Controle_Diagrama_Estados controle;
    Texture tela_principal;

    // Implementação do personagem
    private Boneco boneco;

    //Teste
    private CriaConstrucao criaConstrucao;

    // Arrays duplicados, precisa tentar refatorar isso -> Resolvido eu acho
    private ArrayList<Ellipse> obstaculosElipse;
    private ArrayList<ObstaculoCirculo> obstaculoCirculos;
    private ArrayList<Rectangle> obstaculosRetangulo;

    // Teste Menu
    Texture menuOpcoes;

    //Stage e UI
    private Stage stage;
    private Skin skin;
    private ControleBotao controleMenu;

    // Teste musica
    private Music musica;

    public MainMenuScreen(final Main game, Controle_Diagrama_Estados controle) {
        this.game = game;
        this.controle = controle;
    }

    @Override
    public void show() {
        tela_principal = new Texture("mapafinal.png");
        Texture bonecoTexture = new Texture("bucket.png");
        Sprite bonecoSprite = new Sprite(bonecoTexture);

        //Teste musica
        musica = Gdx.audio.newMusic(Gdx.files.internal("Musicas/MusicaMainmenu.mp3"));
        musica.setLooping(true);
        musica.setVolume(0.5f);
        musica.play();

        // Definição dos Arrays de obstaculos no Menu Principal, precisa passar como argumento em criaConstrucao agora
        obstaculosRetangulo = new ArrayList<>();
        obstaculoCirculos = new ArrayList<>();
        obstaculosElipse = new ArrayList<>();

        criaConstrucao = new CriaConstrucao(game,obstaculosRetangulo, obstaculoCirculos, obstaculosElipse);

        boneco = new Boneco(bonecoSprite, criaConstrucao, controle);

        boneco.setPosition(game.viewport.getWorldWidth()/2f, game.viewport.getWorldHeight()/2f);

        boneco.setSize(0.5f,0.5f);

        // Teste
        criaConstrucao.CriarPrediosTelaInicial(5000f, 4000f, 0f, 0f, 1530f, 1915f, obstaculosRetangulo);
        criaConstrucao.CriarPrediosTelaInicial(5000f, 4000f, 3050f, 0f, 2000f, 1660f, obstaculosRetangulo);
        criaConstrucao.CriarPrediosTelaInicial(5000f, 4000f, 1910f, 0f, 1100f, 600f, obstaculosRetangulo);
        criaConstrucao.CriarElipse(5000f, 4000f, -1200f, 3200f, 1100f, 850f,obstaculosElipse);
        criaConstrucao.CriarElipse(5000f, 4000f, 2200f, 2900f, 870f, 950f,obstaculosElipse);
        criaConstrucao.CriarCirculos(5000f, 4000f, 4100f, 1800f, 100f, "MENU_ATIVIDADES_ESTUDOS");
        criaConstrucao.CriarCirculos(5000f, 4000f, 580f, 1800f, 100f, "MENU_ATIVIDADES_FISICAS");
        // TROCAR O DE BAIXO PARA MENU DESCANSO, REGISTRAR O SONO, TEM QUE MUDAR O DIAGRAMA DE ESTADOS
        criaConstrucao.CriarCirculos(5000f, 4000f, 2480f, 500f, 100f, "MENU_QUIZ");
        criaConstrucao.CriarCirculos(5000f, 4000f, 1950f, 3100f, 100f, "MENU_BATALHA");
        criaConstrucao.CriarCirculos(5000f, 4000f, 3150f, 2000f, 100f, "MENU_CARACTERISTICA");

        // Teste Menu
        menuOpcoes = new Texture("Inventario/Opcoes.png");

        // TESTE BOTOES
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("ui/ui_skin.json"));

        controleMenu = new ControleBotao(stage,skin);

        controleMenu.criarBotao("",7,224,18f,10f, new Botao.AcaoBotao(){
            @Override
            public void executar(){
                controle.Trocar_estado(MENU_LOJA);
            }
        });

        controleMenu.criarBotao("",7,196,18f,10f, new Botao.AcaoBotao(){
            @Override
            public void executar(){
                controle.Trocar_estado(MENU_MOCHILA);
            }
        });

        controleMenu.criarBotao("",7,176,18f,10f, new Botao.AcaoBotao(){
            @Override
            public void executar(){
                controle.Trocar_estado(MENU_MISSAO);
            }
        });

        com.badlogic.gdx.InputMultiplexer multiplexer = new com.badlogic.gdx.InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(boneco);
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.DARK_GRAY);

        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(tela_principal,0,0,game.viewport.getWorldWidth(),game.viewport.getWorldHeight());
        game.batch.draw(menuOpcoes, 0f, 1.80f, 0.3f, 0.7f);
        boneco.draw(game.batch);
        game.batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null); // Teste, aparentemente precisa disso quando troca de tela
        if (musica != null) {
            musica.stop(); // teste p parar a musica, aparentemente é isos q tem q chamar
        }
    }

    @Override
    public void dispose() {
        tela_principal.dispose();
        stage.dispose();
        skin.dispose();
        musica.dispose();
    }
}
