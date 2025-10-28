package com.terminalroot.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Botao extends TextButton {
    public interface AcaoBotao {
        void executar();
    }

    public Botao(String texto, Skin skin, final AcaoBotao acao) {
        super(texto, skin);

        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (acao != null) {
                    acao.executar();
                }
            }
        });
    }
}
