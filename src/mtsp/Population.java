/*
 * Population.java
 * Manages a population of candidate tours
 */
package mtsp;

public class Population {

    // Holds population of tours
    MultiChromosome[] individuals;

    // Construct a population
    public Population(int populationSize, boolean initialise) {

        individuals = new MultiChromosome[populationSize];
        // If we need to initialise a population of tours do so
        if (initialise) {
            // Loop and create individuals
            for (int i = 0; i < populationSize; i++) {
                MultiChromosome newChromosome = new MultiChromosome();
                newChromosome.generateMultiIndividual();
                newChromosome.getFitness();
                individuals[i] = newChromosome;

            }
        }
    }

    // Gets the best tour in the population
    public MultiChromosome getFittest() {
        MultiChromosome fittest = individuals[0];
        if (fittest != null) {
            // Loop through individuals to find fittest
            for (int i = 1; i < populationSize(); i++) {
                if (fittest.getFitness() >= individuals[i].getFitness()) {
                    fittest = individuals[i];
                }
            }
        }
        return fittest;
    }

    // Gets population size
    public int populationSize() {
        return individuals.length;
    }
}
