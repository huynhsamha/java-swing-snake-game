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
    protected static final int maxLifes = 5;
    
    private ArrayList<Integer> X, Y;
    private int direction; private boolean isOpen;
    private static int lifes = 3;
    private int timesEatThunder, countTimeSpeedThunder, defaultTimeThunder = 30;
    
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
    protected boolean isAlive() {return lifes > 0;}
    protected boolean isOpen() {return isOpen;}
    protected int getX(int i) {return X.get(i);}
    protected int getY(int i) {return Y.get(i);}
    protected int getLifes() {return lifes;}
    protected int getType(int i) {return (i == 0 ? Cells.head : Cells.body);}
    
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

    protected void checkEatHeart(Things heart) {
        int x = X.get(0), y = Y.get(0);
        if (heart.empty() || x != heart.getX() || y != heart.getY()) return ;
        heart.setAvailable(false);
        lifes++; 
    } 
    
    protected void checkEatStar(Things star) {
        int x = X.get(0), y = Y.get(0);
        if (star.empty() || x != star.getX() || y != star.getY()) return ;
        star.setAvailable(false);
    }
    
    protected void checkEatThunder(Things thunder) {
        if (timesEatThunder == 0) return ;
        if (++countTimeSpeedThunder == defaultTimeThunder) {
            Screen.speed /= 2;
            timesEatThunder--;
            countTimeSpeedThunder = 0;
        }
        
        int x = X.get(0), y = Y.get(0);
        if (thunder.empty() || x != thunder.getX() || y != thunder.getY()) return ;
        thunder.setAvailable(false);
        timesEatThunder++; 
        Screen.speed *= 2;
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
    
    protected boolean checkDied() {
        int headX = X.get(0), headY = Y.get(0);
        if (headX < 0 || headX >= Screen.szWidth || headY < 0 || headY >= Screen.szHeight) {
            this.lifes--; 
            return true;
        }
        return false;
    }
}
