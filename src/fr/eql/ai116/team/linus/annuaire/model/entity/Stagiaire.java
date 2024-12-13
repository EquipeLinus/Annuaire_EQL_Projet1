package fr.eql.ai116.team.linus.annuaire.model.entity;

public class Stagiaire {
    private String name;
    private String lastname;
    private String promotion;
    private int year;
    private int department;

    public Stagiaire(String name, String lastname, String promotion, int year, int department) {
        this.name = name;
        this.lastname = lastname;
        this.promotion = promotion;
        this.year = year;
        this.department = department;
    }

    @Override
    public String toString() {
        return "Stagiaire{" +
                "name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", promotion='" + promotion + '\'' +
                ", year=" + year +
                ", department=" + department +
                '}';
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
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
