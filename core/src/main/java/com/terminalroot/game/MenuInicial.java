package com.terminalroot.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;

import java.io.FileNotFoundException;

public class MenuInicial implements Screen {
    final Drop game;
    final Controle_Diagrama_Estados controle;

    private VideoPlayer player;
    private SpriteBatch batch;
    private int videoWidth;
    private int videoHeight;

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
