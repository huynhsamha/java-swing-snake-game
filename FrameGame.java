/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JFrame;

/**
 *
 * @author Huỳnh Sâm Hà @Stupid.Dog
 */
public class FrameGame extends JFrame {
    private final int WIDTH_FRAME = 1000, HEIGHT_FRAME = 600;
    private final Screen screen = new Screen();
    
    public FrameGame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH_FRAME, HEIGHT_FRAME));
        setResizable(false);
        setTitle("My Snake Game");
        
        add(screen);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
}
