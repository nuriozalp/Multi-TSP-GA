package mtsp;

import java.util.Random;

public class TSP_GA {

    public static void main(String[] args) {
        int maxRegion = 1000;

        Random rn = new Random();
        // Create and add our tasks
        for (int i = 0; i < Utils.totalTask; i++) {

            Task task = new Task(Utils.taskId++, rn.nextInt(maxRegion), rn.nextInt(maxRegion),
                    rn.nextInt(TaskType.values().length), rn.nextInt(Utils.maxTaskDurationTime));
            TaskManager.addTask(task);
        }

        for (int i = 0; i < Utils.totalTask; i++) {
            Task task = TaskManager.getTask(i);
            switch (task.type.getValue()) {
                case 2:

                    Task temptask2 = new Task(Utils.taskId++, task.x, task.y, task.id, task.type, task.taskDurationTime);
                    TaskManager.addTask(temptask2);
                    break;

                case 3:
                    for (int k = 0; k < 2; k++) {
                        Task temptask3 = new Task(Utils.taskId++, task.x, task.y, task.id, task.type);
                        TaskManager.addTask(temptask3);
                    }
                    break;

            }
        }
        System.out.println("numberOfTasks : " + TaskManager.numberOfTasks());

        for (int i = 0; i < Utils.totalUAV; i++) {
            // Create and add our cities
            UAV uav = new UAV(Utils.uav_id++, rn.nextInt(maxRegion), rn.nextInt(maxRegion), rn.nextInt(UAVType.values().length));
            Utils.addUav(uav);
        }
        System.out.println("numberOfUAVs: " + Utils.numberOfUAVs());
        // Initialize population
        //Population pop = new Population(50, true);
        Population pop = new Population(100, true);
       // System.out.println("Initial distance: " + pop.getFittest().getDistance());

        // Evolve population for 100 generations
        pop = MultiGA.evolvePopulation(pop);
        for (int i = 0; i < 1000; i++) {
            pop = MultiGA.evolvePopulation(pop);
        }

        System.out.println(pop.individuals[0]);

        // Print final results
        System.out.println("Finished");
        //   System.out.println("Final distance: " + pop.getFittest().getDistance());
        System.out.println("Solution:");
        //   System.out.println(pop.getFittest());
        //  pop.tours[0].generateMultiIndividual();
    }
}
