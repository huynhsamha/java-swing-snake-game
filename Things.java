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
        this.type = type; this.available = false; 
    }
    
    protected void creatThings() {
        while (true) {
            X = randX(); Y = randY();
            Cells f = Screen.cell[X][Y];
            if (f.getType() == Cells.cell) break;
        }
        available = true;
    }
    
    protected void creatSpecialThings() {
        if (type == Cells.heart && Screen.snake.getLifes() >= Snake.maxLifes) return ; 
//        int num = random(0, 80); 
int num = 25;
        if (num == 25) creatThings();
    }
    
    protected void updateCell() {
        if (this.empty()) return ;
        Screen.cell[X][Y].setType(type);
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
