package com.terminalroot.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;

public class AtividadesFisicas implements Screen {
    final Main game;
    final Controle_Diagrama_Estados controle;
    Texture basemap;

    private Boneco boneco;
    private CriaConstrucao criaConstrucao;
    private ArrayList<Ellipse> obstaculosElipse;
    private ArrayList<ObstaculoCirculo> obstaculoCirculos;
    private ArrayList<Rectangle> obstaculosRetangulo;

    private Rectangle ZonaTreino;
    private float TempoTreino = 0f;
    private static final float INTERVALO_TREINO = 3600f;

    public static boolean TreinandoForca;

    public AtividadesFisicas(final Main game, Controle_Diagrama_Estados controle){
        this.game = game;
        this.controle = controle;
    }

    public void show(){
        basemap = new Texture("acad.png");
        Texture bonecoTexture = new Texture("bucket.png");
        Sprite bonecoSprite = new Sprite(bonecoTexture);

        obstaculosRetangulo = new ArrayList<>();
        obstaculoCirculos = new ArrayList<>();
        obstaculosElipse = new ArrayList<>();

        criaConstrucao = new CriaConstrucao(game, obstaculosRetangulo, obstaculoCirculos, obstaculosElipse);

        boneco = new Boneco(bonecoSprite, criaConstrucao, controle);
        boneco.setPosition(game.viewport.getWorldWidth()/2f, game.viewport.getWorldHeight()/2f);
        boneco.setSize(0.5f, 0.5f);
        Gdx.input.setInputProcessor(boneco);

        ZonaTreino = new Rectangle(3.2f, 2.4f, 0.5f, 0.5f);
        TempoTreino = 0f;
        // Teste flag
        TreinandoForca = false;
    }

    public void render(float delta) {
        game.batch.begin();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.draw(basemap, 0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        boneco.draw(game.batch);
        game.batch.end();

        Rectangle bonecoLimite = boneco.limitesRetangulo();
        if (ZonaTreino.overlaps(bonecoLimite)) {
            TempoTreino += delta;
            TreinandoForca = true;
            if (TempoTreino >= INTERVALO_TREINO) {
                Main.forca += 1;
                Main.setForca(Main.forca);
                TempoTreino = 0f;
            }
        }
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
