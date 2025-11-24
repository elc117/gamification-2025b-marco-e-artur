package com.terminalroot.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Gerenciador de save do jogo usando LocalStorage (via Preferences)
 * Adaptado para o jogo com stats (força, inteligência) e skin
 */
public class SaveManager {
    private static final String PREFS_NAME = "meu_jogo_save";
    private Preferences prefs;

    // Singleton para acesso global
    private static SaveManager instance;

    private SaveManager() {
        prefs = Gdx.app.getPreferences(PREFS_NAME);
    }

    public static SaveManager getInstance() {
        if (instance == null) {
            instance = new SaveManager();
        }
        return instance;
    }

    // ===== MÉTODOS DE SAVE - ADAPTADO PARA SEU JOGO =====

    public void salvarStats(int forca, int inteligencia) {
        prefs.putInteger("forca", forca);
        prefs.putInteger("inteligencia", inteligencia);
        prefs.flush();
    }

    public void salvarSkin(String skinBoneco) {
        prefs.putString("skin_boneco", skinBoneco);
        prefs.flush();
    }

    public void salvarTudo(int forca, int inteligencia, String skinBoneco) {
        prefs.putInteger("forca", forca);
        prefs.putInteger("inteligencia", inteligencia);
        prefs.putString("skin_boneco", skinBoneco);
        prefs.flush();
    }

    public void salvarProgresso(int nivel, int pontuacao, int vidas) {
        prefs.putInteger("nivel_atual", nivel);
        prefs.putInteger("pontuacao_total", pontuacao);
        prefs.putInteger("vidas", vidas);
        prefs.flush();
    }

    public void salvarConfiguracoes(float volumeMusica, float volumeSFX, boolean fullscreen) {
        prefs.putFloat("volume_musica", volumeMusica);
        prefs.putFloat("volume_sfx", volumeSFX);
        prefs.putBoolean("fullscreen", fullscreen);
        prefs.flush();
    }

    public void salvarMelhorPontuacao(int pontuacao) {
        int melhorAtual = getMelhorPontuacao();
        if (pontuacao > melhorAtual) {
            prefs.putInteger("melhor_pontuacao", pontuacao);
            prefs.flush();
        }
    }

    // Para dados complexos, você pode usar JSON
    public void salvarInventario(String jsonInventario) {
        prefs.putString("inventario", jsonInventario);
        prefs.flush();
    }

    // ===== MÉTODOS DE LOAD - ADAPTADO PARA SEU JOGO =====

    public int getForca() {
        return prefs.getInteger("forca", 5); // Valor padrão: 5
    }

    public int getInteligencia() {
        return prefs.getInteger("inteligencia", 5); // Valor padrão: 5
    }

    public String getSkinBoneco() {
        return prefs.getString("skin_boneco", "SkinSolomonk"); // Skin padrão
    }

    public int getNivelAtual() {
        return prefs.getInteger("nivel_atual", 1);
    }

    public int getPontuacaoTotal() {
        return prefs.getInteger("pontuacao_total", 0);
    }

    public int getVidas() {
        return prefs.getInteger("vidas", 3); // 3 vidas padrão
    }

    public float getVolumeMusica() {
        return prefs.getFloat("volume_musica", 0.7f);
    }

    public float getVolumeSFX() {
        return prefs.getFloat("volume_sfx", 1.0f);
    }

    public boolean isFullscreen() {
        return prefs.getBoolean("fullscreen", false);
    }

    public int getMelhorPontuacao() {
        return prefs.getInteger("melhor_pontuacao", 0);
    }

    public String getInventario() {
        return prefs.getString("inventario", "{}"); // JSON vazio padrão
    }

    // ===== UTILITÁRIOS =====

    public boolean existeSave() {
        return prefs.contains("nivel_atual");
    }

    public void resetarProgresso() {
        prefs.remove("nivel_atual");
        prefs.remove("pontuacao_total");
        prefs.remove("vidas");
        prefs.remove("inventario");
        prefs.flush();
    }

    public void apagarTudo() {
        prefs.clear();
        prefs.flush();
    }
}

// ===== EXEMPLO DE USO COM SEU MAIN.JAVA =====

/*
package com.terminalroot.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;

public class Main extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public FillViewport viewport;
    public Controle_Diagrama_Estados controle;

    // Variáveis globais que serão salvas
    public static String SkinBoneco = "SkinSolomonk";
    public static int inteligencia = 5;
    public static int forca = 5;
    public static ItensInventario inventario = new ItensInventario();

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        viewport = new FillViewport(8, 5);

        // CARREGAR DADOS SALVOS AQUI!
        carregarDados();

        controle = new Controle_Diagrama_Estados(this);

        font.setUseIntegerPositions(false);
        font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());
    }

    // Método para carregar os dados salvos
    private void carregarDados() {
        SaveManager save = SaveManager.getInstance();

        // Restaurar os valores do cache
        forca = save.getForca();
        inteligencia = save.getInteligencia();
        SkinBoneco = save.getSkinBoneco();

        System.out.println("Dados carregados: Força=" + forca +
                          ", Inteligência=" + inteligencia +
                          ", Skin=" + SkinBoneco);
    }

    // Método para salvar os dados (chame quando necessário)
    public void salvarDados() {
        SaveManager save = SaveManager.getInstance();
        save.salvarTudo(forca, inteligencia, SkinBoneco);
        System.out.println("Dados salvos!");
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        // Salvar antes de fechar o jogo
        salvarDados();

        batch.dispose();
        font.dispose();
    }
}

// ===== QUANDO SALVAR? =====

// 1. Quando o jogador aumenta os stats:
Main.forca += 1;
((Main) Gdx.app.getApplicationListener()).salvarDados();

// 2. Quando troca de skin:
Main.SkinBoneco = "NovaSkind";
((Main) Gdx.app.getApplicationListener()).salvarDados();

// 3. Automático ao fechar (já está no dispose)

// 4. Em uma tela de pausa/menu:
SaveManager.getInstance().salvarStats(Main.forca, Main.inteligencia);

*/

// Para salvar objetos complexos, use JSON (com biblioteca como Gson):
/*
import com.google.gson.Gson;

Gson gson = new Gson();
Inventario inv = new Inventario();
inv.adicionarItem("Espada", 1);
inv.adicionarItem("Poção", 5);

String json = gson.toJson(inv);
SaveManager.getInstance().salvarInventario(json);

// Carregar:
String json = SaveManager.getInstance().getInventario();
Inventario inv = gson.fromJson(json, Inventario.class);
*/
