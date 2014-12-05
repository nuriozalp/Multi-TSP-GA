/*
 * GA.java
 * Manages algorithms for evolving population
 */
package mtsp;

import java.util.ArrayList;
import java.util.Random;

public class MultiGA {

    /* GA parameters */
    private static final double mutationRate = 0.015;
    private static final int tournamentSize = 3;
    private static final boolean elitism = true;
    private static Random rn = new Random();

    // Evolves a population over one generation
    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.populationSize(), false);

        // Keep our best individual if elitism is enabled
        int elitismOffset = 0;
        if (elitism) {

            newPopulation.individuals[0] = pop.getFittest();
            elitismOffset = 1;
            System.out.println("best Fitness : " + pop.getFittest().getFitness());
        }

        // Crossover population
        // Loop over the new population's size and create individuals from
        // Current population
        for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
            // Select parents
            MultiChromosome parent = tournamentSelection(pop);
            // Crossover parents
            if (parent != null) {

                int totalChromozomcukSayisi = 0;
                for (int j = 0; j < Utils.numberOfUAVs(); j++) {
                    if (parent.multiChoromosome.containsKey(j)) {
                        totalChromozomcukSayisi++;
                    }
                }
                if (totalChromozomcukSayisi == 1) {
                    mutate(newPopulation.individuals[i]);
                } else {

                    MultiChromosome child = crossover(parent);
                    child.getFitness();
                    if (child.getFitness() < 1) {
                        //                        System.out.println("child.getFitness() : " + child.getFitness());
                    } else {
                        // if(!isPopulationContains(child, newPopulation))
                        reorder2Opt(child);
                        newPopulation.individuals[i] = child;
                    }
                }
            }
        }

        // Mutate the new population a bit to add some new genetic material
        for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
            if (newPopulation.individuals[i].multiChoromosome.values().size() > 0) {
                mutate(newPopulation.individuals[i]);
            }
        }

        return newPopulation;
    }

    public static boolean isPopulationContains(MultiChromosome child, Population newPopulation) {
        for (MultiChromosome multiChromosome : newPopulation.individuals) {
            if (multiChromosome.multiChoromosome.size() > 0 && !multiChromosome.equals(child)) {
                return true;
            }
        }
        return false;
    }

    // Applies crossover to a set of parents and creates offspring
    public static MultiChromosome crossover(MultiChromosome multiParent) {

        MultiChromosome newChromosome = new MultiChromosome();

        int parent1Id = 0;
        //bu kısımda seçilecek ilk kromozomcugun dolu olup olmadığını kontrol ediyoruz
        while (true) {
            int tempId = rn.nextInt(Utils.numberOfUAVs());
            if (multiParent.multiChoromosome.containsKey(tempId)) {
                parent1Id = tempId;
                break;
            }

        }
        int parent2Id = 0;
        //bu kısımda seçilecek ikinci kromozomcugun dolu olup olmadığını kontrol ediyoruz
        do {
            int tempId = rn.nextInt(Utils.numberOfUAVs());
            if (!multiParent.multiChoromosome.containsKey(tempId)) {
                continue;
            }

            if (parent1Id != tempId) {
                parent2Id = tempId;
                break;
            }

        } while (true);

        ArrayList<Task> parent1 = multiParent.multiChoromosome.get(parent1Id);
        ArrayList<Task> parent2 = multiParent.multiChoromosome.get(parent2Id);
        if (parent1 == null || parent2 == null) {
            System.out.println("null");
        }
        int parent1size = parent1.size();
        int parent2size = parent2.size();
        if (parent1 != null && parent2 != null) {
            //System.out.println("parent1Id :" + parent1.size() + " parent2Id :" + parent2.size());
            if (parent1size > Utils.maxTaskAssignment - 2) {
                parent1size -= 2;
            }
            if (parent2size > Utils.maxTaskAssignment - 2) {
                parent2size -= 2;
            }
            int split1 = (int) rn.nextDouble() * parent1size;
            int split2 = (int) rn.nextDouble() * parent2size;
            // System.out.println("split1 :" + split1 + " split2 :" + split2);

            do {
                if (((parent2size - split2) + split1) <= Utils.maxTaskAssignment
                        && ((parent1size - split1) + split2) <= Utils.maxTaskAssignment) {
                    break;
                }
                split1 = (int) rn.nextDouble() * parent1size;
                split2 = (int) rn.nextDouble() * parent2size;
            } while (true);
            for (int i = 0; i < Utils.numberOfUAVs(); i++) {
                if (parent1Id == i) {
                    ArrayList<Task> newCityList = new ArrayList<Task>();
                    for (int j = 0; j < split1; j++) {
                        newCityList.add(parent1.get(j));
                    }
                    for (int j = split2; j < parent2.size(); j++) {
                        newCityList.add(parent2.get(j));
                    }
                    newChromosome.multiChoromosome.put(i, newCityList);

                } else if (parent2Id == i) {
                    ArrayList<Task> newCityList = new ArrayList<Task>();
                    for (int j = 0; j < split2; j++) {
                        newCityList.add(parent2.get(j));
                    }
                    for (int j = split1; j < parent1.size(); j++) {
                        newCityList.add(parent1.get(j));
                    }
                    newChromosome.multiChoromosome.put(i, newCityList);

                } else {
                    if (multiParent.multiChoromosome.containsKey(i)) {
                        newChromosome.multiChoromosome.put(i, multiParent.multiChoromosome.get(i));
                    }

                }
            }
        }
        if (newChromosome.getFitness() < 1) {
            // System.out.println("child.getFitness() : " + newChromosome.getFitness());
        }
        return newChromosome;
    }

    // Mutate a tour using swap mutation
    private static void mutate(MultiChromosome multiChromosome) {
        // Loop through tour cities

        int randomChrom = rn.nextInt(Utils.numberOfUAVs() - 1) + 1;

        if (multiChromosome.multiChoromosome.containsKey(randomChrom)
                && multiChromosome.multiChoromosome.get(randomChrom) != null) {
            ArrayList<Task> appliedChoromosome = multiChromosome.multiChoromosome.get(randomChrom);

            for (int tourPos1 = 0; tourPos1 < appliedChoromosome.size(); tourPos1++) {
                // Apply mutation rate
                if (Math.random() < mutationRate) {
                    // Get a second random position in the tour
                    int tourPos2 = rn.nextInt(appliedChoromosome.size());

                    // Get the cities at target position in tour
                    Task city1 = multiChromosome.multiChoromosome.get(randomChrom).get(tourPos1);
                    Task city2 = multiChromosome.multiChoromosome.get(randomChrom).get(tourPos2);

                    // Swap them around
                    multiChromosome.multiChoromosome.get(randomChrom).set(tourPos2, city1);
                    multiChromosome.multiChoromosome.get(randomChrom).set(tourPos1, city2);
                }
            }
        }
    }

    // Selects candidate tour for crossover
    private static MultiChromosome tournamentSelection(Population pop) {
        // Create a tournament population
        Population tournament = new Population(tournamentSize, false);
        // For each place in the tournament get a random candidate tour and
        // add it
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.populationSize());
            tournament.individuals[i] = pop.individuals[randomId];
        }
        // Get the fittest tour
        MultiChromosome fittest = tournament.getFittest();//seçilenlerin arasındaki en iyi olanını seç
        return fittest;
    }

    /**
     * child optimization
     */
    public static void reorder2Opt(MultiChromosome multiChromosome) {
        boolean done = false;
        for (ArrayList<Task> tour : multiChromosome.multiChoromosome.values()) {
            int numOfTasks = tour.size();
            for (int k = 0; k < numOfTasks && !done; k++) {
                done = true;
                for (int i = 0; i < numOfTasks; i++) {
                    for (int j = i + 2; j < numOfTasks; j++) {
                        if (tour.get(i).distanceTo(tour.get(((i + 1) % numOfTasks)))
                                + tour.get(j).distanceTo(tour.get(((j + 1) % numOfTasks)))
                                > tour.get(i).distanceTo(tour.get(j))
                                + tour.get(((i + 1) % numOfTasks)).distanceTo(tour.get(((j + 1) % numOfTasks)))) {
                            Task tmp = tour.get(((i + 1) % numOfTasks));
                            tour.set((i + 1) % numOfTasks, tour.get(j));
                            tour.set(j, tmp);
// tsp_ga.onNewTourReady(tour);
                            exchange(tour, i + 2, j - 1);
                            done = false;
                        }
                    }
                }
            }
        }

    }

    private static void exchange(ArrayList<Task> tasks, int from, int To) {
        if (from >= To || from >= tasks.size() || To < 0) {
            return;
        }
        for (; from < To; To--) {
            Task tmp = tasks.get(from);
            tasks.set(from, tasks.get(To));
            tasks.set(To, tmp);
            from++;
        }
    }
}
