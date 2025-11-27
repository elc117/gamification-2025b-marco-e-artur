package com.terminalroot.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.terminalroot.game.Controle_Diagrama_Estados.State.MENU_PRINCIPAL;

public class MenuFaction implements Screen {
    final Main game;
    final Controle_Diagrama_Estados controle;

    private Stage stage;
    private Skin skin;
    ControleBotao controleMenuInicial;


    Texture imagemtela;

    public MenuFaction(final Main game, Controle_Diagrama_Estados controle){
        this.game = game;
        this.controle = controle;
    }

    @Override
    public void show(){
        imagemtela = new Texture("angeldemon.jpg");

        skin = new Skin(Gdx.files.internal("ui/ui_skin.json"));
        stage = new Stage(new ScreenViewport());

        controleMenuInicial = new ControleBotao(stage,skin);

        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.top().right();
        stage.addActor(table);

        controleMenuInicial.criarBotao("Anjos", 58, 450, 60f, 2f, new Botao.AcaoBotao(){
            @Override

            public void executar() {
                Main.eanjo = true;
                controle.Trocar_estado(MENU_PRINCIPAL);
            }
        });

        controleMenuInicial.criarBotao("Demonios", 638, 450, 60f, 2f, new Botao.AcaoBotao(){
            @Override
            public void executar() {
                Main.eanjo = false;
                controle.Trocar_estado(MENU_PRINCIPAL);
            }
        });
    }

    @Override
    public void render(float delta){
        ScreenUtils.clear(Color.DARK_GRAY);
        game.batch.setProjectionMatrix(stage.getCamera().combined);
        game.batch.begin();
        game.batch.setColor(Color.WHITE);
        game.batch.draw(imagemtela, 0, 0, stage.getWidth(), stage.getHeight());
        game.batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
        if (stage != null) {
            stage.getViewport().update(width, height, true);
        }
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}

    @Override
    public void dispose() {
        if (stage != null) stage.dispose();
        if (skin != null) skin.dispose();
    }
}
