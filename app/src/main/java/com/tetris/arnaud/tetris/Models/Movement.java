package com.tetris.arnaud.tetris.Models;

public interface Movement {
    public void rotate(int[][] map, boolean right);
    public void left(int[][] map);
    public void right(int[][] map);
    public void down();
}