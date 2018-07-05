package com.tetris.arnaud.tetris.Models;

public final class I extends Block {
    public I(int x) {
        super(x);
        this.matrix = new int[][][]
                {
                        {
                                {1, 1, 1, 1}
                        },
                        {
                                {1},
                                {1},
                                {1},
                                {1}
                        }
                };
    }



}