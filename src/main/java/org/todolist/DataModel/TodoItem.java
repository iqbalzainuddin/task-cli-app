package org.todolist.DataModel;

import org.todolist.Enums.TaskStatus;

public class TodoItem {
    public String taskName;
    public TaskStatus status;

    public void setValue(String taskName, TaskStatus status) {
        this.taskName = taskName;
        this.status = status;
    }
}
