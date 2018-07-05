package com.tetris.arnaud.tetris.Models;

public final class L extends Block {

    public L(int x){
        super(x);
        this.matrix = new int[][][]
                {
                        {
                                {0, 0, 1},
                                {1, 1, 1}
                        },
                        {
                                {1, 0},
                                {1, 0},
                                {1, 1}
                        },
                        {
                                {1, 1, 1},
                                {1, 0, 0}
                        },
                        {
                                {1, 1},
                                {0, 1},
                                {0, 1}
                        }
                };
    }
}