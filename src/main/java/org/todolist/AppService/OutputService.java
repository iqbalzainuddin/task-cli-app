package org.todolist.AppService;

public class OutputService {
    public static void printCommandInstruction() {
        System.out.println("======================");
        System.out.println("-----Task CLI App-----");
        System.out.println("======================");
        System.out.println();
        System.out.println("Command Guide:\n");
        System.out.println("task-cli <action> <arguments>\n");
        System.out.println("Action <arguments>:");
        System.out.println("add <task description>");
        System.out.println("delete <task id>");
        System.out.println("update <task id> <new task description>");
        System.out.println("mark-in-progress <task id>");
        System.out.println("mark-done <task id>");
        System.out.println("list");
        System.out.println("list done");
        System.out.println("list in-progress");
        System.out.println("list todo");
        System.out.println();
    }
}
