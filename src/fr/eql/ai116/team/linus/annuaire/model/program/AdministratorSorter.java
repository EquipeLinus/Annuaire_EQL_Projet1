package fr.eql.ai116.team.linus.annuaire.model.program;

import fr.eql.ai116.team.linus.annuaire.model.entity.Administrator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
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

        String username = "Pierre";
        String password = "TRRâzmvkvopjavopvzjopzavjzovjozjopvzjzzopazvjavzovjazv";
        password = String.valueOf(password.hashCode());

        Administrator administrator = new Administrator(username, password);
        addAdministratorToFile(administrator.getUsername(), administrator.getPassword());

        checkLogs("Pierre","4281659");
    }

    /**
     * For check if the logs for the connexion are good
     * @param usernameWanted
     * @param password
     */
    private static void checkLogs(String usernameWanted , String password) {
        Administrator admin = checkIfAdminExists(getListAdmins(),usernameWanted);
        if ( admin != null){
            logger.info("L'administrateur existe");
            if(admin.getPassword().equals(password)){
                logger.info("Le username et le mot de passe sont bon");
            }
            else {
                logger.info("Le mot de passe n'est pas bon");
            }
        }
        else {
            logger.info("L'administrateur n'existe pas");
        }
    }

    public static boolean isAdmin(Administrator account) {
        if (account == null) return false;
        Administrator admin = checkIfAdminExists(getListAdmins(),account.getUsername());
        return admin != null && admin.getPassword().equals(account.getPassword());
    }

    /**
     * For check if the input admin exist
     * @param listAdmins
     * @param usernameWanted
     * @return
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
     * @param username
     * @param password
     */
    public static void addAdministratorToFile(String username, String password) {

        File ressourcesFolder = new File(FOLDER);
        ressourcesFolder.mkdir();

        try(RandomAccessFile output = new RandomAccessFile(FILE,"rw")){

            output.seek(output.length());
            output.writeUTF(username);
            output.writeUTF(password);

        }
        catch (FileNotFoundException e){
            logger.error("L'écriture n'a pas marché ");
        }
        catch (IOException e){
            logger.error("L'écriture n'a pas marché ");
        }

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
                    Administrator administrator = new Administrator(user,password);
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
