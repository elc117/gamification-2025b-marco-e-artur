package com.terminalroot.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;
import org.w3c.dom.Text;

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

    // flags pra controlar o video
    private boolean video_acabou = false;
    Texture imagem_pos_video;

    public MenuInicial(final Drop game, Controle_Diagrama_Estados controle){
        this.game = game;
        this.controle = controle;
        this.batch = new SpriteBatch();
    }

    public void show(){
        imagem_pos_video = new Texture("Imagem_final.png");

        player = VideoPlayerCreator.createVideoPlayer();
        FileHandle file = Gdx.files.internal("video_certo.webm");

        try {
            player.load(file);
            player.play();
        } catch (Exception e) {
            System.err.println("Erro ao carregar vídeo: " + e.getMessage());
        }

        Skin skin = new Skin(Gdx.files.internal("ui/ui_skin.json"));
        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.top().right();
        stage.addActor(table);

        Botao botaoiniciar = new Botao("Iniciar", skin, new Botao.AcaoBotao() {
            @Override
            public void executar() {
                controle.Trocar_estado(MENU_PRINCIPAL);
            }
        });
        table.add(botaoiniciar).width(100).height(50).pad(20);
        table.row();
    }

    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (player != null && !video_acabou) {
            player.update();

            if(!player.isPlaying()){
                video_acabou = true;
            }

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
        // depos que o vídeo acaba a gente quer chamar 1 imagem igual ao ultimo frase do video e os botões
        if(video_acabou == true){
            // isso aq é p limpar a tela aparentemente, acho que dá pra tirar depois
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            batch.begin();
            // Essa aqui é a imagem de plano de fundo, tudo é pra ser desenhado em cima da imagem, em tese
            // a imagem aparnetemente tá sendo esticada, TEM QUE ARRUMAR ISSO EM ALGUM MOMENTO COM PACIÊNCIA ><<<><><><
            batch.draw(imagem_pos_video, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.end();

            stage.act(delta);
            stage.draw();
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
