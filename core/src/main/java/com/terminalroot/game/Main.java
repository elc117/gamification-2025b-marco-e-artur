package com.terminalroot.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;

public class Main extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public FillViewport viewport;
    public Controle_Diagrama_Estados controle;

    // Skin do boneco, variavel global
    public static String SkinBoneco = "SkinBasica";
    public static int inteligencia = 5;
    public static int forca = 5;
    // tentar trazer todo o manejo de skins do inventario pra cá
    public static ItensInventario inventario = new ItensInventario();

    public Usuario jogador;

    // true = anjos, false = demonios
    public boolean eanjo = true;

    // controle fase
    public int fase_atual = 1;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        viewport = new FillViewport(8, 5);
        controle = new Controle_Diagrama_Estados(this);

        carregarDados();

        font.setUseIntegerPositions(false);
        font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());
    }

    // Teste cache browser
    private void carregarDados(){
        SaveManager save = SaveManager.getInstance();

        // Verifica se existe save anterior
        if (save.existeSave()) {
            forca = save.getForca();
            inteligencia = save.getInteligencia();
            SkinBoneco = save.getSkinBoneco();

            Gdx.app.log("Main", "=== Dados Carregados ===");
            Gdx.app.log("Main", "Força: " + forca);
            Gdx.app.log("Main", "Inteligência: " + inteligencia);
            Gdx.app.log("Main", "Skin: " + SkinBoneco);
        } else {
            Gdx.app.log("Main", "Nenhum save encontrado. Usando valores padrão.");
            // Salva os valores padrão
            salvarDados();
        }
    }

    public void salvarDados() {
        SaveManager save = SaveManager.getInstance();
        save.salvarTudo(forca, inteligencia, SkinBoneco);
        Gdx.app.log("Main", "Dados salvos com sucesso!");
    }

    public static void setForca(int novaForca) {
        forca = novaForca;
        SaveManager.getInstance().salvarStats(forca, inteligencia);
    }

     // Atualiza apenas a inteligência e salva
    public static void setInteligencia(int novaInteligencia) {
        inteligencia = novaInteligencia;
        SaveManager.getInstance().salvarStats(forca, inteligencia);
    }

    public void resetarDados(){
        forca = 5;
        inteligencia = 5;
        SkinBoneco = "SkinBasica";
    }

    public void avancafase(){
        fase_atual++;
    }

    public int getFaseAtual(){
        return fase_atual;
    }

    public boolean isEAnjo(){
        return eanjo;   
    }


    @Override
    public void render() {
        super.render(); //  atualiza e desenha a tela atual
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
