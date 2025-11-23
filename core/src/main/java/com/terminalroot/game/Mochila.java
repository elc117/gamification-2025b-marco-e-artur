package com.terminalroot.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.terminalroot.game.Controle_Diagrama_Estados.State.MENU_PRINCIPAL;
import static com.terminalroot.game.Main.SkinBoneco;

public class Mochila implements Screen {
    final Main game;
    final Controle_Diagrama_Estados controle;
    Texture background;
    Texture boneco;
    Texture seta_volta;
    // Icone Skins
    Texture Skinpadrao;
    Texture Skinsolomonk;
    Texture SkinRaposa;

    // Implementacao botoes
    private Stage stage;
    private Skin skin;
    private ControleBotao controleMochila;

    public Mochila(final Main game, Controle_Diagrama_Estados controle){
        this.game = game;
        this.controle = controle;
    }

    public void show(){
        background = new Texture("Inventario/backgroundinventario.png");
        seta_volta = new Texture("Inventario/arrowLeft.png");
        Skinpadrao = new Texture("Inventario/gaara.png");
        Skinsolomonk = new Texture("Inventario/solomonk.png");
        SkinRaposa = new Texture("Inventario/raposa.png");

        switch (SkinBoneco){
            case "SkinBasica":
                boneco = new Texture("hero/Eni/baixo.png");
                break;
            case "SkinSolomonk":
                boneco = new Texture("hero/EniSkin1/Semfundo.png");
                break;
            case "SkinLast":
                boneco = new Texture("hero/EniSkin2/SemFundo.png");
                break;
        }
        // Botoes
        stage = new Stage(new ScreenViewport());
        stage.setDebugAll(true);
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("ui/ui_skin.json"));
        controleMochila = new ControleBotao(stage,skin);

        controleMochila.criarBotao("", 7, 450, 60f, 2f, new Botao.AcaoBotao(){
            @Override
            public void executar() {
                controle.Trocar_estado(MENU_PRINCIPAL);
            }
        });

        controleMochila.criarBotao("", 7, 50, 40f, 2f, new Botao.AcaoBotao(){
            @Override
            public void executar() {
                if(boneco != null) {
                    boneco.dispose();
                }
                boneco = new Texture ("hero/Eni/baixo.png");
                SkinBoneco = "SkinBasica";
            }
        });

        controleMochila.criarBotao("", 115, 50, 50f, 2f, new Botao.AcaoBotao(){
            @Override
            public void executar() {
                if(boneco != null) {
                    boneco.dispose();
                }
                boneco = new Texture ("hero/EniSkin1/Semfundo.png");
                SkinBoneco = "SkinSolomonk";
            }
        });

        controleMochila.criarBotao("", 205, 50, 50f, 2f, new Botao.AcaoBotao(){
            @Override
            public void executar() {
                if(boneco != null) {
                    boneco.dispose();
                }
                boneco = new Texture ("hero/EniSkin2/SemFundo.png");
                SkinBoneco = "SkinLast";
            }
        });
    }

    public void render(float delta){
        ScreenUtils.clear(Color.DARK_GRAY);
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(background,0,0,game.viewport.getWorldWidth(),game.viewport.getWorldHeight());
        game.batch.draw(seta_volta,0,4.5f,game.viewport.getWorldWidth() /12, game.viewport.getWorldHeight() / 12);
        game.batch.draw(Skinpadrao,0,0.3f,game.viewport.getWorldWidth() /12, game.viewport.getWorldHeight() / 12);
        game.batch.draw(Skinsolomonk,1,0.3f,game.viewport.getWorldWidth() / 12, game.viewport.getWorldHeight() / 12);
        game.batch.draw(SkinRaposa, 2, 0.3f,game.viewport.getWorldWidth() / 12, game.viewport.getWorldHeight() / 12);
        game.batch.draw(boneco, 2, 1, game.viewport.getWorldWidth() / 2, game.viewport.getWorldHeight() / 2);
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
    public void hide() {}
    @Override
    public void dispose() {}
}
