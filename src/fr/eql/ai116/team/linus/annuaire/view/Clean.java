package fr.eql.ai116.team.linus.annuaire.view;

public class Clean {

    public static String cleanFirstName(String stringToClean) {
        String word =  String.valueOf(stringToClean.charAt(0)).toUpperCase() +
                stringToClean.substring(1).toLowerCase();
        return word.trim();
    }

    public static String cleanLastName(String stringToClean) {
        return stringToClean.toUpperCase().trim();
    }

    public static String cleanPromo(String stringToClean) {
        return stringToClean.toUpperCase().trim();
    }
}
