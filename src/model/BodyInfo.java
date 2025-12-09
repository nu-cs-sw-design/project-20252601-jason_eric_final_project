public class BodyInfo {

    private final int age;
    private final int heightInches;
    private final int weightPounds;
    private final String sex;

    public BodyInfo(int age, int heightInches, int weightPounds, String sex) {
        this.age = age;
        this.heightInches = heightInches;
        this.weightPounds = weightPounds;
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public int getHeightInches() {
        return heightInches;
    }

    public int getWeightPounds() {
        return weightPounds;
    }

    public String getSex() {
        return sex;
    }
}
