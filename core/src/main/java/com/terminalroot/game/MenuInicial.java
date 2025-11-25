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

public class MenuInicial implements Screen {
    final Main game;
    final Controle_Diagrama_Estados controle;

    private Stage stage;
    private Texture imagem_pos_video;
    private Skin skin;
    ControleBotao controleMenuInicial;

    private boolean video_acabou = true;

    public MenuInicial(final Main game, Controle_Diagrama_Estados controle){
        this.game = game;
        this.controle = controle;
    }

    @Override
    public void show(){
        imagem_pos_video = new Texture(Gdx.files.internal("Imagem_final.png"));

        video_acabou = true;

        skin = new Skin(Gdx.files.internal("ui/ui_skin.json"));
        stage = new Stage(new ScreenViewport());

        controleMenuInicial = new ControleBotao(stage,skin);

        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.top().right();
        stage.addActor(table);

        controleMenuInicial.criarBotao("Novo Jogo", 58, 450, 60f, 2f, new Botao.AcaoBotao(){
            @Override

            public void executar() {
                game.resetarDados();
                controle.Trocar_estado(MENU_PRINCIPAL);
            }
        });

        controleMenuInicial.criarBotao("Carregar Jogo", 638, 450, 60f, 2f, new Botao.AcaoBotao(){
            @Override
            public void executar() {
                controle.Trocar_estado(MENU_PRINCIPAL);
            }
        });
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(video_acabou){
            ScreenUtils.clear(Color.DARK_GRAY);

            game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
            game.batch.begin();
            game.batch.draw(imagem_pos_video,
                0, 0,
                game.viewport.getWorldWidth(),
                game.viewport.getWorldHeight());
            game.batch.end();

            stage.act(delta);
            stage.draw();
        }
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
        if (imagem_pos_video != null) imagem_pos_video.dispose();
        if (stage != null) stage.dispose();
        if (skin != null) skin.dispose();
    }
}
