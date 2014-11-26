/*
 * TSP_GA.java
 * Create a tour and evolve a solution
 */
package mtsp;

public class TSP_GA {

    public static void main(String[] args) {

        // Create and add our cities
        Task task = new Task(Utils.task_id++, 60, 200);
        TaskManager.addTask(task);
        Task city2 = new Task(Utils.task_id++, 180, 200);
        TaskManager.addTask(city2);
        Task city3 = new Task(Utils.task_id++, 80, 180);
        TaskManager.addTask(city3);
        Task city4 = new Task(Utils.task_id++, 140, 180);
        TaskManager.addTask(city4);
        Task city5 = new Task(Utils.task_id++, 20, 160);
        TaskManager.addTask(city5);
        Task city6 = new Task(Utils.task_id++, 100, 160);
        TaskManager.addTask(city6);
        Task city7 = new Task(Utils.task_id++, 200, 160);
        TaskManager.addTask(city7);
        Task city8 = new Task(Utils.task_id++, 140, 140);
        TaskManager.addTask(city8);
        Task city9 = new Task(Utils.task_id++, 40, 120);
        TaskManager.addTask(city9);
        Task city10 = new Task(Utils.task_id++, 100, 120);
        TaskManager.addTask(city10);
        Task city11 = new Task(Utils.task_id++, 180, 100);
        TaskManager.addTask(city11);
        Task city12 = new Task(Utils.task_id++, 60, 80);
        TaskManager.addTask(city12);
        Task city13 = new Task(Utils.task_id++, 120, 80);
        TaskManager.addTask(city13);
        Task city14 = new Task(Utils.task_id++, 180, 60);
        TaskManager.addTask(city14);
        Task city15 = new Task(Utils.task_id++, 20, 40);
        TaskManager.addTask(city15);
        Task city16 = new Task(Utils.task_id++, 100, 40);
        TaskManager.addTask(city16);
        Task city17 = new Task(Utils.task_id++, 200, 40);
        TaskManager.addTask(city17);
        Task city18 = new Task(Utils.task_id++, 20, 20);
        TaskManager.addTask(city18);
        Task city19 = new Task(Utils.task_id++, 60, 20);
        TaskManager.addTask(city19);
        Task city20 = new Task(Utils.task_id++, 160, 20);
        TaskManager.addTask(city20);
        System.out.println(TaskManager.numberOfTasks());

        UAV uav1 = new UAV(Utils.uav_id++, 0, 0);
        Utils.addUav(uav1);

        UAV uav2 = new UAV(Utils.uav_id++, 10, 10);
        Utils.addUav(uav2);

        UAV uav3 = new UAV(Utils.uav_id++, 50, 50);
        Utils.addUav(uav3);

        // Initialize population
        //Population pop = new Population(50, true);
        Population pop = new Population(100, true);
       // System.out.println("Initial distance: " + pop.getFittest().getDistance());

        // Evolve population for 100 generations
        pop = MultiGA.evolvePopulation(pop);
        for (int i = 0; i < 100; i++) {
            pop = MultiGA.evolvePopulation(pop);
        }

        // Print final results
        System.out.println("Finished");
        //   System.out.println("Final distance: " + pop.getFittest().getDistance());
        System.out.println("Solution:");
     //   System.out.println(pop.getFittest());
        //  pop.tours[0].generateMultiIndividual();
    }
}
