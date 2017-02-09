/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.Image;
import java.io.*;
import javax.imageio.ImageIO;

/**
 *
 * @author Huỳnh Sâm Hà @Stupid.Dog
 */
public class Images {
    protected static Image cell, body, food, star, heart, thunder, 
            headOpenN, headCloseN, headOpenW, headCloseW, headOpenS, headCloseS, headOpenE, headCloseE;
    
    protected static void loadImages() {
        try {
            cell = ImageIO.read(new File("src/snakegame/Images/cell.png"));
            body = ImageIO.read(new File("src/snakegame/Images/body.png"));
            food = ImageIO.read(new File("src/snakegame/Images/food.png"));
            star = ImageIO.read(new File("src/snakegame/Images/star.png"));
            heart = ImageIO.read(new File("src/snakegame/Images/heart.png"));
            thunder = ImageIO.read(new File("src/snakegame/Images/thunder.png"));
            headOpenN = ImageIO.read(new File("src/snakegame/Images/headopenNorth.png"));
            headOpenW = ImageIO.read(new File("src/snakegame/Images/headopenWest.png"));
            headOpenS = ImageIO.read(new File("src/snakegame/Images/headopenSouth.png"));
            headOpenE = ImageIO.read(new File("src/snakegame/Images/headopenEast.png"));
            headCloseN = ImageIO.read(new File("src/snakegame/Images/headcloseNorth.png"));
            headCloseW = ImageIO.read(new File("src/snakegame/Images/headcloseWest.png"));
            headCloseS = ImageIO.read(new File("src/snakegame/Images/headcloseSouth.png"));
            headCloseE = ImageIO.read(new File("src/snakegame/Images/headcloseEast.png"));
        } 
        catch (IOException ex) {}
    }
    
    protected static Image getImageOfType(int type) {
        switch (type) {
            case 0: return cell;
            case 2: return body;
            case 3: return food;
            case 4: return star;
            case 5: return heart;
            case 6: return thunder;
            default: { /* case 1, it is HEAD */
                int dir = Screen.snake.getDirection();
                boolean isOpen = Screen.snake.isOpen();
                switch (dir) {
                    case Direction.north: return isOpen ? headOpenN : headCloseN;
                    case Direction.south: return isOpen ? headOpenS : headCloseS;
                    case Direction.east: return isOpen ? headOpenE : headCloseE;
                    default: return isOpen ? headOpenW : headCloseW;
                }
            }
        }
    }
}
