package com.tetris.arnaud.tetris.Models;

public final class O extends Block {

    public O(int x){
        super(x);
        this.matrix = new int[][][]
                {
                        { {1, 1}, {1, 1} },
                };
    }
}