    package com.terminalroot.game;

    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.Screen;
    import com.badlogic.gdx.graphics.Color;
    import com.badlogic.gdx.graphics.Texture;
    import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
    import com.badlogic.gdx.scenes.scene2d.Stage;
    import com.badlogic.gdx.scenes.scene2d.ui.Skin;
    import com.badlogic.gdx.utils.ScreenUtils;
    import com.badlogic.gdx.utils.viewport.ScreenViewport;
    import com.badlogic.gdx.graphics.g2d.BitmapFont;


    public class Batalha implements Screen {
        final Main game;
        final Controle_Diagrama_Estados controle;

        private Texture fundo;
        private Monstro monstro;
        private Usuario jogador;

        private boolean turnoJogador = true;
        private float tempoEspera = 0f;
        private boolean aguardandoAnimacao = false;
        private enum FaseMonstro { INDO, ATACANDO, VOLTANDO, PARADO }
        private FaseMonstro faseMonstro = FaseMonstro.PARADO;
        private float tempoFase = 0f;

        private Stage stage;
        private Skin skin;

        private ShapeRenderer shapeRenderer;
        private BitmapFont font;

        public Batalha(final Main game, Controle_Diagrama_Estados controle){
            this.game = game;
            this.controle = controle;
        }

        private void addBotao(String texto, float x, float y, int dano) {
            Botao botao = new Botao(texto, skin, () -> {
                if (!turnoJogador || aguardandoAnimacao) return;
                
                // jogador ataca
                monstro.TOMA_DANO(dano);
                System.out.println(texto + " → dano " + dano + " | HP Monstro: " + monstro.getHP());
                
                // verifica se o monstro morreu
                if (!monstro.ESTADO()) {
                    System.out.println("Vitória! Monstro derrotado!");
                    game.avancafase();
                    controle.Trocar_estado(Controle_Diagrama_Estados.State.MENU_PRINCIPAL);
                    return;
                }
                
                // passa o turno para o monstro
                turnoJogador = false;
                aguardandoAnimacao = true;
                tempoEspera = 2f; 
            });

            
            botao.setPosition(x, y);
            botao.setSize(150f, 50f); // tamanho do botão

            // deixa invisivel
            botao.getStyle().up = null;
            botao.getStyle().down = null;
            botao.getStyle().over = null;
            botao.getStyle().fontColor = new Color(0, 0, 0, 0); 
            

            stage.addActor(botao);
        }


        // em fase de criação

        private void desenharBarraHP(float x, float y, float largura, float altura, 
                                    int hpAtual, int hpMaximo, Color corBarra) {


            float porcentagem = (float) hpAtual / hpMaximo;
            
            // fundo da barra
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(x, y, largura, altura);
            
            // barra de hp
            shapeRenderer.setColor(corBarra);
            shapeRenderer.rect(x, y, largura * porcentagem, altura);
            
            // bordas
            shapeRenderer.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.rect(x, y, largura, altura);
            shapeRenderer.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        }

        public void show(){
            fundo = new Texture("batalha.png"); // campo de batalha

            jogador = new Usuario( "boneco.png"); // criação do personagem
            jogador.setPosicao(1f, 1.6f); // posição do personagem
            jogador.setTamanho(1.5f, 2f); // tamanho do personagem

            monstro = BancoMonstros.carregarMonstro(game.isEAnjo(), game.getFaseAtual()); // carrega o monstro com base na fase

            monstro.setPosicao(5.5f, 2f); // posição do monstro
            monstro.setTamanho(1f, 1f); //  tamanho do monstro

            stage = new Stage(new ScreenViewport()); 
            skin = new Skin(Gdx.files.internal("ui/ui_skin.json"));

            criarBotoes();
            Gdx.input.setInputProcessor(stage);
        }

        private void criarBotoes() {
            float baseY = 60f;  // Altura base dos botões
            float espacamento = 160f; // Espaçamento entre botões
            float basex = (Gdx.graphics.getWidth() / 2f) - 60;
            
            // ira de thor
            addBotao("", basex, baseY, (jogador.getFORCA() + jogador.getINTELIGENCIA()) * 2);
            
            // ataque basico
            addBotao("", basex + espacamento + 80, baseY, (int)(jogador.getFORCA() * 2));
            
            // meteoro
            addBotao("", basex , baseY - 50, (int)(jogador.getINTELIGENCIA() * 2));
        }

        public void render(float delta){

        ScreenUtils.clear(Color.BLACK);

        // atualiza animações
        monstro.update(delta);
        
        // sistema de turnos
        if (!turnoJogador && aguardandoAnimacao) { // não é turno do jogador e está aguardando animação do monstro
            tempoEspera -= delta; // diminui o tempo de espera inicial antes do monstro começar a agir 
            
            // quando acabar o tempo de espera e o monstro estiver parado, inicia o movimento do monstro ao jogador
            if (tempoEspera <= 0 && faseMonstro == FaseMonstro.PARADO) {
                faseMonstro = FaseMonstro.INDO;
                tempoFase = 0f; // reinicia o cronometro
            }
            
            // se o monstro estiver em alguma fase sem ser a de parado
            if (faseMonstro != FaseMonstro.PARADO) {
                tempoFase += delta; // soma o tempo decorrido na fase atual
                
                switch (faseMonstro) {
                    case INDO: // monstro anda até o jogodar
                        if (tempoFase < 1f) {
                            float progresso = tempoFase / 1f;
                            float novaX = 5.5f - (5.5f - 1.5f) * progresso;
                            monstro.setPosicao(novaX, 2f); // nova posição do monstro
                        } else {
                            monstro.setPosicao(1.5f, 2f); // ja está na posição do jogador
                            faseMonstro = FaseMonstro.ATACANDO; // troca a fase para atacar
                            tempoFase = 0f;
                            monstro.atacar(); // inicia a animação de ataque
                        }
                        break;
                        
                    case ATACANDO:  
                        // espera a animação do ataque terminar
                        if (monstro.animacaoAtaqueTerminou()) {
                            // toma o dano do jogador
                            int dano = monstro.getDANO();
                            jogador.TOMA_DANO(dano);
                            
                            faseMonstro = FaseMonstro.VOLTANDO; // muda a fase para voltar a posição original
                            tempoFase = 0f;
                        }
                        break;
                        
                    case VOLTANDO:
                        // volta para posição original 
                        if (tempoFase < 1f) { // movimentação suave para voltar
                            float progresso = tempoFase / 1f;
                            float novaX = 1.5f + (5.5f - 1.5f) * progresso;
                            monstro.setPosicao(novaX, 2f);
                        } else { // terminou de voltar
                            monstro.setPosicao(5.5f, 2f); // encerra a fase do monstro
                            faseMonstro = FaseMonstro.PARADO;
                            
                            // verifica se o jogador morreu
                            if (!jogador.ESTADO()) {
                                System.out.println("Game Over! Você foi derrotado!");
                                controle.Trocar_estado(Controle_Diagrama_Estados.State.MENU_PRINCIPAL);
                                return;
                            }
                            
                            // passa o turno para o jogador
                            aguardandoAnimacao = false;
                            turnoJogador = true;
                        }
                        break;
                    case PARADO:
                        // não faz nada
                        break;

                    default:
                    break;
                }
            }
        }

        // Desenha tudo
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();

        game.batch.draw(fundo, 0, 0,
            game.viewport.getWorldWidth(),
            game.viewport.getWorldHeight()
        );
        // desnha o jogador e o monstro
        jogador.draw(game.batch);
        monstro.draw(game.batch, monstro.getX(), monstro.getY(), monstro.getW(), monstro.getH());
        
        game.batch.end();

        stage.act(delta);
        stage.draw();

        }

        @Override public void resize(int w, int h) {
            game.viewport.update(w, h, true);
            stage.getViewport().update(w, h, true);
        }

        @Override public void hide() { 
            Gdx.input.setInputProcessor(null); 
        }

        @Override public void dispose() {
            if (fundo != null) fundo.dispose();
            if (jogador != null) jogador.dispose();
            if (stage != null) stage.dispose();
            if (skin != null) skin.dispose();
            if (monstro != null) monstro.dispose();
        }

        @Override public void pause() {}
        @Override public void resume() {}
    }
