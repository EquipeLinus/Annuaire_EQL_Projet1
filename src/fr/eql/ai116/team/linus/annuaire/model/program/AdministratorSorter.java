package fr.eql.ai116.team.linus.annuaire.model.program;

import fr.eql.ai116.team.linus.annuaire.model.entity.Administrator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Java class for the administrators list
 */
public class AdministratorSorter {


    private static final Logger logger = LogManager.getLogger();

    private static final String FOLDER = "resources/";
    private static final String FILE = "administrators.bin";


    public static void main(String[] args) {

        addSuperAdminToFile("Super User","Super Password","Super Administrateur");
        //logger.info(getListAdmins().get(1).getStatut());

       createAdmin("a","b","Super User","Super Password");
       removeAdmin("a","Super User","Super Password");

    }

    private static void removeAdmin(String usernameChoiceDelete, String usernameCheck, String usernamePassword) {
//        File ressourcesFolder = new File(FOLDER);
//        ressourcesFolder.mkdir();
//
//        try(RandomAccessFile output = new RandomAccessFile(FILE,"rw")){
//
//            output.seek(output.length());
//            output.writeUTF(username);
//            output.writeUTF(password);
//            output.writeUTF(statut);
//
//        }
//        catch (FileNotFoundException e){
//            logger.error("The file was not found ");
//        }
//        catch (IOException e){
//            logger.error("L'écriture n'a pas marché ");
//        }
    }


    public static void createAdmin(String newAdminUsername , String newAdminPassword,String usernameCheck, String usernamePassword){

        if(checkLogs(usernameCheck,usernamePassword).getStatut().equals("Super Administrateur")){
            System.out.println(checkIfAdminExists(getListAdmins(),newAdminUsername) );

            if(checkIfAdminExists(getListAdmins(),newAdminUsername) == null){
                addUsernamesToFile(newAdminUsername, newAdminPassword, "Administrateur");
                logger.info("The user has been created");
            }
           else {
                logger.info("The user with this username exist ");
            }

        }
        else {
            logger.info("Only the Super Admin can create administrators");
        }
    }
    /**
     * For check if the logs for the connexion are good
     */
    public static Administrator checkLogs(String usernameWanted , String password) {
        Administrator admin = checkIfAdminExists(getListAdmins(),usernameWanted);
        if ( admin != null){
            logger.info("L'administrateur existe");
            if(admin.getPassword().equals(password)){

                if(admin.getStatut().equals("Super Administrateur")){
                    return admin;
                }
                else if(admin.getStatut().equals("Administrateur")){
                    return admin;
                }
            }
            else {
                logger.info("Le mot de passe n'est pas bon");
            }
        }
        else {
            logger.info("L'administrateur n'existe pas");
        }

        return null;
    }

    /**
     * For check if the input admin exist
     */
    private static Administrator checkIfAdminExists(List<Administrator> listAdmins, String usernameWanted) {

        for (Administrator admin : listAdmins) {
            if(admin.getUsername().equals(usernameWanted)){
                return admin;
            }
        }
        return null;

    }


    /**
     * Add one username to the binary file administrator
     */
    public static void addUsernamesToFile(String username, String password, String statut) {

        File ressourcesFolder = new File(FOLDER);
        ressourcesFolder.mkdir();

        try(RandomAccessFile output = new RandomAccessFile(FILE,"rw")){

            output.seek(output.length());
            output.writeUTF(username);
            output.writeUTF(password);
            output.writeUTF(statut);

        }
        catch (FileNotFoundException e){
            logger.error("The file was not found ");
        }
        catch (IOException e){
            logger.error("L'écriture n'a pas marché ");
        }

    }

    /**
     * For add the super admin in the file
     * @param username
     * @param password
     * @param statut
     */

    private static void addSuperAdminToFile(String username, String password, String statut) {
        addUsernamesToFile(username,password, statut);
    }
    /**
     * Read the binary file administrator
     * @return List of administrastor object
     */
    public static List<Administrator> getListAdmins(){

        List<Administrator> administratorsList = new ArrayList<>();
        try (DataInputStream input = new DataInputStream(new FileInputStream(FILE))){
            while (true){
                try {
                    String user = input.readUTF();
                    String password = input.readUTF();
                    String statut = input.readUTF();
                    Administrator administrator = new Administrator(user,password,statut);
                    administratorsList.add(administrator);
                }
                catch (EOFException e){
                    break;
                }
            }

        }
        catch (FileNotFoundException e) {
            logger.error("Unable to create " + FILE, e);
        }
        catch (IOException e) {
            logger.error("Unable to operate on the " + FILE, e);
        }
        return administratorsList;
    }


}
