package com.kravets.rpnjava3;

public class RailcarNoFreeSpaceException extends Exception {
    public RailcarNoFreeSpaceException() {
        super("Ошибка: В вагоне нет свободных мест!");
    }
}
