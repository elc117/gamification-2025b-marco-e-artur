package com.terminalroot.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.io.FileNotFoundException;

import static com.terminalroot.game.Controle_Diagrama_Estados.State.MENU_PRINCIPAL;

public class MenuInicial implements Screen {
    final Drop game;
    final Controle_Diagrama_Estados controle;

    private VideoPlayer player;
    private SpriteBatch batch;
    private int videoWidth;
    private int videoHeight;

    private Stage stage;
    private Skin skin;

    public MenuInicial(final Drop game, Controle_Diagrama_Estados controle){
        this.game = game;
        this.controle = controle;
        this.batch = new SpriteBatch();
    }

    public void show(){
        player = VideoPlayerCreator.createVideoPlayer();

        FileHandle file = Gdx.files.internal("video_intro.webm");

        try {
            player.load(file);
            player.play();
        } catch (Exception e) {
            System.err.println("Erro ao carregar vídeo: " + e.getMessage());
        }

        stage = new Stage(game.viewport);
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        controlebotao();

        Gdx.input.setInputProcessor(stage);
    }

    private void controlebotao(){
        Table table = new Table();
        table.setFillParent(true);

        Botao botao_iniciar = new Botao("Começar jogo", skin);
        botao_iniciar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controle.Trocar_estado(MENU_PRINCIPAL);
            }
        });

        table.bottom().right();
        table.add(botao_iniciar).pad(10).width(150).height(50);
    }

    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (player != null) {
            player.update();

                videoWidth = player.getVideoWidth();
                videoHeight = player.getVideoHeight();

            Texture frame = player.getTexture();
            if (frame != null) {
                batch.begin();

                float x = (Gdx.graphics.getWidth() - videoWidth) / 2f;
                float y = (Gdx.graphics.getHeight() - videoHeight) / 2f;

                batch.draw(frame, x, y, videoWidth, videoHeight);

                batch.end();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
    }
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {
        if (player != null) {
            player.dispose();
        }
        if (batch != null) {
            batch.dispose();
        }
    }
}
