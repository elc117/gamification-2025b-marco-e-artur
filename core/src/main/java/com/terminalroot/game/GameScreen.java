package com.terminalroot.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {

    final Drop game;

    Texture backgroundTexture;
    Texture bucketTexture;
    Texture dropTexture;
    Sound dropSound;
    Music music;

    Sprite bucketSprite;
    Array<Sprite> dropSprites;
    Rectangle bucketRectangle;
    Rectangle dropRectangle;
    Vector2 touchPos;

    float dropTimer;

    public GameScreen(final Drop game) {
        this.game = game;
        create();
    }

    private void create() {
        backgroundTexture = new Texture("background.png");
        bucketTexture = new Texture("bucket.png");
        dropTexture = new Texture("drop.png");
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));

        bucketSprite = new Sprite(bucketTexture);
        bucketSprite.setSize(1, 1);

        dropSprites = new Array<>();
        bucketRectangle = new Rectangle();
        dropRectangle = new Rectangle();
        touchPos = new Vector2();

        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();
    }

    private void input() {
        float speed = 4f;
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucketSprite.translateX(speed * delta);
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucketSprite.translateX(-speed * delta);

        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            game.viewport.unproject(touchPos);
            bucketSprite.setCenterX(touchPos.x);
        }
    }

    private void logic() {
        float delta = Gdx.graphics.getDeltaTime();
        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();

        bucketSprite.setX(MathUtils.clamp(bucketSprite.getX(), 0, worldWidth - bucketSprite.getWidth()));
        bucketRectangle.set(bucketSprite.getX(), bucketSprite.getY(), bucketSprite.getWidth(), bucketSprite.getHeight());

        for (int i = dropSprites.size - 1; i >= 0; i--) {
            Sprite dropSprite = dropSprites.get(i);
            dropSprite.translateY(-2f * delta);
            dropRectangle.set(dropSprite.getX(), dropSprite.getY(), dropSprite.getWidth(), dropSprite.getHeight());

            if (dropSprite.getY() < -dropSprite.getHeight()) dropSprites.removeIndex(i);
            else if (bucketRectangle.overlaps(dropRectangle)) {
                dropSprites.removeIndex(i);
                dropSound.play();
            }
        }

        dropTimer += delta;
        if (dropTimer > 1f) {
            dropTimer = 0;
            createDroplet(worldWidth, worldHeight);
        }
    }

    private void createDroplet(float worldWidth, float worldHeight) {
        Sprite dropSprite = new Sprite(dropTexture);
        dropSprite.setSize(1, 1);
        dropSprite.setX(MathUtils.random(0f, worldWidth - dropSprite.getWidth()));
        dropSprite.setY(worldHeight);
        dropSprites.add(dropSprite);
    }

    @Override
    public void render(float delta) {
        input();
        logic();

        ScreenUtils.clear(Color.BLACK);
        game.viewport.apply();

        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        bucketSprite.draw(game.batch);

        for (Sprite dropSprite : dropSprites) dropSprite.draw(game.batch);

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}

    @Override
    public void show() {}

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        dropSound.dispose();
        music.dispose();
        dropTexture.dispose();
        bucketTexture.dispose();
    }
}
