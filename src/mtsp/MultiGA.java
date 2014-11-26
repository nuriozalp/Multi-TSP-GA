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
    private static final int tournamentSize = 5;
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
            System.out.println("pop.getFittest() : " + pop.getFittest().getFitness());
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
                        System.out.println("child.getFitness() : " + child.getFitness());
                    }
                    newPopulation.individuals[i] = child;
                }
            }
        }

        // Mutate the new population a bit to add some new genetic material
        for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
            mutate(newPopulation.individuals[i]);
        }

        return newPopulation;
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
        if (parent1 != null && parent2 != null) {
            //System.out.println("parent1Id :" + parent1.size() + " parent2Id :" + parent2.size());

            int split1 = rn.nextInt(parent1.size());
            int split2 = rn.nextInt(parent2.size());

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
            System.out.println("child.getFitness() : " + newChromosome.getFitness());
        }
        return newChromosome;
    }

    // Mutate a tour using swap mutation
    private static void mutate(MultiChromosome multiChromosome) {
        // Loop through tour cities

        int randomChrom = rn.nextInt(Utils.numberOfUAVs());
        if (multiChromosome.multiChoromosome.get(randomChrom) != null) {
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
}
