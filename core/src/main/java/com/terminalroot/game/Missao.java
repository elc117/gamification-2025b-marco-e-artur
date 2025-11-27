package com.terminalroot.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

import static com.terminalroot.game.Controle_Diagrama_Estados.State.MENU_PRINCIPAL;
import static com.terminalroot.game.ItensBoneco.Arma.*;
import static com.terminalroot.game.ItensBoneco.Skins.SkinSolomonk;
import static com.terminalroot.game.ItensBoneco.Skins.Skinraposa;

public class Missao implements Screen {
    final Main game;
    final Controle_Diagrama_Estados controle;
    Texture tela_principal;

    private Stage stage;
    private Skin skin;
    private BitmapFont font;

    private ControleBotao controleMissao;

    Texture seta_volta;

    // Teste inserir quantidade moeda
    private Label labelmoedas;

    // TESTE botao p completar a qeyt
    Texture exclamacao;
    Texture certinho;

    private boolean missaoCompleta1 = false;
    private boolean missaoCompleta2 = false;
    private boolean missaoCompleta3 = false;

    public Missao (final Main game, Controle_Diagrama_Estados controle){
        this.game = game;
        this.controle = controle;
    }

    public void show(){
        tela_principal = new Texture("missao/missao.png");
        seta_volta = new Texture("Inventario/arrowLeft.png");

        exclamacao = new Texture("missao/exclamation.png");
        certinho = new Texture("missao/checkmark.png");

        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("ui/ui_skin.json"));
        font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;

        //Teste moedas
        labelmoedas = new Label("Moedas: " + Main.getMoedas(), labelStyle);
        labelmoedas.setPosition(550, 365);
        labelmoedas.setFontScale(2f);
        stage.addActor(labelmoedas);

        controleMissao = new ControleBotao(stage, skin);

        controleMissao.criarBotao("", 7, 400, 60f, 2f, new Botao.AcaoBotao(){
            @Override
            public void executar() {
                controle.Trocar_estado(MENU_PRINCIPAL);
            }
        });

        controleMissao.criarBotao("", 580, 300, 60f, 2f, new Botao.AcaoBotao(){
            @Override
            public void executar() {
                missaoCompleta1 = true;
                Main.setMoedas(Main.getMoedas() + 1);
            }
        });

        controleMissao.criarBotao("", 580, 200, 60f, 2f, new Botao.AcaoBotao(){
            @Override
            public void executar() {
                missaoCompleta2 = true;
                Main.setMoedas(Main.getMoedas() + 1);
            }
        });

        controleMissao.criarBotao("", 580, 100, 60f, 2f, new Botao.AcaoBotao(){
            @Override
            public void executar() {
                missaoCompleta3 = true;
                Main.setMoedas(Main.getMoedas() + 1);
            }
        });

        com.badlogic.gdx.InputMultiplexer multiplexer = new com.badlogic.gdx.InputMultiplexer();
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);
    }

    private void atualizarMoedas() {
        labelmoedas.setText("Moedas: " + Main.getMoedas());
    }

    public void render(float delta){
        ScreenUtils.clear(Color.DARK_GRAY);

        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(tela_principal, 0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        game.batch.draw(seta_volta,0,4.5f,game.viewport.getWorldWidth() /12, game.viewport.getWorldHeight() / 12);

        if (missaoCompleta1) {
            game.batch.draw(certinho, 5.6f, 2.7f, 1f, 1f);
        } else {
            game.batch.draw(exclamacao, 5.6f, 2.7f, 1f, 1f);
        }

        if (missaoCompleta2) {
            game.batch.draw(certinho, 5.6f, 1.6f, 1f, 1f);
        } else {
            game.batch.draw(exclamacao, 5.6f, 1.6f, 1f, 1f);
        }

        if (missaoCompleta3) {
            game.batch.draw(certinho, 5.6f, 0.5f, 1f, 1f);
        } else {
            game.batch.draw(exclamacao, 5.6f, 0.5f, 1f, 1f);
        }

        game.batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        tela_principal.dispose();
        stage.dispose();
        skin.dispose();
        font.dispose();
    }
}
