package com.tetris.arnaud.tetris.Models;

public final class Z extends Block implements Movement {
    public Z(int x) {
        super(x);
        this.matrix = new int[][][]
                {
                        {
                                {1, 1, 0},
                                {0, 1, 1}
                        },
                        {
                                {0, 1},
                                {1, 1},
                                {1, 0}
                        }
                };
    }
}