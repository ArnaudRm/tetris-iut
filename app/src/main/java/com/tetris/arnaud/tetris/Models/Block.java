package com.tetris.arnaud.tetris.Models;

import android.util.Log;

public abstract class Block implements Movement{
    protected int height;
    protected int width;
    protected int sens = 0;
    protected int[][][] matrix;
    protected int x = 0;
    protected int y = 0;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int color;

    public Block(int x) {
        this.x = x;
    }

    @Override
    public void down() {
        this.y++;
    }

    public void rotate(int[][] map, boolean right)
    {
        if(this.canMove(map, "rotate"))
        {
            this.sens = this._rotate();
        }
    }

    public int[][] getForm()
    {
        return this.matrix[this.sens];
    }

    @Override
    public void left(int[][] map) {
        if(this.canMove(map, "left"))
        {
            this.x--;
        }
    }

    @Override
    public void right(int[][] map) {
        if(this.canMove(map, "right"))
        {
            this.x++;
        }
    }

    public void down(int[][] map) {
        if (this.canMove(map, "down"))
        {
            this.down();
        }
    }

    private int _rotate() {
        return (this.sens >= this.matrix.length -1) ? 0 : this.sens + 1;
    }

    public boolean canMove(int[][] map, String d)
    {
        boolean r = true;
        int x, y, s;
        x = y = 0;
        s = this.sens;
        switch(d){
            case "left": x--; break;
            case "right": x++; break;
            case "down": y++; break;
            default:
                s = this._rotate();
                break;
        }

        outerloop:
        for(int i = 0; i < this.matrix[s].length; i++)
        {
            for(int j = 0; j < this.matrix[s][i].length; j++)
            {
                int Y = this.y + i + y;
                int X = this.x + j + x;
                if(X < 0 || Y < 0 ||
                        Y >= map.length ||
                        X >= map[Y].length ||
                        (map[Y][X] > 0 && this.matrix[s][i][j] > 0))
                {
                    r = false;
                    break outerloop;
                }
            }
        }
        return r;
    }

    public void fix(int[][] map)
    {
        for(int i = 0; i < this.matrix[this.sens].length; i++)
        {
            for(int j = 0; j < this.matrix[this.sens][i].length; j++)
            {
                if(this.matrix[this.sens][i][j] != 0)
                {
                    map[this.y + i][this.x + j] = this.color; // this.matrix[this.sens][i][j];
                }
            }
        }
    }
}