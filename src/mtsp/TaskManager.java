package mtsp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static mtsp.Utils.airtime;

public class TaskManager {

    // Holds our cities
    static ArrayList<Task> taskList = new ArrayList<Task>();

    static Map<TaskType, Integer> taskTypeMap = new HashMap<TaskType, Integer>();

    // Adds a destination city
    public static void addTask(Task city) {
        taskList.add(city);
    }

    // Get a city
    public static Task getTask(int index) {
        return taskList.get(index);
    }

    // Get a city
    public static Task getTaskbyId(int _id) {
        Task task = null;
        for (Task t : taskList) {
            if (t.id == _id) {
                task = t;
                break;
            }
        }
        return task;
    }

    // Get the number of destination cities
    public static int numberOfTasks() {
        return taskList.size();
    }

    public static Map<TaskType, Integer> getUAVTypeAirtime() {
        if (taskTypeMap.size() == 0) {
            taskTypeMap.put(TaskType.little, 500);
            taskTypeMap.put(TaskType.large, 1500);
            taskTypeMap.put(TaskType.medium, 5000);
        }
        return taskTypeMap;
    }
}
