package mtsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MultiChromosome {

    // Holds our tour of cities
    Map<Integer, ArrayList<Task>> multiChoromosome = new HashMap<Integer, ArrayList<Task>>();

    // Cache
    private double fitness = 0;
    private double distance = 0;

    // Constructs a blank tour
    public MultiChromosome() {

    }

    public ArrayList<Integer> generateRandomDigit() {
        ArrayList<Integer> randomList = new ArrayList<Integer>();
        do {

            Random rn = new Random();
            int temp = 0;
            int kalan = 0;
            for (int i = 0; i < Utils.numberOfUAVs() - 1; i++) {
                do {
                    temp = rn.nextInt(TaskManager.numberOfTasks() - temp);
                    if (Utils.maxTaskAssignment >= temp && temp > 2) {
                        break;
                    }
                } while (true);

                randomList.add(temp);
                kalan += temp;
            }
            temp = TaskManager.numberOfTasks() - kalan;
            if (temp < 0) {
                temp = 0;
            }
            if (temp <= Utils.maxTaskAssignment) {
                randomList.add(temp);
                break;
            } else {
                randomList.clear();
            }

        } while (true);

        return randomList;

    }

    // Gets a city from the tour
    public Task getTask(int tourPosition) {
        return null;
    }

    // Sets a city in a certain position within a tour
    public void setTask(int tourPosition, Task city) {
        // tour.set(tourPosition, city);
        // If the tours been altered we need to reset the fitness and distance
        fitness = 0;
        distance = 0;
    }

    // Gets the tours fitness
    public double getFitness() {
        if (fitness == 0) {
            try {
                for (int k = 0; k < multiChoromosome.keySet().size(); k++) {
                    ArrayList<Task> taskList = multiChoromosome.get(k);
                    if (taskList != null && taskList.size() > 0) {
                        fitness += taskList.get(0).distanceTo(Utils.getUavList().get(k));

                        for (int i = 0; i < taskList.size() - 1; i++) {
                            fitness += taskList.get(i).distanceTo(taskList.get(i + 1));
                        }
                        fitness += taskList.get(taskList.size() - 1).distanceTo(taskList.get(0));
                    }
                }
            } catch (Exception e) {
                e.getStackTrace();

                System.out.println(e.getMessage());
            }

        }
        fitness =Math.log(fitness * calculateTotalTime(this));
        distance = fitness;
        return fitness;
    }

    public double calculateTotalTime(MultiChromosome multichromosome) {
        double result = 0;
        for (int i = 0; i < Utils.totalUAV; i++) {
            if (multichromosome.multiChoromosome.containsKey(i)) {
                ArrayList<Task> getTaskList = multichromosome.multiChoromosome.get(i);
                result += calculateUAVTotalTaskDurationTime(getTaskList, i);
            }
        }
        return result;
    }

    public double calculateUAVTotalTaskDurationTime(ArrayList<Task> getTaskList, int uav) {

        double result = 0;
        if (!getTaskList.isEmpty()) {
            result += getTaskList.get(0).calculateTimeBetweenUAVandTask(Utils.getUavList().get(uav));

            for (int j = 0; j < getTaskList.size() - 1; j++) {
                result += getTaskList.get(j).calculateTimeBetweenTask(getTaskList.get(j + 1), Utils.getUavList().get(uav));
            }
        }
        return result;
    }

    void generateMultiIndividual() {
        // Loop through all our destination cities and add them to our tour
        ArrayList<Integer> generateRandomDigit = generateRandomDigit();//herbir ihaya atanacak task sayısı

        ArrayList<Task> remainTaskList = new ArrayList<Task>(TaskManager.taskList);

        Random rn = new Random();
        int total = TaskManager.numberOfTasks();

        for (int uav = 0; uav < Utils.numberOfUAVs(); uav++) {
            int maxITeration = Utils.maxITeration;
            ArrayList<Task> taskList = new ArrayList<Task>();

            int totalTaskForCurrentUAV = generateRandomDigit.get(uav);

            for (int i = 0; i < totalTaskForCurrentUAV; i++) {
                do {
                    if (remainTaskList.size() == 0) {
                        break;
                    }
                    //  System.out.println("remainTaskList.type() : " + remainTaskList.type());

                    int currentTaskId = 0;
                    if (remainTaskList.size() > 1) {
                        int taskIndex = rn.nextInt(remainTaskList.size());
                        currentTaskId = remainTaskList.get(taskIndex).id;
                    } else {
                        currentTaskId = remainTaskList.get(0).id;
                    }

                    Task task = TaskManager.getTaskbyId(currentTaskId);

                    if (!isTaskListContain(taskList, task)) {
                        taskList.add(task);
                        remainTaskList.remove(task);
                        total--;
                        break;
                    }
                    if (maxITeration-- == 0) {
                        break;
                    }
                    Integer maxAirTime = Utils.getUAVTypeAirtime().get(Utils.getUavList().get(uav).type);
                    if (calculateUAVTotalTaskDurationTime(taskList, uav) > maxAirTime);
                    {
                        break;
                    }

                } while (true);
                maxITeration = Utils.maxITeration;
            }

            // total -= taskList.type();
            multiChoromosome.put(uav, taskList);
            if (remainTaskList.size() == 0) {
                break;
            }
        }

    }

    public void display() {

        for (ArrayList<Task> cityList : multiChoromosome.values()) {
            for (Task city : cityList) {
                System.out.print(city.x + " : " + city.y + " / ");
            }
            System.out.println("--------------");
        }

    }

    public boolean isTaskListContain(ArrayList<Task> taskList, Task _task) {
        boolean result = false;

        for (Task task : taskList) {
            if (task != null && _task != null && task.controlParentequals(_task)) {
                result = true;
                break;
            }
        }

        return result;
    }

    public boolean isContain(Task _task) {
        boolean result = false;

        for (ArrayList<Task> taskList : multiChoromosome.values()) {
            for (Task task : taskList) {
                if (task != null && _task != null && task.equals(_task)) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    @Override
    public String toString() {
        String temp = "Total UAV Size :" + multiChoromosome.keySet().size() + "\n";
        int total = 0;
        for (int i = 0; i < multiChoromosome.keySet().size(); i++) {
            ArrayList<Task> taskList = multiChoromosome.get(i);
            if (taskList == null) {
                temp += "UAV-" + Utils.getUavList().get(i).type.name() + "- " + i + " : \n";
            } else {

                temp += "UAV-" + Utils.getUavList().get(i).type.name() + "- " + i + " : ";
                for (Task task : taskList) {
                    total++;
                    temp += task.toString();
                }
                temp += " total airTime :" + calculateUAVTotalTaskDurationTime(taskList, i) + "\n";;
            }
        }
        System.out.println("total : " + total);
        System.out.println("numberOfTasks : " + TaskManager.numberOfTasks());
        return temp; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean equals(Object obj) {
        return this.getFitness() == ((MultiChromosome) obj).getFitness();
    }

}
