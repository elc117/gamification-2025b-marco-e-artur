package com.terminalroot.game;

public class BancoMonstros {

    private static final int[] HP_POR_FASE =   {30, 50, 75, 100, 130, 165, 200, 240, 285, 350}; // hp dos monstros por fase
    private static final int[] DANO_POR_FASE = { 3,  5,  7,  10,  12,  15,  18,  22,  26,  30}; // dano dos monstros por fase, tabelados

    // frames de cada animação 
    
    private static final int[][] FRAMES_ANJOS = {
        {4, 28, 5, 17}, 
        {8, 11, 5, 16}, 
        {8, 12, 7, 49}, 
        {8, 13, 5, 16},
        {12, 7, 5, 34},
        {8, 13, 5, 24},
        {6, 9, 5, 9}, 
        {6, 9, 5, 14}, 
        {6, 10, 5, 20}, 
        {6, 8, 5, 13}  
    };
    

    private static final int[][] FRAMES_DEMONIOS = {
        {8, 11, 4, 12},
        {20, 13, 5, 20}, 
        {10, 8, 5, 11}, 
        {7, 14, 4, 19}, 
        {8, 12, 5, 16}, 
        {10, 12, 7, 25}, 
        {6, 8, 4, 7}, 
        {12, 19, 8, 17}, 
        {8, 8, 6, 5}, 
        {8, 14, 6, 12} 
    };

    public static Monstro carregarMonstro(boolean isAnjo, int fase) {
        // protecao de indice
        if (fase < 1) fase = 1;
        if (fase > 10) fase = 10;
        int index = fase - 1;

        int hp = HP_POR_FASE[index];
        int dano = DANO_POR_FASE[index];
        
        String nomeMonstro;
        String pastaBase;
        int[] configFrames; // array de frames

        if (isAnjo) {
            // jogador anjo luta contra demonios
            nomeMonstro = "Inimigo Infernal " + fase; // nome
            pastaBase = "monstros/demonios/fase" + fase; // local da textura
            configFrames = FRAMES_DEMONIOS[index];
        } else {
            // jogaddor demonio luta contra anjos
            nomeMonstro = "Inimigo Sagrado " + fase; // nome
            pastaBase = "monstros/anjos/fase" + fase; // local da textura
            configFrames = FRAMES_ANJOS[index];
        }

        // constroi o monstro
        return new Monstro(nomeMonstro, hp, dano, pastaBase, configFrames);
    }
}