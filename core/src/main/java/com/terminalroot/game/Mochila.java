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
import static com.terminalroot.game.MainMenuScreen.SkinBoneco;

public class Mochila implements Screen {
    final Main game;
    final Controle_Diagrama_Estados controle;
    Texture background;
    Texture boneco;
    Texture seta_volta;

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
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("ui/ui_skin.json"));
        controleMochila = new ControleBotao(stage,skin);

        controleMochila.criarBotao("", 7, 450, 60f, 2f, new Botao.AcaoBotao(){
            @Override
            public void executar() {
                controle.Trocar_estado(MENU_PRINCIPAL);
            }
        });
    }

    public void render(float delta){
        ScreenUtils.clear(Color.DARK_GRAY);
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(background,0,0,game.viewport.getWorldWidth(),game.viewport.getWorldHeight());
        game.batch.draw(seta_volta,0,4.5f,game.viewport.getWorldWidth() /12, game.viewport.getWorldHeight() / 12) ;
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
