package fr.eql.ai116.team.linus.annuaire.model.entity;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Stagiaire stagiaire = (Stagiaire) o;
        return year == stagiaire.year && department == stagiaire.department && Objects.equals(firstName, stagiaire.firstName) && Objects.equals(lastName, stagiaire.lastName) && Objects.equals(promotion, stagiaire.promotion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, promotion, year, department);
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
