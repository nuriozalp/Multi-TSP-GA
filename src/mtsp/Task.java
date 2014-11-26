/*
 * Task.java
 * Models a city
 */
package mtsp;

public class Task {

    int id;
    int x;
    int y;

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

    // Gets the distance to given city
    public double distanceTo(Task city) {
        int xDistance = Math.abs(getX() - city.getX());
        int yDistance = Math.abs(getY() - city.getY());
        double distance = Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));

        return distance;
    }

    @Override
    public String toString() {
        return getX() + ", " + getY();
    }

    @Override
    public boolean equals(Object obj) {
        return this.id == ((Task) obj).id;
    }

}
