package fr.eql.ai116.team.linus.annuaire.model.program;

import fr.eql.ai116.team.linus.annuaire.model.entity.Stagiaire;

public class BinManager {

    public void importDataInBin(String inputFilePath) {

    }

    private String getNodeNameFromStagiaire(Stagiaire stagiaire) {
        return stagiaire.getPromotion() + "_" + stagiaire.getLastname() + "_" + stagiaire.getName();
    }

    private Stagiaire getStagiaireFromBinIndex(int binIndex) {
        return null;
    }

    public void addStagiaire(Stagiaire stagiaire) {

    }

    public void removeStagiaire(Stagiaire stagiaire) {

    }
}
