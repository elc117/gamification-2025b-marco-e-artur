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
    public static String SkinBoneco = "SkinSolomonk";
    public static int inteligencia = 5;
    public static int forca = 5;
    // tentar trazer todo o manejo de skins do inventario pra cá
    public static ItensInventario inventario = new ItensInventario();

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        viewport = new FillViewport(8, 5);
        controle = new Controle_Diagrama_Estados(this);

        // Teste utilizar o Cache browser
        carregarDados();

        font.setUseIntegerPositions(false);
        font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());
    }

    // Teste cache browser
    private void carregarDados(){
        SaveManager save = SaveManager.getInstance();

        forca = save.getForca();
        inteligencia = save.getInteligencia();
        SkinBoneco = save.getSkinBoneco();

        // DEBUG RETIRAR DEPOIS
        System.out.println("=== Dados Carregados ===");
        System.out.println("Força: " + forca);
        System.out.println("Inteligência: " + inteligencia);
        System.out.println("Skin: " + SkinBoneco);
    }

    public void salvarDados(){
        SaveManager save = SaveManager.getInstance();
        save.salvarTudo(forca, inteligencia, SkinBoneco);
    }

    public void resetarDados(){
        forca = 5;
        inteligencia = 5;
        SkinBoneco = "SkinBasica";
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
