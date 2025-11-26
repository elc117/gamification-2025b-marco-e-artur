package com.terminalroot.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import static com.terminalroot.game.Main.moedas;

public class Batalha implements Screen {
    final Main game;
    final Controle_Diagrama_Estados controle;

    private Texture fundo;
    private Monstro monstro;
    private Usuario jogador;

    private boolean turnoJogador = true;
    private float tempoEspera = 0f;
    private boolean aguardandoAnimacao = false;

    private enum FaseMonstro {
        INDO, ATACANDO, VOLTANDO, PARADO
    }

    private FaseMonstro faseMonstro = FaseMonstro.PARADO;
    private float tempoFase = 0f;

    private Animation<TextureRegion> efeitoirathor, efeitometeoro;
    private Texture texirathor = new Texture("efeitos/ira_thor.png");
    private Texture texmeteoro = new Texture("efeitos/meteoro.png");
    private float stateTimeEfeito;
    private boolean efeitoAtivo;
    private float efeitoX, efeitoY, efeitoW, efeitoH;

    private Animation<TextureRegion> efeitoAtual;

    private Hpenome hud;

    private Stage stage;
    private Skin skin;

    private Music musica;

    public Batalha(final Main game, Controle_Diagrama_Estados controle) {
        this.game = game;
        this.controle = controle;

    }

    private void addBotao(String texto, float x, float y, int dano, Animation<TextureRegion> animEfeito,
            int custoStamina, boolean ehMagia) {
        Botao botao = new Botao(texto, skin, () -> {
            if (!turnoJogador || aguardandoAnimacao) // verifica se é o turno do jogador ou se alguma animação está
                                                     // acontecendo
                return;
            if (!jogador.temStamina(custoStamina)) { // verifica se tem stamina
                System.out.println("Sem energia suficiente!");
                return; // cancela o ataque
            }
            jogador.gastaStamina(custoStamina); // recupera a stamina

            if (ehMagia) {
                jogador.castar(); // animação de cast para magias
            } else {
                jogador.atacar(); // animação de ataque físico
            }

            // Ativa o efeito
            efeitoW = 4f;
            efeitoH = 4f;
            efeitoX = monstro.getX() + monstro.getW() / 2f - efeitoW / 2f;
            efeitoY = monstro.getY() * 0.60f;

            efeitoAtual = animEfeito; // define a animação que vai ocorrer
            efeitoAtivo = true; // flag para ativar o efeito
            stateTimeEfeito = 0f; // reseta o tempo ded animação

            monstro.TOMA_DANO(dano); // aplica o dano ao monstro
            System.out.println(texto + " → dano " + dano + " | HP Monstro: " + monstro.getHP()); // debug

            if (!monstro.ESTADO()) { // verifica se o monstro morreu
                System.out.println("Vitória! Monstro derrotado!"); 

                aguardandoAnimacao = true;
                tempoEspera = 2f; // tempo para ver a "morte" do monstro (ajuste conforme necessário)
                turnoJogador = false;

                return;
            }

            turnoJogador = false; // passa o turno para o monstro
            aguardandoAnimacao = true; // sinaliza que esta aguardando a animação do monstro
            tempoEspera = 2f; // tempo de espera antes do monstro agir
        });

        botao.setPosition(x, y); // escolhe o lugar do botão
        botao.setSize(150f, 50f); // escolhe o tamanho do botão
        // deixa os botões invisiveis
        if (botao.getText().toString().isEmpty()) { // se o texto do botão for "", deixa invisivel
            botao.getStyle().up = null;
            botao.getStyle().down = null;
            botao.getStyle().over = null;
        }
        stage.addActor(botao);
    }

    private Animation<TextureRegion> criarAnimacaoEfeito(Texture tex, int colunas, int linhas, float tempo) {

        int frameWidth = tex.getWidth() / colunas;
        int frameHeight = tex.getHeight() / linhas;

        TextureRegion[][] tmp = TextureRegion.split(tex, frameWidth, frameHeight);

        TextureRegion[] frames = new TextureRegion[colunas * linhas];
        int index = 0;

        for (int i = 0; i < linhas; i++) { // igual o do usuario
            for (int j = 0; j < colunas; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        Animation<TextureRegion> anim = new Animation<>(tempo, frames);
        anim.setPlayMode(Animation.PlayMode.NORMAL);

        return anim;
    }

    public void show() {
        fundo = new Texture("batalha.png"); // campo de batalha

        // Teste musica
        musica = Gdx.audio.newMusic(Gdx.files.internal("Musicas/Musicabattle.mp3"));
        musica.setLooping(true);
        musica.setVolume(0.5f);
        musica.play();

        int[] colunas = {4, 4, 4};  // idle, ataque, cast
        int[] linhas  = {6, 8, 6};  // idle, ataque, cast
        jogador = new Usuario("hero/Eni", colunas, linhas); // criação do personagem
        jogador.setPosicao(0.4f, 0.6f); // posição do personagem
        jogador.setTamanho(3f, 3.5f); // tamanho do personagem

        monstro = BancoMonstros.carregarMonstro(game.isEAnjo(), SaveManager.getInstance().getDiaAtual()); // carrega o monstro com base na
                                                                                      // fase
        hud = new Hpenome(game);

        game.font.getData().setScale(0.02f);
        game.font.setUseIntegerPositions(false);

        monstro.setPosicao(5.5f, 2f); // posição do monstro
        monstro.setTamanho(1f, 1f); // tamanho do monstro

        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("ui/ui_skin.json"));

        efeitoirathor = criarAnimacaoEfeito(texirathor, 10, 6, 0.02f);
        efeitometeoro = criarAnimacaoEfeito(texmeteoro, 11, 1, 0.03f);

        efeitoAtivo = false;

        criarBotoes();
        Gdx.input.setInputProcessor(stage);
    }

    private void criarBotoes() {
        float baseY = 60f;
        float espacamento = 160f;
        float basex = (Gdx.graphics.getWidth() / 2f) - 60;

        addBotao("", basex, baseY, (jogador.getFORCA() + jogador.getINTELIGENCIA()) * 2, efeitoirathor, 60,true);
        addBotao("", basex + espacamento + 80, baseY, (int) (jogador.getFORCA() * 2), null, 10,false);
        addBotao("", basex, baseY - 50, (int) (jogador.getINTELIGENCIA() * 2), efeitometeoro, 20,true);

        Botao btnVoltar = new Botao("", skin, () -> {
            controle.Trocar_estado(Controle_Diagrama_Estados.State.MENU_PRINCIPAL);
        });

        btnVoltar.setPosition(0, 0);
        btnVoltar.setSize(100, 50);
        stage.addActor(btnVoltar);
    }

    public void render(float delta) {

        ScreenUtils.clear(Color.BLACK);

        // atualiza animações
        monstro.update(delta);
        jogador.update(delta);

        if (efeitoAtivo) {
            stateTimeEfeito += delta;
            if (efeitoAtual != null && efeitoAtual.isAnimationFinished(stateTimeEfeito)) {
                efeitoAtivo = false;
            }
        }

        // sistema de turnos
        if (!turnoJogador && aguardandoAnimacao) { // não é turno do jogador e está aguardando animação do monstro
            tempoEspera -= delta; // diminui o tempo de espera inicial antes do monstro começar a agir

            if (!monstro.ESTADO()) {
                if (tempoEspera <= 0) {
                    moedas += SaveManager.getInstance().getDiaAtual(); // recompensa em moedas baseada na fase/dia 
                    SaveManager.getInstance().avancarDia();
                    controle.Trocar_estado(Controle_Diagrama_Estados.State.MENU_PRINCIPAL);
                    return;
                }
                // Não faz mais nada, só aguarda

            } else {
                // quando acabar o tempo de espera e o monstro estiver parado, inicia o
                // movimento do monstro ao jogador
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
                                jogador.recuperaStamina();

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
        }

        // Desenha tudo
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();

        game.batch.enableBlending();

        game.batch.draw(fundo, 0, 0,
                game.viewport.getWorldWidth(),
                game.viewport.getWorldHeight());
        // desnha o jogador e o monstro
        jogador.draw(game.batch);
        monstro.draw(game.batch, monstro.getX(), monstro.getY(), monstro.getW(), monstro.getH());

        if (efeitoAtivo && efeitoAtual != null) {
            TextureRegion frame = efeitoAtual.getKeyFrame(stateTimeEfeito);
            game.batch.draw(frame, efeitoX, efeitoY, efeitoW, efeitoH);
        }

        game.batch.end();

        hud.render(jogador, monstro);

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int w, int h) {
        game.viewport.update(w, h, true);
        stage.getViewport().update(w, h, true);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        if (musica != null) {
            musica.stop(); // teste p parar a musica, aparentemente é isos q tem q chamar
        }
    }

    @Override
    public void dispose() {
        if (fundo != null)
            fundo.dispose();
        if (jogador != null)
            jogador.dispose();
        if (stage != null)
            stage.dispose();
        if (skin != null)
            skin.dispose();
        if (monstro != null)
            monstro.dispose();
        if (texirathor != null)
            texirathor.dispose();
        if (texmeteoro != null)
            texmeteoro.dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
