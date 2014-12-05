/*
 * Task.java
 * Models a city
 */
package mtsp;

import java.util.ArrayList;

public class Task {

    int id;
    int x;
    int y;
    int parentId;
    TaskType type;
    int taskDurationTime;

    // Constructs a randomly placed city
    public Task() {

        this.x = (int) (Math.random() * 200);
        this.y = (int) (Math.random() * 200);
    }

    // Constructs a city at chosen x, y location
    public Task(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Task(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.parentId = id;
    }

    public Task(int id, int x, int y, int size) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.parentId = id;
        this.type = TaskType.values()[size];

    }

    public Task(int id, int x, int y, int size, int taskDurationTime) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.parentId = id;
        this.type = TaskType.values()[size];
        this.taskDurationTime = taskDurationTime;

    }

    public Task(int id, int x, int y, int parentId, TaskType size) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.parentId = parentId;
        this.type = size;
    }

    public Task(int id, int x, int y, int parentId, TaskType size, int taskDurationTime) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.parentId = parentId;
        this.type = size;
        this.taskDurationTime = taskDurationTime;
    }

    public Task(int id, int x, int y, int parentId, int size, int taskDurationTime) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.parentId = parentId;
        this.type = TaskType.values()[size];
        this.taskDurationTime = taskDurationTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Gets city's x coordinate
    public int getX() {
        return this.x;
    }

    // Gets city's y coordinate
    public int getY() {
        return this.y;
    }

    // Gets the distance to given task to next task 
    public double distanceTo(Task task) {
        int xDistance = Math.abs(getX() - task.getX());
        int yDistance = Math.abs(getY() - task.getY());
        double distance = Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));

        return distance;
    }

// Gets the distance to given task with current uav
    public double distanceTo(UAV uav) {
        int xDistance = Math.abs(getX() - uav.x);
        int yDistance = Math.abs(getY() - uav.y);
        double distance = Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));

        return distance;
    }

    public double calculateTimeBetweenTask(Task task, UAV uav) {

        double distance = this.distanceTo(task);

        return (distance / uav.maxSpeed);
    }

    public double calculateTimeBetweenUAVandTask(UAV uav) {

        double distance = this.distanceTo(uav);

        return (distance / uav.maxSpeed);
    }

    @Override
    public String toString() {
        return this.parentId + " - ";
    }

    @Override
    public boolean equals(Object obj) {
        return this.id == ((Task) obj).id;
    }

    public boolean controlParentequals(Object obj) {
        return this.parentId == ((Task) obj).parentId;
    }
}
