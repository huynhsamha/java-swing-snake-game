/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

/**
 *
 * @author Huỳnh Sâm Hà @Stupid.Dog
 */
public class Food {
    private int X, Y;
    private boolean available;
            
    public Food() {
        X = -1; Y = -1; available = false;
    }
    
    protected void creatFood() {
        while (true) {
            X = randX(); Y = randY();
            Cells f = Screen.cell[X][Y];
            if (f.getType() == Cells.cell) break;
        }
        available = true;
    }
    
    protected void setAvailable(boolean bool) {available = bool;}
    
    protected boolean empty() {return !available;}
    protected int getX() {return X;}
    protected int getY() {return Y;}
    
    private int randX() {return random(0, Screen.szWidth-1);}
    private int randY() {return random(0, Screen.szHeight-1);}
    private int random(int L, int R) {
        return (int) Math.round(L + Math.random() * (R-L));
    }
}
