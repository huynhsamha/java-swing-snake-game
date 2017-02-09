/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 *
 * @author Huỳnh Sâm Hà @Stupid.Dog
 */
public class Screen extends JPanel implements Runnable {

    protected static final int 
        szWidth = 28, szHeight = 18, numCells = szWidth * szHeight,
        szCell = 25, szBound = 0, szDist = szCell + szBound, 
        szPadding = 30,
        WIDTH_PANEL = szPadding*2+szDist*szWidth, /* 30*2+25*28 = 760 */
        HEIGHT_PANEL = szPadding*2+szDist*szHeight; /* 30*2+25*18 = 510 */    
        
    protected static int speed = 50;
    
    private static boolean gameIsPaused, gameOver, gameIsStarted;
    
    protected static Snake snake; 
    protected static Cells[][] cell; 
    protected static Things food, heart, thunder, star; 
    Thread thread;
    
    public Screen() {
        setPreferredSize(new Dimension(WIDTH_PANEL, HEIGHT_PANEL));
        setFocusable(true);
        gameIsPaused = false; gameOver = false; gameIsStarted = false;
        init();
        runGame();
    }

    private void init() {
        snake = new Snake();
        cell = new Cells[szWidth][szHeight];
        food = new Things(Cells.food);
        heart = new Things(Cells.heart);
        thunder = new Things(Cells.thunder);
        star = new Things(Cells.star);
        
        Images.loadImages();
        for (int i = 0; i < szWidth; i++) 
            for (int j = 0; j < szHeight; j++) 
                cell[i][j] = new Cells(szPadding+i*szDist, szPadding+j*szDist, Cells.cell);
        
        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                e.consume();
                int key = e.getKeyCode();
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP: snake.setDirection(Direction.north); break;
                    case KeyEvent.VK_DOWN: snake.setDirection(Direction.south); break;
                    case KeyEvent.VK_RIGHT: snake.setDirection(Direction.west); break;
                    case KeyEvent.VK_LEFT: snake.setDirection(Direction.east); break;
                }
            }
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
    }
    
    private void runGame() {
        thread = new Thread(this);
        thread.start();
    }
    
    private void restart() {
        init(); 
        runGame();
    }
    
    @Override
    public void run() {
        while (true) {
            snake.move();
            boolean justDied = snake.checkDied();
            if (snake.isAlive()) {
                if (justDied) {restart(); return ;}
                snake.checkEatFood(food);
                snake.checkEatHeart(heart);
                snake.checkEatThunder(thunder);
                snake.checkEatStar(star);
                snake.checkEatBody();
                if (food.empty()) food.creatThings();
                if (heart.empty()) heart.creatSpecialThings();
                if (thunder.empty()) thunder.creatSpecialThings();
                if (star.empty()) star.creatSpecialThings();
                updateCell();
            }
            else 
                gameOver = true;
            
            repaint();
            
            try {thread.sleep(10000 / speed);} 
            catch (InterruptedException ex) {}
        }
    }
    
    private void updateCell() {
        for (int i = 0; i < szWidth; i++) 
            for (int j = 0; j < szHeight; j++) 
                cell[i][j].setType(Cells.cell);
        
        for (int idx = 0; idx < snake.length(); idx++) {
            int i = snake.getX(idx), j = snake.getY(idx);
            if (i < 0 || i >= szWidth || j < 0 || j >= szHeight) continue ;
            cell[i][j].setType(snake.getType(idx));
        }
        
        food.updateCell();
        heart.updateCell();
        thunder.updateCell();
        star.updateCell();
    }
    
    public void clearPlaygroud(Graphics g) {
        g.setColor(Colors.backgroundPlayGround);
        g.fill3DRect(szPadding, szPadding, WIDTH_PANEL-2*szPadding, HEIGHT_PANEL-2*szPadding, true);
    }
    
    @Override
    public void paint(Graphics g) {
        if (!gameIsStarted) {
            g.setColor(Colors.paddingPlayGround);
            g.fill3DRect(0, 0, szPadding, HEIGHT_PANEL-szPadding, true);
            g.fill3DRect(szPadding, 0, WIDTH_PANEL-szPadding, szPadding, true);
            g.fill3DRect(WIDTH_PANEL-szPadding, szPadding, szPadding, HEIGHT_PANEL-szPadding, true);
            g.fill3DRect(0, HEIGHT_PANEL-szPadding, WIDTH_PANEL-szPadding, szPadding, true);
            clearPlaygroud(g);
            gameIsStarted = true;
        }
        
        if (gameOver) {
            
        }
        
        if (gameIsPaused) return ;
        
        clearPlaygroud(g);
        for (int i = 0; i < szWidth; i++) 
        for (int j = 0; j < szHeight; j++) 
            if (cell[i][j].getType() != Cells.cell) 
                g.drawImage(
                    cell[i][j].getImage(), 
                    cell[i][j].getX(), cell[i][j].getY(), 
                    szCell, szCell, null
                );
    }
}
