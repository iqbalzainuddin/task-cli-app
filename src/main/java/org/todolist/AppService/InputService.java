package org.todolist.DataService;

import org.todolist.DataModel.TodoItem;
import org.todolist.Enums.TaskStatus;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class InputHandler {
    private static TaskStatus getMarkStatus(String status) {
        try {
            return switch (status) {
                case "todo" -> TaskStatus.PENDING;
                case "in-progress" -> TaskStatus.ONGOING;
                case "done" -> TaskStatus.COMPLETED;
                default -> throw new IOException("Invalid arguments!");
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printList(LinkedHashMap<Integer, TodoItem> allItems) {
        for (Map.Entry<Integer, TodoItem> entry : allItems.entrySet()) {
            Integer k = entry.getKey();
            TodoItem v = entry.getValue();
            System.out.println(k + "\t" + v.taskName + "\t\t\t" + v.status);
        }
    }

    public static void performAction(String[] appParams) {
        try {
            TaskListManager taskListManager = new TaskListManager("data.json");
            String action = appParams[0];
            TaskStatus status;

            switch (action) {
                case "add":
                    if (appParams.length < 2) throw new IOException("Task name is required as argument!");
                    if (appParams.length > 2) throw new IOException("Only 1 argument required!");
                    taskListManager.addItem(appParams[1]);
                    break;
                case "update":
                    if (appParams.length < 2) throw new IOException("Task id and new task description is required as argument!");
                    if (appParams.length < 3) throw new IOException("New task description is required as argument!");
                    if (appParams.length > 3) throw new IOException("Only 2 arguments required!");
                    taskListManager.updateItemById(Integer.parseInt(appParams[1]), appParams[2]);
                    break;
                case "delete":
                    if (appParams.length < 2) throw new IOException("Task id is required as argument!");
                    if (appParams.length > 2) throw new IOException("Only 1 argument required!");
                    taskListManager.deleteItemById(Integer.parseInt(appParams[1]));
                    break;
                case "mark-in-progress":
                case "mark-done":
                    if (appParams.length < 2) throw new IOException("Task id is required as argument!");
                    if (appParams.length > 2) throw new IOException("Only 1 argument required!");
                    status = getMarkStatus(action.replace("mark-", ""));
                    taskListManager.updateItemById(Integer.parseInt(appParams[1]), status);
                    break;
                case "list":
                    if (appParams.length > 2) throw new IOException("Only 1 argument required!");
                    if (appParams.length < 2) {
                        printList(taskListManager.getAll());
                    } else {
                        status = getMarkStatus(appParams[1]);
                        printList(taskListManager.getItemByStatus(status));
                    }
                    break;
                default:
                    throw new IOException(action + " is an invalid action!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
