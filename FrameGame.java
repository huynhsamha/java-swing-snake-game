/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Huỳnh Sâm Hà @Stupid.Dog
 */
public class FrameGame extends JFrame {
    protected static final int WIDTH_FRAME = 1000, HEIGHT_FRAME = 650;
    
    public FrameGame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH_FRAME, HEIGHT_FRAME));
        setResizable(false);
        setTitle("My Snake Game");
        
        Images.loadImages();
        add(new Screen());
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
