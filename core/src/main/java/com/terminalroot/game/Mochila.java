package com.terminalroot.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.w3c.dom.Text;

import static com.terminalroot.game.Controle_Diagrama_Estados.State.MENU_PRINCIPAL;
import static com.terminalroot.game.Main.*;

public class Mochila implements Screen {
    final Main game;
    final Controle_Diagrama_Estados controle;
    Texture background;
    Texture boneco;
    Texture seta_volta;
    // Icone Skins Boneco
    Texture Skinpadrao;
    Texture SkinsolomonkDesbloq;
    Texture SkinsolomonkBloq;
    Texture SkinRaposaDesbloq;
    Texture SkinRaposaBloq;

    // Icone Skins Arma
    Texture SkinArma1forca;
    Texture SkinArma1forcaBloq;
    Texture SkinArma2forca;
    Texture SkinArma2forcaBloq;
    Texture SkinArma3forca;
    Texture SkinArma3forcaBloq;

    Texture SkinArma1int;
    Texture SkinArma1intBloq;
    Texture SkinArma2int;
    Texture SkinArma2intBloq;
    Texture SkinArma3int;
    Texture SkinArma3intBloq;

    // Implementacao botoes
    private Stage stage;
    private Skin skin;
    private ControleBotao controleMochila;

    // Caracteristicas
    private BitmapFont font;

    public Mochila(final Main game, Controle_Diagrama_Estados controle){
        this.game = game;
        this.controle = controle;
    }

    public void show(){
        background = new Texture("Inventario/backgroundinventario.png");
        seta_volta = new Texture("Inventario/arrowLeft.png");
        Skinpadrao = new Texture("Inventario/gaara.png");
        SkinsolomonkDesbloq = new Texture("Inventario/solomonk.png");
        SkinsolomonkBloq = new Texture("Inventario/solomonkBloq.png");
        SkinRaposaDesbloq = new Texture("Inventario/raposa.png");
        SkinRaposaBloq = new Texture("Inventario/raposaCinza.png");
        // Skins arma
        SkinArma1forca = new Texture("Inventario/Armas/Armaforca1.png");
        SkinArma1forcaBloq = new Texture("Inventario/Armas/Armaforca1Cinza.png");
        SkinArma2forca = new Texture("Inventario/Armas/Armaforca2.png");
        SkinArma2forcaBloq = new Texture("Inventario/Armas/Armaforca2Cinza.png");
        SkinArma3forca = new Texture("Inventario/Armas/Armaforca3.png");
        SkinArma3forcaBloq = new Texture("Inventario/Armas/Armaforca3Cinza.png");

        SkinArma1int = new Texture("Inventario/Armas/Armaint1.png");
        SkinArma1intBloq = new Texture("Inventario/Armas/Armaint1Cinza.png");
        SkinArma2int = new Texture("Inventario/Armas/Armaint2.png");
        SkinArma2intBloq = new Texture("Inventario/Armas/Armaint2Cinza.png");
        SkinArma3int = new Texture("Inventario/Armas/Armaint3.png");
        SkinArma3intBloq = new Texture("Inventario/Armas/Armaint3Cinza.png");

        font = new BitmapFont();

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
        //stage.setDebugAll(true);
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("ui/ui_skin.json"));
        controleMochila = new ControleBotao(stage,skin);

        controleMochila.criarBotao("", 7, 450, 60f, 2f, new Botao.AcaoBotao(){
            @Override
            public void executar() {
                controle.Trocar_estado(MENU_PRINCIPAL);
            }
        });

        controleMochila.criarBotao("", 7, 50, 40f, 2f, new Botao.AcaoBotao(){
            @Override
            public void executar() {
                if(boneco != null) {
                    boneco.dispose();
                }
                boneco = new Texture ("hero/Eni/baixo.png");
                SkinBoneco = "SkinBasica";
            }
        });

        controleMochila.criarBotao("", 115, 50, 50f, 2f, new Botao.AcaoBotao(){
            @Override
            public void executar() {
                if(inventario.possuiSkin(ItensBoneco.Skins.SkinSolomonk)){
                    if(boneco != null) {
                        boneco.dispose();
                    }
                    boneco = new Texture ("hero/EniSkin1/Semfundo.png");
                    SkinBoneco = "SkinSolomonk";
                }
            }
        });

        controleMochila.criarBotao("", 205, 50, 50f, 2f, new Botao.AcaoBotao(){
            @Override
            public void executar() {
                if(inventario.possuiSkin(ItensBoneco.Skins.Skinraposa)){
                    if(boneco != null) {
                        boneco.dispose();
                    }
                    boneco = new Texture ("hero/EniSkin2/SemFundo.png");
                    SkinBoneco = "SkinLast";
                }
            }
        });

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;

        Label labelInteligencia = new Label("Inteligência: " + inteligencia, labelStyle);
        labelInteligencia.setPosition(630, 420);
        stage.addActor(labelInteligencia);

        Label labelForca = new Label("Força: " + forca, labelStyle);
        labelForca.setPosition(630, 390);
        stage.addActor(labelForca);
    }

    public void render(float delta){
        ScreenUtils.clear(Color.DARK_GRAY);
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(background,0,0,game.viewport.getWorldWidth(),game.viewport.getWorldHeight());
        game.batch.draw(seta_volta,0,4.5f,game.viewport.getWorldWidth() /12, game.viewport.getWorldHeight() / 12);
        game.batch.draw(Skinpadrao,0,0.3f,game.viewport.getWorldWidth() /12, game.viewport.getWorldHeight() / 12);

        if(inventario.possuiSkin(ItensBoneco.Skins.SkinSolomonk)){
            game.batch.draw(SkinsolomonkDesbloq,1,0.3f,game.viewport.getWorldWidth() / 12, game.viewport.getWorldHeight() / 12);
        } else{
            game.batch.draw(SkinsolomonkBloq,1,0.3f,game.viewport.getWorldWidth() / 12, game.viewport.getWorldHeight() / 12);
        }

        if(inventario.possuiSkin(ItensBoneco.Skins.Skinraposa)){
            game.batch.draw(SkinRaposaDesbloq,2,0.3f,game.viewport.getWorldWidth() / 12, game.viewport.getWorldHeight() / 12);
        } else{
            game.batch.draw(SkinRaposaBloq,2,0.3f,game.viewport.getWorldWidth() / 12, game.viewport.getWorldHeight() / 12);
        }

        if(inventario.possuiArma(ItensBoneco.Arma.FORCA_1)){
            game.batch.draw(SkinArma1forca,0.5f,4,game.viewport.getWorldWidth() / 11, game.viewport.getWorldHeight() / 11);
        } else{
            game.batch.draw(SkinArma1forcaBloq,0.5f,4,game.viewport.getWorldWidth() / 11, game.viewport.getWorldHeight() / 11);
        }

        if(inventario.possuiArma(ItensBoneco.Arma.FORCA_2)){
            game.batch.draw(SkinArma2forca,1.5f,4,game.viewport.getWorldWidth() / 11, game.viewport.getWorldHeight() / 11);
        } else{
            game.batch.draw(SkinArma2forcaBloq,1.5f,4,game.viewport.getWorldWidth() / 11, game.viewport.getWorldHeight() / 11);
        }

        if(inventario.possuiArma(ItensBoneco.Arma.FORCA_3)){
            game.batch.draw(SkinArma3forca,2.5f,4,game.viewport.getWorldWidth() / 11, game.viewport.getWorldHeight() / 11);
        } else{
            game.batch.draw(SkinArma3forcaBloq,2.5f,4,game.viewport.getWorldWidth() / 11, game.viewport.getWorldHeight() / 11);
        }

        if(inventario.possuiArma(ItensBoneco.Arma.INT_1)){
            game.batch.draw(SkinArma1int,0.5f,3,game.viewport.getWorldWidth() / 11, game.viewport.getWorldHeight() / 11);
        } else{
            game.batch.draw(SkinArma1intBloq,0.5f,3,game.viewport.getWorldWidth() / 11, game.viewport.getWorldHeight() / 11);
        }

        if(inventario.possuiArma(ItensBoneco.Arma.INT_2)){
            game.batch.draw(SkinArma2int,1.5f,3,game.viewport.getWorldWidth() / 11, game.viewport.getWorldHeight() / 11);
        } else{
            game.batch.draw(SkinArma2intBloq,1.5f,3,game.viewport.getWorldWidth() / 11, game.viewport.getWorldHeight() / 11);
        }

        if(inventario.possuiArma(ItensBoneco.Arma.INT_3)){
            game.batch.draw(SkinArma3int,2.5f,3,game.viewport.getWorldWidth() / 11, game.viewport.getWorldHeight() / 11);
        } else{
            game.batch.draw(SkinArma3intBloq,2.5f,3,game.viewport.getWorldWidth() / 11, game.viewport.getWorldHeight() / 11);
        }

        game.batch.draw(boneco, 3f, 0.7f, game.viewport.getWorldWidth() / 2, game.viewport.getWorldHeight() / 2);
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
