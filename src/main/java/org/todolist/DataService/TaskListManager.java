package org.todolist.DataService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.todolist.DataModel.TodoItem;
import org.todolist.Enums.TaskStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

public class TaskListManager {
    private final Path storageFile;
    private final ObjectMapper gObjectMapper;

    public TaskListManager(String filename) {
        try {
            String userHome = System.getProperty("user.home").toLowerCase();
            Path dataDir = Paths.get(userHome, ".todolist");

            this.storageFile = dataDir.resolve(filename);
            this.gObjectMapper = new ObjectMapper();

            Files.createDirectories(dataDir);

            if (!Files.exists(this.storageFile)) {
                Files.writeString(this.storageFile, this.gObjectMapper.writeValueAsString(new HashMap<>()));
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int generateIncrementalId(int id, Set<Integer> allIds) {
        if (id == 0) return 1;
        if (allIds.contains(id + 1)) {
            return this.generateIncrementalId(id + 1, allIds);
        }

        return id + 1;
    }

    private void saveListChange(int id, TodoItem item) {
        try {
            LinkedHashMap<Integer, TodoItem> allItems = this.getAll();
            allItems.put(id, item);
            String dataString = gObjectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(allItems);
            Files.writeString(this.storageFile, dataString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveListChange(int id) {
        try {
            LinkedHashMap<Integer, TodoItem> allItems = this.getAll();
            allItems.remove(id);
            String dataString = gObjectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(allItems);
            Files.writeString(this.storageFile, dataString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public LinkedHashMap<Integer, TodoItem> getAll() throws IOException {
        TypeReference<LinkedHashMap<Integer, TodoItem>> typeRef = new TypeReference<>() {};
        String dataString = Files.readString(this.storageFile);

        return gObjectMapper.readValue(dataString, typeRef);
    }

    public LinkedHashMap<Integer, TodoItem> getItemByStatus(TaskStatus status) {
        try {
            LinkedHashMap<Integer, TodoItem> allItems = this.getAll();
            LinkedHashMap<Integer, TodoItem> newList = new LinkedHashMap<>();

            allItems.forEach((k, v) -> {
                if (v.status == status) {
                    newList.put(k, v);
                }
            });

            return newList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TodoItem getItemById(int id) {
        try {
            // Get list of all task
            LinkedHashMap<Integer, TodoItem> allItems = this.getAll();

            if (!allItems.containsKey(id)) {
                throw new IOException("Item with id: " + id + " does not exist.");
            }

            return allItems.get(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addItem(String task) {
        try {
            // Get unique id to be assigned for task
            int id = generateIncrementalId(this.getAll().size(), this.getAll().keySet());

            // Create object for new task to be added
            TodoItem item = new TodoItem();
            item.setValue(task, TaskStatus.PENDING);

            // Save change to the item list
            this.saveListChange(id, item);

            // Display success message
            System.out.println("Task added successfully!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateItemById(int id, String newDescription) {
        try {
            // Get current item that want to be updated
            TodoItem currentItem = this.getItemById(id);

            // Update task description
            currentItem.setValue(newDescription, currentItem.status);

            // Save change to the item list
            this.saveListChange(id, currentItem);

            // Display success message
            System.out.println("Task updated successfully!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateItemById(int id, TaskStatus newStatus) {
        try {
            // Get current item that want to be updated
            TodoItem currentItem = this.getItemById(id);

            // Update task description
            currentItem.setValue(currentItem.taskName, newStatus);

            // Save change to the item list
            this.saveListChange(id, currentItem);

            // Display success message
            System.out.println("Task " + id + " " + currentItem.taskName + " marked as " + newStatus + "!");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteItemById(int id) {
        try {
            // Check if item exist
            if (!this.getAll().containsKey(id)) {
                throw new IOException("Task " + id + "does not exist!");
            }

            // Save change to list with action of removing the item
            this.saveListChange(id);

            // Display success message
            System.out.println("Task deleted successfully!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
