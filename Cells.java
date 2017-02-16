/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.*;

/**
 *
 * @author Huỳnh Sâm Hà @Stupid.Dog
 */
public class Cells {
    private int x, y, type;
    private Image image;
    
    protected static final int cell = 0, head = 12, body1 = 1, body2 = 2, star = 3, thunder = 4;
    protected static final int food = -1, traidau = 5, trainho = 6;

    public Cells(int x, int y, int type) {
        this.x = x; this.y = y; this.type = type;
        this.image = Images.getImageOfType(type);
    }

    protected void setType(int type) {
        this.type = type;
        this.image = Images.getImageOfType(type);
    }
    
    protected int getX() {return this.x;}
    protected int getY() {return this.y;}
    protected int getType() {return this.type;}
    protected Image getImage() {return this.image;}
}
