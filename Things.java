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
public class Things {
    private int X, Y, type;
    private boolean available;
            
    public Things(int type) {
        this.X = -1; this.Y = -1; 
        this.available = false; 
        this.type = type; 
        if (type == Cells.food) {
            long x = (long) Math.round(Math.random()*100);
            this.type = (x < 50) ? Cells.traidau : Cells.trainho;
        }
    }
    
    protected void creatThings() {
        while (true) {
            X = randX(); Y = randY();
            Cells f = Screen.cell[X][Y];
            if (f.getType() == Cells.cell) break;
        }
        available = true;
        Screen.cell[X][Y].setType(type);
    }
    
    protected void creatSpecialThings(int probability) {
        if (random(0, 100) <= probability) creatThings();
    }
    
    protected void updateCell() {
        if (!available) return ;
        if (X < 0 || X >= Screen.widthSize || Y < 0 || Y >= Screen.heightSize) return ;
        Screen.cell[X][Y].setType(type);
    }
    
    protected void setAvailable(boolean bool) {available = bool;}
    
    protected boolean empty() {return !available;}
    protected int getX() {return X;}
    protected int getY() {return Y;}
    
    protected static boolean addFood(int foodSize) {
        if (foodSize <= 2) return true;
        if (foodSize <= 5) return random(0, foodSize*100) <= 100;
        if (foodSize <= 9) return random(0, foodSize*300) <= 100;
        if (foodSize <= 15) return random(0, foodSize*500) <= 100;
        return false;
    }
    
    private int randX() {return random(0, Screen.widthSize-1);}
    private int randY() {return random(0, Screen.heightSize-1);}
    protected static int random(int L, int R) {
        return (int) Math.round(L + Math.random() * (R-L));
    }
}
