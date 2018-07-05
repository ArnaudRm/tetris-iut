package com.tetris.arnaud.tetris.Models;

public class S extends Block implements Movement {

    public S(int x){
        super(x);
        this.matrix = new int[][][]
                {
                        {
                                {0, 1, 1},
                                {1, 1, 0}
                        },
                        {
                                {1, 0},
                                {1, 1},
                                {0, 1}
                        }
                };
    }
}