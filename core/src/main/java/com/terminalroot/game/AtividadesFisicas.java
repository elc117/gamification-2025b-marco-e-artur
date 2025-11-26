    package com.terminalroot.game;

    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.InputMultiplexer;
    import com.badlogic.gdx.Screen;
    import com.badlogic.gdx.audio.Music;
    import com.badlogic.gdx.graphics.Texture;
    import com.badlogic.gdx.graphics.g2d.BitmapFont;
    import com.badlogic.gdx.graphics.g2d.Sprite;
    import com.badlogic.gdx.math.Ellipse;
    import com.badlogic.gdx.math.Rectangle;
    import com.badlogic.gdx.scenes.scene2d.Stage;
    import com.badlogic.gdx.scenes.scene2d.ui.Skin;
    import com.badlogic.gdx.utils.viewport.ScreenViewport;

    import java.util.ArrayList;

    import static com.terminalroot.game.Controle_Diagrama_Estados.State.MENU_PRINCIPAL;

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

        private ControleBotao controleAtividadeFisica;
        private Stage stage;
        private Skin skin;
        Texture seta_volta;

        private Music musica;

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

            ZonaTreino = new Rectangle(3.2f, 2.4f, 0.5f, 0.5f);
            TempoTreino = 0f;
            // Teste flag
            TreinandoForca = false;

            stage = new Stage(new ScreenViewport());
            skin = new Skin(Gdx.files.internal("ui/ui_skin.json"));
            controleAtividadeFisica = new ControleBotao(stage,skin);
            controleAtividadeFisica.criarBotao("", 7, 450, 60f, 2f, new Botao.AcaoBotao(){
                @Override
                public void executar() {
                    controle.Trocar_estado(MENU_PRINCIPAL);
                }
            });
            seta_volta = new Texture("Inventario/arrowLeft.png");

            InputMultiplexer multiplexer = new InputMultiplexer();
            multiplexer.addProcessor(stage);
            multiplexer.addProcessor(boneco);
            Gdx.input.setInputProcessor(multiplexer);

            //Teste musica e mostrar o timer
            musica = Gdx.audio.newMusic(Gdx.files.internal("Musicas/bxx.mp3"));
            musica.setLooping(true);
            musica.setVolume(0.5f);
            musica.play();
        }

        public void render(float delta) {
            game.batch.begin();
            game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
            game.batch.draw(basemap, 0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
            game.batch.draw(seta_volta,0,4.5f,game.viewport.getWorldWidth() /12, game.viewport.getWorldHeight() / 12);
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
            } else{
                TreinandoForca = false;
                TempoTreino = 0f; // reseta o timer
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
        public void hide() {
            if (musica != null) {
                musica.stop(); // teste p parar a musica, aparentemente é isos q tem q chamar
            }
        }
        @Override
        public void dispose() {}
    }
