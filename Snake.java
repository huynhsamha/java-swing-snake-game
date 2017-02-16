/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.util.ArrayList;

/**
 *
 * @author Huỳnh Sâm Hà @Stupid.Dog
 */
public class Snake {
    protected static final int lengthDefault = 8, posHeadDefaultX = 8, posHeadDefaultY = 5;
    private final int dirDefault  = Direction.west;
    
    private ArrayList<Integer> X, Y;
    private int direction; private boolean isOpen;
    protected static int timesEatThunder, countTimeSpeedThunder, defaultTimeThunder = 50, deltaSpeed= 30;
    
    public Snake() {
        direction = dirDefault;
        X = new ArrayList<>();
        Y = new ArrayList<>();
        for (int i = 0; i < lengthDefault; i++) {
            X.add(posHeadDefaultX-i);
            Y.add(posHeadDefaultY);
        }
        isOpen = true;
        timesEatThunder = 0;
        countTimeSpeedThunder = 0;
    }
    
    protected void setDirection(int direction) {
        int d1 = this.direction, d2 = direction;
        if (d1 == Direction.north && d2 == Direction.south) return ;
        if (d1 == Direction.south && d2 == Direction.north) return ;
        if (d1 == Direction.east && d2 == Direction.west) return ;
        if (d1 == Direction.west && d2 == Direction.east) return ;
        this.direction = direction;
    }
    
    protected int length() {return X.size();}
    protected int getDirection() {return direction;}
    protected boolean isOpen() {return isOpen;}
    protected int getX(int i) {return X.get(i);}
    protected int getY(int i) {return Y.get(i);}
    protected int getType(int i) {
        if (i == 0) return Cells.head;
        if ((i+1)%4 == 0) return Cells.body1; 
        return Cells.body2;
    }
    
    protected void move() {
        for (int i=length()-1;i>0;i--) {
            X.set(i, X.get(i-1));
            Y.set(i, Y.get(i-1));
        }
        X.set(0, Direction.nextX(X.get(0), direction));
        Y.set(0, Direction.nextY(Y.get(0), direction));
        isOpen = !isOpen;
    }
    
    protected void checkEatFood(Things food) {
        int x = X.get(0), y = Y.get(0);
        if (x != food.getX() || y != food.getY()) return ;
        food.setAvailable(false);
        x = X.get(X.size()-1); y = Y.get(Y.size()-1);
        switch (direction) {
            case Direction.north: y++; break;
            case Direction.south: y--; break;
            case Direction.east : x++; break;
            case Direction.west : x--; break;
        }
        X.add(x); Y.add(y);
    }

    protected void checkEndThunder() {
        if (timesEatThunder == 0) return ;
        if (++countTimeSpeedThunder == defaultTimeThunder) {
            Screen.speed -= deltaSpeed;
            timesEatThunder--;
            countTimeSpeedThunder = 0;
        }
    }
    
    protected void checkEatThunder(Things thunder) {
        int x = X.get(0), y = Y.get(0);
        if (thunder.empty() || x != thunder.getX() || y != thunder.getY()) return ;
        thunder.setAvailable(false);
        Screen.speed += deltaSpeed;
        timesEatThunder++; 
        Player.score += Player.scoreThunder;
    }
    
    protected void checkEatStar(Things star) {
        int x = X.get(0), y = Y.get(0);
        if (x != star.getX() || y != star.getY()) return ;
        star.setAvailable(false);
        Player.score += Player.scoreStar;
    }
    
    protected void checkEatBody() {
        int headX = X.get(0), headY = Y.get(0);
        int len = length();
        for (int i = 1; i < len; i++) {
            int x = X.get(i), y = Y.get(i);
            if (headX != x || headY != y) continue;
            for (int j=len-1;j>=i;j--) {
                X.remove(j); Y.remove(j);
            }
            break;
        }
    }
    
    protected boolean isAlive() {
        if (length() < lengthDefault) return false;
        int headX = X.get(0), headY = Y.get(0);
        if (headX < 0 || headX >= Screen.widthSize || headY < 0 || headY >= Screen.heightSize) {
            return false;
        }
        return true;
    }
}
