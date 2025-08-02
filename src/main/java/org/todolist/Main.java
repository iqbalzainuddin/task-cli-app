package org.todolist;

import org.todolist.DataService.InputHandler;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("==============");
            System.out.println("To-do List App");
            System.out.println("==============\n");

            InputHandler.performAction(args);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}