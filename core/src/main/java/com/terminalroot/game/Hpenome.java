package com.terminalroot.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;

public class Hpenome implements Disposable {
    private final Main game;
    private final ShapeRenderer shapeRenderer;

    // Cores estilo Pokemon (RGB normalizado 0-1)
    private final Color COR_BORDA = Color.valueOf("3f4e2b");
    private final Color COR_FUNDO_BARRA = Color.valueOf("555b5b"); // Cinza escuro
    private final Color COR_VIDA_ALTA = Color.valueOf("70f8a8");  // Verde claro
    private final Color COR_VIDA_MEDIA = Color.valueOf("f1b61e");  // Amarelo
    private final Color COR_VIDA_BAIXA = Color.valueOf("c36d4a");  // Vermelho

    public Hpenome(Main game) {
        this.game = game;
        this.shapeRenderer = new ShapeRenderer();
    }

    public void render(Usuario jogador, Monstro monstro) {
        if (jogador == null || monstro == null) return;

        shapeRenderer.setProjectionMatrix(game.viewport.getCamera().combined);
        
        // desenho das barras
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // barra de hp do monstro
        float mX = 5f;
        float mY = 1.45f;
        float mW = 1.66f;
        float mH = 0.10f; 

        desenharBarraEstilizada(mX, mY, mW, mH, monstro.getHP(), monstro.getHP_MAXIMO());

        // barra de hp do jogador
        float jX = 0.53f; 
        float jY = 4.1f;
        float jW = 1.66f; 
        float jH = 0.10f;

        desenharBarraEstilizada(jX, jY, jW, jH, jogador.getHP(), jogador.getHP_MAXIMO());

        shapeRenderer.end();

        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();

        game.font.getData().setScale(0.015f); // tamanho do texto
        
        // texto do monstro
        
        game.font.setColor(Color.valueOf("484444")); // cor do texto
        game.font.draw(game.batch, monstro.getNOME(), mX, mY + mH + 0.3f); // desenha nome do monstro
        
        // hp do monstro
        game.font.getData().setScale(0.015f);
        game.font.draw(game.batch, monstro.getHP() + "/" + monstro.getHP_MAXIMO(), mX + mW + 0.05f,  1.60f);

        // texto do jogador
        game.font.getData().setScale(0.015f);

        game.font.setColor(Color.valueOf("484444")); // cor do texto
        game.font.draw(game.batch, "Heroi", jX, jY + jH + 0.3f);

        // hp do jogador
        game.font.draw(game.batch, jogador.getHP() + "/" + jogador.getHP_MAXIMO(), jX + jW + 0.05f, 4.25f);

        // stamina do jogador

        game.font.getData().setScale(0.010f);
        game.font.setColor(Color.BLACK);
        game.font.draw(game.batch, "Stamina: " + jogador.getSTAMINA() + "/" + jogador.getSTAMINA_MAXIMA(), jX, jY - 0.2f);

        game.batch.end();
    }

   
    private void desenharBarraEstilizada(float x, float y, float w, float h, int hpAtual, int hpMax) {
        // espessura da borda 
        float borda = 0.04f; 

        // desenha a borda
        shapeRenderer.setColor(COR_BORDA);
        shapeRenderer.rect(x - borda, y - borda, w + (borda*2), h + (borda*2));

        // desenha o fundo da barra
        shapeRenderer.setColor(COR_FUNDO_BARRA);
        shapeRenderer.rect(x, y, w, h);

        // calcula a porcentagem de vida
        float porcentagem = (float) hpAtual / hpMax;
        if (porcentagem < 0) porcentagem = 0;

        // define a cor baseada na porcentagem de vida
        if (porcentagem > 0.5f) {
            shapeRenderer.setColor(COR_VIDA_ALTA); // verde = alta
        } else if (porcentagem > 0.2f) {
            shapeRenderer.setColor(COR_VIDA_MEDIA); // amarelo = medio
        } else {
            shapeRenderer.setColor(COR_VIDA_BAIXA); // vermelho = baixa
        }

        // desenha a vida
        shapeRenderer.rect(x, y, w * porcentagem, h);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}