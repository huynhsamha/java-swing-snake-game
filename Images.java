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
    protected static Image backgroundLeaf, snakeLogo, gameover;
    protected static Image background, border, info, pressSpace;
    protected static Image cell, body1, body2, traidau, trainho, thunder, star;
    protected static Image headOpenN, headCloseN, headOpenW, headCloseW, headOpenS, headCloseS, headOpenE, headCloseE;
    
    protected static void loadImages() {
        try {
            backgroundLeaf = ImageIO.read(new File("src/snakegame/Images/backgroundLeaf.png"));
            snakeLogo = ImageIO.read(new File("src/snakegame/Images/snakegamelogo.png"));
            background = ImageIO.read(new File("src/snakegame/Images/bg.jpg"));
            border = ImageIO.read(new File("src/snakegame/Images/border.png"));
            info = ImageIO.read(new File("src/snakegame/Images/infoBG.png"));
            pressSpace = ImageIO.read(new File("src/snakegame/Images/press.png"));
            gameover = ImageIO.read(new File("src/snakegame/Images/gameoverBG.png"));
            
            cell = ImageIO.read(new File("src/snakegame/Images/cell.png"));
            body1 = ImageIO.read(new File("src/snakegame/Images/body1.png"));
            body2 = ImageIO.read(new File("src/snakegame/Images/body2.png"));
            thunder = ImageIO.read(new File("src/snakegame/Images/thunder.png"));
            traidau = ImageIO.read(new File("src/snakegame/Images/trai-dau.png"));
            trainho = ImageIO.read(new File("src/snakegame/Images/trai-nho.png"));
            star = ImageIO.read(new File("src/snakegame/Images/star.png"));
            
            headOpenN = ImageIO.read(new File("src/snakegame/Images/headopenNorth.png"));
            headOpenW = ImageIO.read(new File("src/snakegame/Images/headopenWest.png"));
            headOpenS = ImageIO.read(new File("src/snakegame/Images/headopenSouth.png"));
            headOpenE = ImageIO.read(new File("src/snakegame/Images/headopenEast.png"));
            headCloseN = ImageIO.read(new File("src/snakegame/Images/headcloseNorth.png"));
            headCloseW = ImageIO.read(new File("src/snakegame/Images/headcloseWest.png"));
            headCloseS = ImageIO.read(new File("src/snakegame/Images/headcloseSouth.png"));
            headCloseE = ImageIO.read(new File("src/snakegame/Images/headcloseEast.png"));
        } 
        catch (IOException ex) {
            System.out.println("Error reading images - class Images.java");
        }
    }
    
    protected static Image getImageOfType(int type) {
        switch (type) {
            case 0: return cell;
            case 1: return body1;
            case 2: return body2;
            case 3: return star;
            case 4: return thunder;
            case 5: return traidau;
            case 6: return trainho;
            default: { /* case 12, it is HEAD */
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
