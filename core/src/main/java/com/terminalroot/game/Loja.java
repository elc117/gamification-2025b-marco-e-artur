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

public class Loja implements Screen {
    final Main game;
    final Controle_Diagrama_Estados controle;
    Texture tela_principal;

    private Stage stage;
    private Skin skin;
    private BitmapFont font;

    private ControleBotao controleLoja;

    Texture seta_volta;

    public Loja (final Main game, Controle_Diagrama_Estados controle){
        this.game = game;
        this.controle = controle;
    }

    public void show(){
        tela_principal = new Texture("Loja.png");
        seta_volta = new Texture("Inventario/arrowLeft.png");

        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("ui/ui_skin.json"));
        font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;

        controleLoja = new ControleBotao(stage, skin);

        controleLoja.criarBotao("", 7, 450, 60f, 2f, new Botao.AcaoBotao(){
            @Override
            public void executar() {
                controle.Trocar_estado(MENU_PRINCIPAL);
            }
        });

        controleLoja.criarBotao("", 190, 200, 40f, 1f, new Botao.AcaoBotao(){
            @Override
            public void executar() {
                // Arma Forca1
                Main.inventario.desbloquearArma(FORCA_1);
            }
        });

        controleLoja.criarBotao("", 320, 200, 40f, 1f, new Botao.AcaoBotao(){
            @Override
            public void executar() {
                // Arma forca2
                Main.inventario.desbloquearArma(FORCA_2);
            }
        });

        controleLoja.criarBotao("", 450, 200, 40f, 1f, new Botao.AcaoBotao(){
            @Override
            public void executar() {
                // Arma forca3
                Main.inventario.desbloquearArma(FORCA_3);
            }
        });

        controleLoja.criarBotao("", 580, 200, 40f, 1f, new Botao.AcaoBotao(){
            @Override
            public void executar() {
                //Arma int 1
                Main.inventario.desbloquearArma(INT_1);
            }
        });

        controleLoja.criarBotao("", 190, 80, 40f, 1f, new Botao.AcaoBotao(){
            @Override
            public void executar() {
                // Arma int2
                Main.inventario.desbloquearArma(INT_2);

            }
        });

        controleLoja.criarBotao("", 320, 80, 40f, 1f, new Botao.AcaoBotao(){
            @Override
            public void executar() {
                // Arma int 3
                Main.inventario.desbloquearArma(INT_3);
            }
        });

        controleLoja.criarBotao("", 450, 80, 40f, 1f, new Botao.AcaoBotao(){
            @Override
            public void executar() {
                // Skin solomonk
                Main.inventario.desbloquearSkin(SkinSolomonk);
            }
        });

        controleLoja.criarBotao("", 580, 80, 40f, 1f, new Botao.AcaoBotao(){
            @Override
            public void executar() {
                // Skin raposa
                Main.inventario.desbloquearSkin(Skinraposa);
            }
        });

        com.badlogic.gdx.InputMultiplexer multiplexer = new com.badlogic.gdx.InputMultiplexer();
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);
    }

    public void render(float delta){
        ScreenUtils.clear(Color.DARK_GRAY);

        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(tela_principal, 0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        game.batch.draw(seta_volta,0,4.5f,game.viewport.getWorldWidth() /12, game.viewport.getWorldHeight() / 12);
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
