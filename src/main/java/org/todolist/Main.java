package org.todolist;

import org.todolist.AppService.OutputService;
import org.todolist.AppService.InputService;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                OutputService.printCommandInstruction();
            } else {
                InputService.performAction(args);
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}