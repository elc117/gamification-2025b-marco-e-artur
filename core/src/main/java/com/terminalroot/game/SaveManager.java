package com.terminalroot.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import java.util.Date;
import java.util.Calendar;

public class SaveManager {
    private static final String PREFS_NAME = "meu_jogo_save";
    private Preferences prefs;

    // Singleton
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

    // ===== SAVE - STATS E SKIN =====

    public void salvarStats(int forca, int inteligencia) {
        prefs.putInteger("forca", forca);
        prefs.putInteger("inteligencia", inteligencia);
        prefs.flush();
        Gdx.app.log("SaveManager", "Stats salvos: Força=" + forca + ", Inteligência=" + inteligencia);
    }

    public void salvarSkin(String skinBoneco) {
        prefs.putString("skin_boneco", skinBoneco);
        prefs.flush();
        Gdx.app.log("SaveManager", "Skin salva: " + skinBoneco);
    }

    public void salvarTudo(int forca, int inteligencia, String skinBoneco) {
        prefs.putInteger("forca", forca);
        prefs.putInteger("inteligencia", inteligencia);
        prefs.putString("skin_boneco", skinBoneco);
        prefs.flush();
        Gdx.app.log("SaveManager", "Dados completos salvos");
    }

    public void iniciarContadorDias() {
        long dataAtual = System.currentTimeMillis();
        prefs.putLong("data_inicio", dataAtual);
        prefs.putInteger("dia_manual", 1); // Dia manual para progressão por fases
        prefs.flush();
        Gdx.app.log("SaveManager", "Contador de dias iniciado em: " + new Date(dataAtual));
    }

    private int calcularDiasReaisPassados() {
        if (!prefs.contains("data_inicio")) {
            return 0;
        }

        long dataInicio = prefs.getLong("data_inicio", 0);
        long dataAtual = System.currentTimeMillis();

        long diferencaMs = dataAtual - dataInicio;

        int diasPassados = (int) (diferencaMs / 86400000L); // Converte pra dias

        return diasPassados;
    }

    public int getDiaAtual() {
        int diaManual = prefs.getInteger("dia_manual", 1);
        int diasReais = calcularDiasReaisPassados();

        int diaCalculado = Math.max(diaManual, diasReais + 1);

        return Math.min(diaCalculado, 10);
    }

    public void avancarDia() {
        int diaManual = prefs.getInteger("dia_manual", 1);
        if (diaManual < 10) {
            prefs.putInteger("dia_manual", diaManual + 1);
            prefs.flush();
            Gdx.app.log("SaveManager", "Avançou para o dia " + (diaManual + 1));
        } else {
            Gdx.app.log("SaveManager", "Já está no último dia (10)");
        }
    }

    // ===== LOAD - STATS E SKIN =====

    public int getForca() {
        return prefs.getInteger("forca", 5);
    }

    public int getInteligencia() {
        return prefs.getInteger("inteligencia", 5);
    }

    public String getSkinBoneco() {
        return prefs.getString("skin_boneco", "SkinSolomonk");
    }

    // ===== LOAD - PROGRESSO =====

    public int getNivelAtual() {
        return prefs.getInteger("nivel_atual", 1);
    }

    public String getInventario() {
        return prefs.getString("inventario", "{}");
    }

    public boolean existeSave() {
        return prefs.contains("forca") || prefs.contains("nivel_atual");
    }

    public void resetarStats() {
        prefs.putInteger("forca", 5);
        prefs.putInteger("inteligencia", 5);
        prefs.putString("skin_boneco", "SkinSolomonk");
        prefs.flush();
        Gdx.app.log("SaveManager", "Stats resetados para valores padrão");
    }
}
