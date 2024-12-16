package fr.eql.ai116.team.linus.annuaire.model.enums;


public enum Statut {

    ///  ATTRIBUTS ///

    ADMINISTRATOR("Administrateur"),
    SUPERADMINISTRATOR("Super Administrateur");


    private final String statut;
    ///  CONSTRUCTORS ///

    Statut(String statut) {
        this.statut = statut;
    }

    ///  GETTERS ///

    public String getStatut() {
        return statut;
    }

}
