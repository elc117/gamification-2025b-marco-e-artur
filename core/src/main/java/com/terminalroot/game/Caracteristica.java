package com.terminalroot.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    private ShapeRenderer shapeRenderer;

    private Rectangle zonainteligencia;
    private Stage stage;
    private Skin skin;
    private BitmapFont font;
    private boolean bonecoOverlapInt = false;

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
        shapeRenderer = new ShapeRenderer();

        // Não criar obstáculos - apenas a zona de inteligência será visível

        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("ui/ui_skin.json"));
        font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;

        // Transformar em atributos da classe
        labelInteligencia = new Label("Inteligencia: " + inteligencia, labelStyle);
        labelInteligencia.setPosition(630, 420);
        stage.addActor(labelInteligencia);

        labelForca = new Label("Forca: " + forca, labelStyle);
        labelForca.setPosition(630, 390);
        stage.addActor(labelForca);

        zonainteligencia = new Rectangle(4.8f, 3.4f, 0.5f, 0.5f);

        boneco = new Boneco(bonecoSprite, criaConstrucao, controle);
        boneco.setPosition(game.viewport.getWorldWidth()/2f, game.viewport.getWorldHeight()/2f);
        boneco.setSize(0.5f, 0.5f);

        com.badlogic.gdx.InputAdapter inputAdapter = new com.badlogic.gdx.InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                // Verifica se o boneco está na zona de inteligência
                Rectangle bonecoRect = new Rectangle(boneco.getX(), boneco.getY(),
                    boneco.getWidth(), boneco.getHeight());

                if (zonainteligencia.overlaps(bonecoRect)) {
                    Main.inteligencia += 1;
                    labelInteligencia.setText("Inteligencia: " + Main.inteligencia);
                    System.out.println("Inteligencia aumentada! Total: " + Main.inteligencia);
                }

                return false; // Não consome o evento
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
        Rectangle bonecoRect = new Rectangle(boneco.getX(), boneco.getY(),
            boneco.getWidth(), boneco.getHeight());
        boolean estaEmZona = zonainteligencia.overlaps(bonecoRect);

        if (estaEmZona && !bonecoOverlapInt) {
            System.out.println("Boneco entrou na zona de inteligencia!");
        }
        bonecoOverlapInt = estaEmZona;

        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(tela_principal, 0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        boneco.draw(game.batch);
        game.batch.end();

        shapeRenderer.setProjectionMatrix(game.viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Desenha o boneco
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(boneco.getX(), boneco.getY(), boneco.getWidth(), boneco.getHeight());

        // Desenha a zona de inteligência (roxo quando fora, verde quando dentro)
        if (estaEmZona) {
            shapeRenderer.setColor(Color.GREEN);
        } else {
            shapeRenderer.setColor(Color.PURPLE);
        }
        shapeRenderer.rect(zonainteligencia.x, zonainteligencia.y, zonainteligencia.width, zonainteligencia.height);

        shapeRenderer.end();

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
        shapeRenderer.dispose();
        stage.dispose();
        skin.dispose();
        font.dispose();
    }
}
