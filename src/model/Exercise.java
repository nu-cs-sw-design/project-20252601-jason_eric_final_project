public class Exercise {

    private final String name;
    private final int sets;
    private final int reps;
    private final double weight;

    public Exercise(String name, int sets, int reps, double weight) {
        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return name + " (" + sets + "x" + reps + " @" + weight + "lbs)";
    }
}
