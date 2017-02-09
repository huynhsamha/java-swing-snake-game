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
public class Direction {
    protected static final int north = 0, west = 1, south = 2, east = 3; 
    protected static final int nextX(int x, int direction) {
        switch (direction) {
            case west: return x+1;
            case east: return x-1;
            default: return x;
        }
    }
    
    protected static final int nextY(int y, int direction) {
        switch (direction) {
            case north: return y-1;
            case south: return y+1;
            default: return y;
        }
    }
}
