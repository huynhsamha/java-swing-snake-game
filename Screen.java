/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 *
 * @author Huỳnh Sâm Hà @Stupid.Dog
 */
public class Screen extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    protected static final int widthSize = 25, heightSize = 15, borderSize = 30;
    protected static final int cellSize = 25, boundSize = 0, distSize = cellSize + boundSize; 
    protected static final int widthScreen  = borderSize*2+distSize*widthSize; 
    protected static final int heightScreen = borderSize*2+distSize*heightSize; 
    protected static final int paddingX = 25, paddingY = 150; 
    
    protected static int speed = 60;
    private static boolean gameIsStarted;
    private static boolean gameIsPaused; 
    private static boolean gameOver; 
    
    protected static Snake snake; 
    protected static Cells[][] cell; 
    protected static Things star, thunder;
    protected static ArrayList<Things> food;
    protected Thread thread;

    public Screen() {
        setPreferredSize(new Dimension(FrameGame.WIDTH_FRAME, FrameGame.HEIGHT_FRAME));
        setFocusable(true);
        newGame();
    }

    private void newGame() {
        gameOver = false; gameIsStarted = false; gameIsPaused = true;
        speed = 60;
        init();
        addKeyListener(this);
        removeMouseListener(this);
        removeMouseMotionListener(this);
        thread = new Thread(this);
        thread.start();
    }
    
    private void endGame() {
        removeKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    
    private void init() {
        snake = new Snake();
        cell = new Cells[widthSize][heightSize];
        star = new Things(Cells.star);
        thunder = new Things(Cells.thunder);
        food = new ArrayList<Things>();
        
        for (int i = 0; i < widthSize; i++) 
            for (int j = 0; j < heightSize; j++) 
                cell[i][j] = new Cells(paddingX+borderSize+i*distSize, paddingY+borderSize+j*distSize, Cells.cell);
        
        for (int idx = 0; idx < snake.length(); idx++) {
            int i = snake.getX(idx), j = snake.getY(idx);
            cell[i][j].setType(snake.getType(idx));
        }
        
        thunder.creatSpecialThings(100);
        star.creatSpecialThings(100);
    }
    
    @Override
    public void run() {
        while (true) {
            if (!gameIsPaused) {
                snake.move();
                if (!snake.isAlive()) gameOver = true;
                else {
                    snake.checkEatThunder(thunder);
                    snake.checkEndThunder();
                    snake.checkEatStar(star);
                    for (int i = 0; i < food.size(); i++) {
                        Things fo = food.get(i);
                        snake.checkEatFood(fo);
                        if (fo.empty()) food.remove(i--);
                    }
                    snake.checkEatBody();
                    if (thunder.empty()) thunder.creatSpecialThings(10);
                    if (star.empty()) star.creatSpecialThings(1);
                    if (Things.addFood(food.size())) {
                        Things fo = new Things(Cells.food);
                        fo.creatThings();
                        food.add(fo);
                    }
                }
                updateCell();
                Player.update();
            }
            repaint();
            if (gameOver) {endGame(); return ;}
            try {thread.sleep(10000 / speed);} 
            catch (InterruptedException ex) {}
        }
    }
    
    private void updateCell() {
        for (int i = 0; i < widthSize; i++) 
            for (int j = 0; j < heightSize; j++) 
                cell[i][j].setType(Cells.cell);
        
        for (int idx = 0; idx < snake.length(); idx++) {
            int i = snake.getX(idx), j = snake.getY(idx);
            if (i < 0 || i >= widthSize || j < 0 || j >= heightSize) continue ;
            cell[i][j].setType(snake.getType(idx));
        }
        thunder.updateCell();
        star.updateCell();
        for (int i = 0; i < food.size(); i++) {
            Things fo = food.get(i);
            fo.updateCell();
        }
    }
    
    @Override
    public void paint(Graphics g) {
        if (!gameIsStarted) {
            g.drawImage(Images.backgroundLeaf, 0, 0, null);
            g.drawImage(Images.snakeLogo, 50, -10, 900, 180, null);
            g.drawImage(Images.border, paddingX+0, paddingY+0, borderSize, heightScreen-borderSize, null);
            g.drawImage(Images.border, paddingX+borderSize, paddingY+0, widthScreen-borderSize, borderSize, null);
            g.drawImage(Images.border, paddingX+widthScreen-borderSize, paddingY+borderSize, borderSize, heightScreen-borderSize, null);
            g.drawImage(Images.border, paddingX+0, paddingY+heightScreen-borderSize, widthScreen-borderSize, borderSize, null);
            paintPlaygroud(g);
            paintInformation(g);
            gameIsStarted = true;
        }
        if (gameOver) {
            int x = paddingX+borderSize, y = paddingY+borderSize;
            int w = widthScreen-2*borderSize, h = heightScreen-2*borderSize;
            g.drawImage(Images.gameover, x, y, w, h, null);
            g.setColor(new Color(255,251,0));
            g.setFont(new Font("Bookman Old Style", Font.BOLD, 60));
            String score = String.format("%06d", Player.score);
            g.drawString(score, x+200, y+250);
            return ;
        }
        paintPlaygroud(g);
        paintInformation(g);
        showPressSpace(g);
    }
    
    private long TimeShowPause = (long) System.currentTimeMillis();
    private boolean showPause = true;
    private void showPressSpace(Graphics g) {
        if (gameIsPaused && showPause) 
            g.drawImage(Images.pressSpace, 90, 300, 550, 120, null);
        long t = (long) System.currentTimeMillis();
        if (t-TimeShowPause > 400) {
            TimeShowPause = t; 
            showPause = !showPause;
        }
    }
    
    private void paintInformation(Graphics g) {
        int x = paddingX+widthScreen+20, y = paddingY+25, w = 240, h = 380;
        g.clearRect(x, y, w, h);
        g.drawImage(Images.info, x, y, w, h, null);
        g.setColor(new Color(255, 238, 0));
        g.setFont(new Font("Bookman Old Style", Font.BOLD, 40));
        String score = String.format("%06d", Player.score);
        g.drawString(score, x+45, y+115);
        String length = String.format("%03d", Player.length);
        g.drawString(length, x+80, y+220);
    }
    
    private void paintPlaygroud(Graphics g) {
        int x = paddingX+borderSize, y = paddingY+borderSize;
        int w = widthScreen-2*borderSize, h = heightScreen-2*borderSize;
        g.drawImage(Images.background, x, y, w, h, null);

        for (int i = 0; i < widthSize; i++) 
        for (int j = 0; j < heightSize; j++) 
            if (cell[i][j].getType() != Cells.cell) 
                g.drawImage(cell[i][j].getImage(), 
                    cell[i][j].getX(), cell[i][j].getY(), 
                    cellSize, cellSize, null
                );
    }

    @Override
    public void keyPressed(KeyEvent e) {
        e.consume();
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE) {
            gameIsPaused = !gameIsPaused;
            return ;
        }
        if (gameIsPaused) return ;
        switch (key) {
            case KeyEvent.VK_UP: snake.setDirection(Direction.north); break;
            case KeyEvent.VK_DOWN: snake.setDirection(Direction.south); break;
            case KeyEvent.VK_RIGHT: snake.setDirection(Direction.west); break;
            case KeyEvent.VK_LEFT: snake.setDirection(Direction.east); break;
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX(), y = e.getY();
        boolean yflag = 460 <= y && y <= 510;
        boolean again = 150 <= x && x <= 330;
        boolean exit  = 430 <= x && x <= 580;
        if (!yflag) return ;
        if (exit) System.exit(0);
        if (again) newGame();
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX(), y = e.getY();
        boolean yflag = 460 <= y && y <= 510;
        boolean again = 150 <= x && x <= 330;
        boolean exit  = 430 <= x && x <= 580;
        if (yflag && (again || exit)) setCursor(new Cursor(Cursor.HAND_CURSOR));
        else setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX(), y = e.getY();
        boolean yflag = 460 <= y && y <= 510;
        boolean again = 150 <= x && x <= 330;
        boolean exit  = 430 <= x && x <= 580;
        if (yflag && (again || exit)) setCursor(new Cursor(Cursor.HAND_CURSOR));
        else setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    @Override
    public void keyReleased(KeyEvent e) {
        e.consume();
    }
    @Override
    public void keyTyped(KeyEvent e) {
        e.consume();
    }
    @Override
    public void mousePressed(MouseEvent e) {
        e.consume();
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        e.consume();
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        e.consume();
    }
    @Override
    public void mouseExited(MouseEvent e) {
        e.consume();
    }
}
