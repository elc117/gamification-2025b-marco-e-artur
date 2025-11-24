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

import static com.terminalroot.game.Main.forca;
import static com.terminalroot.game.Main.inteligencia;

public class Caracteristica implements Screen {
    final Main game;
    final Controle_Diagrama_Estados controle;
    Texture tela_principal;

    private Boneco boneco;
    private CriaConstrucao criaConstrucao;
    private ArrayList<Ellipse> obstaculosElipse;
    private ArrayList<ObstaculoCirculo> obstaculoCirculos;
    private ArrayList<Rectangle> obstaculosRetangulo;

    private Rectangle zonainteligencia;
    private Rectangle zonaforca;
    private Stage stage;
    private Skin skin;
    private BitmapFont font;
    private boolean bonecoOverlapInt = false;
    private boolean bonecoOverlapForca = false;

    private ControleBotao controleCaracteristica;

    // Labels como atributos para poder atualizar
    private Label labelInteligencia;
    private Label labelForca;

    public Caracteristica(final Main game, Controle_Diagrama_Estados controle){
        this.game = game;
        this.controle = controle;
    }

    public void show(){
        tela_principal = new Texture("Caracteristicas/basemapteste.png");
        Texture bonecoTexture = new Texture("bucket.png");
        Sprite bonecoSprite = new Sprite(bonecoTexture);

        obstaculosRetangulo = new ArrayList<>();
        obstaculoCirculos = new ArrayList<>();
        obstaculosElipse = new ArrayList<>();

        criaConstrucao = new CriaConstrucao(game, obstaculosRetangulo, obstaculoCirculos, obstaculosElipse);

        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("ui/ui_skin.json"));
        font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;

        labelInteligencia = new Label("Inteligencia: " + inteligencia, labelStyle);
        labelInteligencia.setPosition(630, 420);
        stage.addActor(labelInteligencia);

        labelForca = new Label("Forca: " + forca, labelStyle);
        labelForca.setPosition(630, 390);
        stage.addActor(labelForca);

        zonainteligencia = new Rectangle(4.8f, 3.4f, 0.5f, 0.5f);
        zonaforca = new Rectangle(1.5f, 3.4f, 0.5f, 0.5f); // Zona de força abaixo da inteligência

        boneco = new Boneco(bonecoSprite, criaConstrucao, controle);
        boneco.setPosition(game.viewport.getWorldWidth()/2f, game.viewport.getWorldHeight()/2f);
        boneco.setSize(0.5f, 0.5f);

        controleCaracteristica = new ControleBotao(stage, skin);
        controleCaracteristica.criarBotao("", 4.8f, 3.4f, 0.5f, 0.5f, new Botao.AcaoBotao() {
            @Override
            public void executar() {
            }
        });

        // botao de forca
        controleCaracteristica.criarBotao("", 4.8f, 2.7f, 0.5f, 0.5f, new Botao.AcaoBotao() {
            @Override
            public void executar() {
            }
        });

        com.badlogic.gdx.InputAdapter inputAdapter = new com.badlogic.gdx.InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                com.badlogic.gdx.math.Vector3 worldCoords = game.viewport.unproject(
                    new com.badlogic.gdx.math.Vector3(screenX, screenY, 0)
                );

                Rectangle bonecolimite = boneco.limitesRetangulo();

                if (zonainteligencia.contains(worldCoords.x, worldCoords.y) &&
                    zonainteligencia.overlaps(bonecolimite)) {
                    Main.inteligencia += 1;
                    labelInteligencia.setText("Inteligencia: " + Main.inteligencia);
                    return true;
                }
                if (zonaforca.contains(worldCoords.x, worldCoords.y) &&
                    zonaforca.overlaps(bonecolimite)) {
                    Main.forca += 1;
                    labelForca.setText("Forca: " + Main.forca);
                    return true;
                }
                return false;
            }
        };

        com.badlogic.gdx.InputMultiplexer multiplexer = new com.badlogic.gdx.InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(inputAdapter);
        multiplexer.addProcessor(boneco);
        Gdx.input.setInputProcessor(multiplexer);
    }

    public void render(float delta){
        ScreenUtils.clear(Color.DARK_GRAY);
        Rectangle bonecolimite = boneco.limitesRetangulo();
        boolean estaEmZonaInt = zonainteligencia.overlaps(bonecolimite);
        boolean estaEmZonaForca = zonaforca.overlaps(bonecolimite);

        bonecoOverlapInt = estaEmZonaInt;

        bonecoOverlapForca = estaEmZonaForca;

        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(tela_principal, 0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        boneco.draw(game.batch);
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
