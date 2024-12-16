package fr.eql.ai116.team.linus.annuaire.model.program;

import fr.eql.ai116.team.linus.annuaire.model.entity.Stagiaire;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Java class for add stagiaire into a list of stagiaire
 */
public class StagiairesSorter {


    private static final Logger logger = LogManager.getLogger();

    private static final String FOLDER = "resources/";
    private static final String FILE = "stagiaires.txt";

    public static void main(String[] args) {

        stagiairesListGenerator();
    }

    /**
     * Read stagiaires.txt then add trainee information in object for add them in the list
     * @return stagiaireList
     */
    public static List<Stagiaire> stagiairesListGenerator() {


        List<Stagiaire> stagiairesList = new ArrayList<>();

        try {

            FileReader fr = new FileReader(FOLDER + FILE);
            BufferedReader br = new BufferedReader(fr);


            while (br.ready()) {
                String promotion = br.readLine();
                int year = Integer.parseInt(br.readLine());
                String lastName = br.readLine();
                String firstName = br.readLine();
                int department = Integer.parseInt(br.readLine());
                br.readLine();

                Stagiaire stagiaire = new Stagiaire(firstName, lastName, promotion, year, department);

                stagiairesList.add(stagiaire);

            }

        } catch (FileNotFoundException e) {
            logger.error("Unable to open the " + FILE, e);
        } catch (IOException e) {
            logger.error("Unable to operate on the " + FILE, e);
        }

        return stagiairesList;

    }

}
