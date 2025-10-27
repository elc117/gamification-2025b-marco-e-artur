package com.terminalroot.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Botao extends TextButton {

    public Botao(String texto, Skin skin) {
        super(texto, skin);
        estilo();
        comportamento();
    }

    private void estilo() {
        this.pad(10);
        this.getLabel().setFontScale(1.2f);
    }

    private void comportamento() {
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("botao clicado");
            }
        });
    }
}
