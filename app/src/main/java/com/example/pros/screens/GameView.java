package com.example.pros.screens;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.example.pros.model.Background;
import com.example.pros.model.Ball;
import com.example.pros.model.EnemyBlock;
import com.example.pros.model.EnemyCpuBlock;
import com.example.pros.model.EnemyMultiplayerBlock;
import com.example.pros.model.MultiPlayerGame;
import com.example.pros.model.MyBlock;
import com.example.pros.R;

import java.util.Timer;
import java.util.TimerTask;

public class GameView extends SurfaceView implements Runnable {

    protected Thread gameTread;
    private MyBlock myBlock;
    private EnemyBlock enemyBlock;
    private Ball gameBall;
    private Background gameBackground;
    private GameScreenActivity.ScoreHandler scoreHandler;
    private boolean gameOver;

    private Bitmap myBlockBitmap;
    private Bitmap enemyBlockBitmap;

    private boolean isMultiplayer;
    private boolean isP1;

    public GameView(Context context, boolean isMultiplayer, boolean isP1, int windowHeight, int windowWidth, int myPlayerSkinImageID, GameScreenActivity.ScoreHandler scoreHandler) {
        super(context);

        this.scoreHandler = scoreHandler;
        gameTread = new Thread(this);

        this.isMultiplayer = isMultiplayer;
        this.isP1 = isP1;

        if(isMultiplayer){
            if(isP1){
                myBlockBitmap = BitmapFactory.decodeResource(getResources(), MultiPlayerGame.getInstance().getP1SkinImageID());
                myBlockBitmap = Bitmap.createScaledBitmap(myBlockBitmap, 250, 50, false);
                myBlock = new MyBlock(myBlockBitmap, (int) (windowWidth * 0.5) - (myBlockBitmap.getWidth() / 2), (int) (windowHeight * 0.9), windowWidth, windowHeight, true);
                myBlock.setP1(true);
                MultiPlayerGame.getInstance().setP1BitmapXPos(MultiPlayerGame.getInstance().getGameCode(), myBlock.getXPos());

                enemyBlockBitmap = BitmapFactory.decodeResource(getResources(), MultiPlayerGame.getInstance().getP2SkinImageID());
                enemyBlockBitmap = Bitmap.createScaledBitmap(enemyBlockBitmap, 250, 50, false);
                enemyBlock = new EnemyMultiplayerBlock(enemyBlockBitmap, (int) (windowWidth * 0.5) - (myBlockBitmap.getWidth() / 2), (int) (windowHeight * 0.1), windowWidth, windowHeight);
                ((EnemyMultiplayerBlock)enemyBlock).setP1(false);
                MultiPlayerGame.getInstance().setP2BitmapXPos(MultiPlayerGame.getInstance().getGameCode(), enemyBlock.getXPos());
            }
            else{
                myBlockBitmap = BitmapFactory.decodeResource(getResources(), MultiPlayerGame.getInstance().getP2SkinImageID());
                myBlockBitmap = Bitmap.createScaledBitmap(myBlockBitmap, 250, 50, false);
                myBlock = new MyBlock(myBlockBitmap, (int) (windowWidth * 0.5) - (myBlockBitmap.getWidth() / 2), (int) (windowHeight * 0.9), windowWidth, windowHeight, true);
                myBlock.setP1(false);
                MultiPlayerGame.getInstance().setP2BitmapXPos(MultiPlayerGame.getInstance().getGameCode(), myBlock.getXPos());

                enemyBlockBitmap = BitmapFactory.decodeResource(getResources(), MultiPlayerGame.getInstance().getP1SkinImageID());
                enemyBlockBitmap = Bitmap.createScaledBitmap(enemyBlockBitmap, 250, 50, false);
                enemyBlock = new EnemyMultiplayerBlock(enemyBlockBitmap, (int) (windowWidth * 0.5) - (myBlockBitmap.getWidth() / 2), (int) (windowHeight * 0.1), windowWidth, windowHeight);
                ((EnemyMultiplayerBlock)enemyBlock).setP1(true);
                MultiPlayerGame.getInstance().setP1BitmapXPos(MultiPlayerGame.getInstance().getGameCode(), enemyBlock.getXPos());
            }
        }
        else{
            myBlockBitmap = BitmapFactory.decodeResource(getResources(), myPlayerSkinImageID);
            myBlockBitmap = Bitmap.createScaledBitmap(myBlockBitmap, 250, 50, false);
            myBlock = new MyBlock(myBlockBitmap, (int) (windowWidth * 0.5) - (myBlockBitmap.getWidth() / 2), (int) (windowHeight * 0.9), windowWidth, windowHeight, false);

            enemyBlockBitmap = BitmapFactory.decodeResource(getResources(), myPlayerSkinImageID);
            enemyBlockBitmap = Bitmap.createScaledBitmap(enemyBlockBitmap, 250, 50, false);
            enemyBlock = new EnemyCpuBlock(myBlockBitmap, (int) (windowWidth * 0.5) - (myBlockBitmap.getWidth() / 2), (int) (windowHeight * 0.1), windowWidth, windowHeight);
        }

        Bitmap gameBallBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        gameBallBitmap = Bitmap.createScaledBitmap(gameBallBitmap, 70, 70, false);
        gameBall = new Ball(gameBallBitmap, (int) (windowWidth * 0.5) - (gameBallBitmap.getWidth() / 2), (int) (windowHeight * 0.5) - (gameBallBitmap.getHeight() / 2), windowWidth, windowHeight);

        Bitmap backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.game_background_black);
        backgroundBitmap = Bitmap.createScaledBitmap(backgroundBitmap, windowWidth, windowHeight, false);
        gameBackground = new Background(backgroundBitmap, 0, 0, windowWidth, windowHeight);

        gameTread.start();
        gameOver = false;
    }

    public void setGameOver() {
        gameOver = true;
    }

    @Override
    public void run() {
        resetGame();
        new Timer().schedule(new TimerTask() {
            public void run() {
                giveBallInitialSpeed();
                while (!gameOver) {
                    if (gameBall.checkCollision(myBlock) || gameBall.checkCollision(enemyBlock)) {
                        gameBall.setySpeed(gameBall.getySpeed() * -1);
                        float collisionXLocation = myBlock.getCollisionXLocation(gameBall);
                        if (myBlock.getXPos() <= collisionXLocation && collisionXLocation < (myBlock.getXPos() + myBlock.getBitmap().getWidth()) / 2) {
                            if (gameBall.getxSpeed() > 0) {
                                gameBall.setxSpeed(gameBall.getxSpeed() * -1);
                            }
                        } else if ((myBlock.getXPos() + myBlock.getBitmap().getWidth()) / 2 <= collisionXLocation && collisionXLocation <= myBlock.getXPos() + myBlock.getBitmap().getWidth()) {
                            if (gameBall.getxSpeed() < 0) {
                                gameBall.setxSpeed(gameBall.getxSpeed() * -1);
                            }
                        }
                    }
                    if (someoneScored()) {
                        Message goalMessage = scoreHandler.obtainMessage();
                        if (gameBall.checkWhoScored() == 1) {
                            myBlock.setScore(myBlock.getScore() + 1);
                        }
                        if (gameBall.checkWhoScored() == 2) {
                            enemyBlock.setScore(enemyBlock.getScore() + 1);
                        }
                        resetGame();
                        goalMessage.getData().putString("score_string", myBlock.getScore() + "-" + enemyBlock.getScore());
                        goalMessage.getData().putInt("my_block_score_int", myBlock.getScore());
                        goalMessage.getData().putInt("enemy_cpu_score_int", enemyBlock.getScore());
                        scoreHandler.sendMessage(goalMessage);
                        try {
//                            myBlock.setXTarget((float)(myBlock.getWindowWidth() * 0.5) - (myBlock.getBitmap().getWidth() / 2));
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    move();
                    drawSurface();
                }
            }
        }, 2000);
//        Message finalScoreMessage = finalScoreHandler.obtainMessage();
//        finalScoreMessage.getData().putIntegerArrayList("final_score", new ArrayList<Integer>(Arrays.asList(myBlock.getScore(), enemyCpuBlock.getScore())));
//        finalScoreHandler.sendMessage(finalScoreMessage);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        myBlock.setXTarget(event.getX());
        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_UP:
                myBlock.setXTarget(myBlock.getXPos());
                break;
        }
        return true;
    }

    private void drawSurface() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            if (canvas != null) {
                gameBackground.draw(canvas);
                myBlock.draw(canvas);
                enemyBlock.draw(canvas);
                gameBall.draw(canvas);
                getHolder().unlockCanvasAndPost(canvas);
            }
        }
    }

    private void move() {
        myBlock.move();
        gameBall.move();
        if(!isMultiplayer){
            enemyBlock.setxTarget(gameBall.getXPos());
        }
        else{
            //לא צריך פה כלום כי מהפעולה הבונה של האנמי מולטיפלייר בלוק זה כבר יעדכן את המיקום בקוד
//            enemyBlock.move();
        }
    }

    private boolean someoneScored() {
        return gameBall.checkWhoScored() == 1 || gameBall.checkWhoScored() == 2;
    }

    public void resetGame() {
        gameBall.setXPos((float) (gameBall.getWindowWidth() * 0.5) - (gameBall.getBitmap().getWidth() / 2));
        gameBall.setYPos((float) (gameBall.getWindowHeight() * 0.5) - (gameBall.getBitmap().getHeight() / 2));
        myBlock.setXPos((float) (myBlock.getWindowWidth() * 0.5) - (myBlock.getBitmap().getWidth() / 2));
        myBlock.setXTarget(myBlock.getXPos());
        if(!isMultiplayer){
            enemyBlock.setXPos((float) (enemyBlock.getWindowWidth() * 0.5) - (enemyBlock.getBitmap().getWidth() / 2));
        }
        drawSurface();
    }

    public void giveBallInitialSpeed() {
        gameBall.setxSpeed(16);
        gameBall.setySpeed(14);
    }
}
