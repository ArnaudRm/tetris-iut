package com.tetris.arnaud.tetris.Models;

public final class J extends Block {
    public J(int x){
        super(x);
        this.matrix = new int[][][]
                {
                        {
                                {1, 0, 0},
                                {1, 1, 1}
                        },
                        {
                                {0, 1},
                                {0, 1},
                                {1, 1}
                        },
                        {
                                {1, 1, 1},
                                {0, 0, 1}
                        },
                        {
                                {1, 1},
                                {1, 0},
                                {1, 0}
                        }
                };
    }
}