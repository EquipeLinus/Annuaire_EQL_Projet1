package fr.eql.ai116.team.linus.annuaire.model.program;

import fr.eql.ai116.team.linus.annuaire.model.entity.Stagiaire;

import java.util.List;

public class InterfaceSearch {

    private List<String> promotions;
    private String lastName;
    private String firstName;

    public List<Stagiaire> interFaceSearch (List<String> promotions, String lastName, String firstName ) {
        this.promotions = promotions;
        this.lastName = lastName;
        this.firstName = firstName;
        return null;
    }
}
