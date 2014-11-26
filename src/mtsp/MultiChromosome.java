
package mtsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MultiChromosome {

    // Holds our tasks
    Map<Integer, ArrayList<Task>> multiChoromosome = new HashMap<Integer, ArrayList<Task>>();

    // Cache
    private double fitness = 0;
    private double distance = 0;

    // Constructs a blank tour
    public MultiChromosome() {

    }

    public ArrayList<Integer> generateRandomDigit() {
        ArrayList<Integer> randomList = new ArrayList<Integer>();
        Random rn = new Random();
        int temp = 0;
        int kalan = 0;
        for (int i = 0; i < Utils.numberOfUAVs() - 1; i++) {
            temp = rn.nextInt(TaskManager.numberOfTasks() - temp);
            randomList.add(temp);
            kalan += temp;
        }
        temp = TaskManager.numberOfTasks() - kalan;
        randomList.add(temp);

        for (Integer randomList1 : randomList) {
            // System.out.println("UAV city : " + randomList1);
        }
        return randomList;

    }

    // Gets a city from the tour
    public Task getTask(int taskPosition) {
        return null;
    }

    public void setTask(int taskPosition, Task task) {
         fitness = 0;
        distance = 0;
    }

    // Gets the tours fitness
    public double getFitness() {
        if (fitness == 0) {
            try {
                 for (ArrayList<Task> taskList : multiChoromosome.values()) {
                if (taskList != null) {
                    for (int i = 0; i < taskList.size() - 1; i++) {
                        fitness += taskList.get(i).distanceTo(taskList.get(i + 1));
                    }
                    fitness += taskList.get(taskList.size() - 1).distanceTo(taskList.get(0));
                }
            }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
           
       }
        distance = fitness;
        return fitness;
    }

    void generateMultiIndividual() {
         ArrayList<Integer> generateRandomDigit = generateRandomDigit();//herbir ihaya atanacak task sayısı
        Random rn = new Random();
        for (int uav = 0; uav < Utils.numberOfUAVs(); uav++) {
            ArrayList<Task> taskList = new ArrayList<Task>();

            for (int i = 0; i < generateRandomDigit.get(uav); i++) {
                do {
                    Task task = TaskManager.getTask(rn.nextInt(TaskManager.numberOfTasks()));

                    if (!isContain(task)) {
                        taskList.add(task);
                        break;
                    }

                } while (true);

                multiChoromosome.put(uav, taskList);
            }
        }
    }

    public void display() {

        for (ArrayList<Task> taskList : multiChoromosome.values()) {
            for (Task task : taskList) {
                System.out.print(task.x + " : " + task.y + " / ");
            }
            System.out.println("--------------");
        }

    }

    public boolean isContain(Task _task ){
        boolean result = false;

        for (ArrayList<Task> cityList : multiChoromosome.values()) {
            for (Task city : cityList) {
                if (city != null && _task != null && city.equals(_task)) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

}
