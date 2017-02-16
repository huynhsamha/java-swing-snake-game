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
public class Player {
    protected static final long scoreStar = 50, scoreThunder = 10;
    protected static long score = 0, length = Snake.lengthDefault;
    protected static void update() {
        long curLength = Screen.snake.length();
        score += curLength-length;
        length = curLength;
    }
}
