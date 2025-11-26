package com.terminalroot.game;

import java.util.EnumSet;

public class ItensInventario {
    private EnumSet<ItensBoneco.Arma> armasDesbloqueadas = EnumSet.noneOf(ItensBoneco.Arma.class);
    private EnumSet<ItensBoneco.Skins> skinsDesbloqueadas = EnumSet.noneOf(ItensBoneco.Skins.class);

    public void desbloquearArma(ItensBoneco.Arma arma) {
        armasDesbloqueadas.add(arma);
    }

    public boolean possuiArma(ItensBoneco.Arma arma) {
        return armasDesbloqueadas.contains(arma);
    }

    public void desbloquearSkin(ItensBoneco.Skins skins) {
        skinsDesbloqueadas.add(skins);
    }

    public boolean possuiSkin(ItensBoneco.Skins skins) {
        return skinsDesbloqueadas.contains(skins);
    }
}

