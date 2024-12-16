package fr.eql.ai116.team.linus.annuaire.model.entity;

public class Stagiaire {
    private String firstName;
    private String lastName;
    private String promotion;
    private int year;
    private int department;

    public Stagiaire(String firstName, String lastName, String promotion, int year, int department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.promotion = promotion;
        this.year = year;
        this.department = department;
    }

    @Override
    public String toString() {
        return "Stagiaire{" +
                "name='" + firstName + '\'' +
                ", lastname='" + lastName + '\'' +
                ", promotion='" + promotion + '\'' +
                ", year=" + year +
                ", department=" + department +
                '}';
    }

    public String getID() {
        return getPromotion() + "_" + getLastName() + "_" + getFirstName();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }
}
