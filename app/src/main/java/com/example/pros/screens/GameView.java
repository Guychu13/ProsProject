package com.example.pros.screens;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.example.pros.db.Repository;
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

/**
 * 	המחלקה האחראית על כל הלוגיקה בפועל של המשחק.
 */
public class GameView extends SurfaceView implements Runnable {
    /**
     * ה-  Thread עליו מתנהל המשחק.
     */
    protected Thread gameTread;
    /**
     * 	הבלוק של המשתמש.
     */
    private MyBlock myBlock;
    /**
     * 	הבלוק של אויבו של המשתמש.
     */
    private EnemyBlock enemyBlock;
    /**
     * 	כדור המשחק.
     */
    private Ball gameBall;
    /**
     * רקע מסך המשחק.
     */
    private Background gameBackground;
    /**
     * 	ה-  Handler המטפל בלוח הניקוד בעת משחק.
     */
    private GameScreenActivity.ScoreHandler scoreHandler;
    /**
     * משתנה בוליאני המסמל האם המשחק נגמר.
     */
    private boolean gameOver;
    /**
     * התמונה של בלוק המשתמש.
     */
    private Bitmap myBlockBitmap;
    /**
     * התמונה של הבלוק של אויבו של המשתמש.
     */
    private Bitmap enemyBlockBitmap;
    /**
     * משתנה בוליאני המסמל אם הבלוק הוא חלק ממשחק מקוון או לא.
     */
    private boolean isMultiplayer;
    /**
     * 	משתנה בוליאני המסמל האם הבלוק הוא של יוצר המשחק(P1).
     */
    private boolean isP1;

    /**
     * הפעולה הבונה של המחלקה. בה מתבצעת הבנייה של האובייקטים השונים במשחק, ומופעלת לולאת המשחק.
     * @param context
     * @param isMultiplayer
     * @param isP1
     * @param windowHeight
     * @param windowWidth
     * @param myPlayerSkinImageID
     * @param scoreHandler
     */
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
                enemyBlock = new EnemyMultiplayerBlock(enemyBlockBitmap, (int) (windowWidth * 0.5) - (myBlockBitmap.getWidth() / 2), (int) (windowHeight * 0.1), windowWidth, windowHeight, false);
//                ((EnemyMultiplayerBlock)enemyBlock).setP1(false);
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
                enemyBlock = new EnemyMultiplayerBlock(enemyBlockBitmap, (int) (windowWidth * 0.5) - (myBlockBitmap.getWidth() / 2), (int) (windowHeight * 0.1), windowWidth, windowHeight, true);
//                ((EnemyMultiplayerBlock)enemyBlock).setP1(true);
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

        Repository.getInstance().setListenerOnBallIsMoving(MultiPlayerGame.getInstance().getGameCode());
        gameTread.start();
        gameOver = false;
    }

    public void setGameOver() {
        gameOver = true;
    }

    /**
     * הפעולה המכילה את לולאת המשחק, בה מתבצעת ציור האובייקטים, תזוזת האובייקטים ובדיקות יציאה מהמסך והבקעת שערים.
     */
    @Override
    public void run() {
        resetGame();
        new Timer().schedule(new TimerTask() {
            public void run() {
                giveBallInitialSpeed();
                MultiPlayerGame.getInstance().setBallIsMoving(true);
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
                            MultiPlayerGame.getInstance().setBallIsMoving(false);//מעדכן מתי הכדור בהשהייה
                            Thread.sleep(3000);
                            MultiPlayerGame.getInstance().setBallIsMoving(true);//מעדכן מתי חזר לזוז
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

    /**
     * הפעולה המזהה נגיעות והחלקות על המסך ומעדכנת את ה-X אליו ינוע הבלוק של המשתמש.
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {//כל עוד האצבע על המסך ימשיך לעקוב אחרי האצבע ולזוז אחריה אבל כשמרימים את האצבע הבלוק ייעצר
        myBlock.setXTarget(event.getX());
        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_UP:
                myBlock.setXTarget(myBlock.getXPos());
                break;
        }
        return true;
    }

    /**
     * הפעולה המציירת את האובייקטים על המסך.
     */
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
        if(isMultiplayer && MultiPlayerGame.getInstance().isBallIsMoving()){//רוצה שהכדור יזוז במולטיפלייר רק אם בפיירבייס כתוב שהוא זז
            gameBall.move();
        }
        if(!isMultiplayer){
            enemyBlock.setxTarget(gameBall.getXPos());
            enemyBlock.move();
            gameBall.move();//זה היה מחוץ לif כלשהו
        }
    }

    private boolean someoneScored() {
        return gameBall.checkWhoScored() == 1 || gameBall.checkWhoScored() == 2;
    }

    /**
     * הפעולה המאתחלת את מיקומי האובייקטים לאחר הבקעת שער ובתחילת המשחק.
     */
    public void resetGame() {
        gameBall.setXPos((float) (gameBall.getWindowWidth() * 0.5) - (gameBall.getBitmap().getWidth() / 2));
        gameBall.setYPos((float) (gameBall.getWindowHeight() * 0.5) - (gameBall.getBitmap().getHeight() / 2));
        myBlock.setXPos((float) (myBlock.getWindowWidth() * 0.5) - (myBlock.getBitmap().getWidth() / 2));
        myBlock.setXTarget(myBlock.getXPos());//כדי שאחרי גולים לא ישתגר לאיקס טארגט, אלא יישאר באמצע
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
