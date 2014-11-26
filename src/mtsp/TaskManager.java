/*
* TaskManager.java
* Holds the cities of a tour
*/

package mtsp;

import java.util.ArrayList;

public class TaskManager {

    // Holds our cities
    private static ArrayList destinationCities = new ArrayList<Task>();
    
   

    
    // Adds a destination city
    public static void addTask(Task city) {
        destinationCities.add(city);
    }
    
    // Get a city
    public static Task getTask(int index){
        return (Task)destinationCities.get(index);
    }
    
    // Get the number of destination cities
    public static int numberOfTasks(){
        return destinationCities.size();
    }
}