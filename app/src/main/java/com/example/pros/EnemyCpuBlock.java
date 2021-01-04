package com.example.pros;

import android.graphics.Bitmap;

public class EnemyCpuBlock extends GameObject{

    private int score;
    private int xSpeed;
    public EnemyCpuBlock(Bitmap bitmap, int xPos, int yPos, int windowWidth, int windowHeight) {
        super(bitmap, xPos, yPos, windowWidth, windowHeight);
        this.score = 0;
        xSpeed = 13;
    }

    public void move(int xTarget) {
        if (xPos + bitmap.getWidth() / 2 < xTarget) {
            for(int i = 0; i < Math.abs(xSpeed); i++){//כדי שאם יש לחיצה ארוכה הוא יזוז בצעד צעד עד שהוא לא יכול יותר. אם זה זז 20 20 אז הוא לא יסכים לזוז אם הוא נגיד רחוק 18 מהמסגרת
                if(xPos + bitmap.getWidth() < windowWidth){
                    xPos += 1;
                }
            }
        }
        if (xPos + bitmap.getWidth() / 2 > xTarget) {
            for(int i = 0; i < Math.abs(xSpeed); i++){//כדי שאם יש לחיצה ארוכה הוא יזוז בצעד צעד עד שהוא לא יכול יותר. אם זה זז 20 20 אז הוא לא יסכים לזוז אם הוא נגיד רחוק 18 מהמסגרת
                if(xPos > 0){
                    xPos -= 1;
                }
            }
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
