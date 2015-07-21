/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleShipGame;

import java.util.Random;

/**
 *
 * @author adhoulih
 */
public class testBot {
    private int EMPTY = 0;
    private int PATROLBOAT = 2;
    private int DESTROYER = 3;
    private int BATTLESHIP = 4;
    private int AIRCRAFTCARRIER = 5;
    private int HIT = 6;
    private int MISS = 7;
    
    
    
    public testBot(){
        
    }
    public int[] runMove(Section[][] grid){
        int[] retCoord = new int[2];
        Random rand = new Random();
        int randX = rand.nextInt((10 - 0));
        int randY = rand.nextInt((10 - 0));
        while(grid[randY][randX].getSectionStatus() == HIT){
            randX = rand.nextInt((10 - 0));
            randY = rand.nextInt((10 - 0));
        }
        retCoord[0] = randX;
        retCoord[1] = randY;
        
        return retCoord;
        
    }
    
}
