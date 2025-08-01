package org.todolist;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("==============");
            System.out.println("To-do List App");
            System.out.println("==============\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}