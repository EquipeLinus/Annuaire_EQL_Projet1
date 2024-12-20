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

//      addAdministratorToFile("Ad","Dors","Administrateur");
          addAdministratorToFile("re","Rastors","Administrateur");
//        logger.info(getListAdmins());

        //modifyAdministrator("Adk","Adk", "Dorsee","Administrateur");

    }

    public static void modifyAdministrator(String OldUsername ,String NewUsername,String NewPassword, String statut) {
        removeAdministrator(OldUsername);
        addAdministratorToFile(NewUsername,NewPassword,statut);
    }

    private static void removeAdministrator(String usernameWanted) {

        String username ="";
        int nextValue = 0;
        int previousValue = 0;

        try (RandomAccessFile output = new RandomAccessFile(FILE,"rw")){

            do {
                output.readInt();
                previousValue = output.readInt();
                nextValue = output.readInt();
                username = output.readUTF();
                if(nextValue != -1){
                    output.seek(nextValue);
                }

                logger.info("Username : " + username + " | Username Wanted" + usernameWanted + " | Next value : " +nextValue +" | Previous value : " + previousValue);
            }while (!usernameWanted.equals(username));
            System.out.println("sortie");

            if (nextValue == -1){
                output.seek(previousValue);
                output.readInt();
                output.readInt();
                output.writeInt(nextValue);

            }
            else {
                output.seek(previousValue);
                output.readInt();
                output.readInt();
                output.writeInt(nextValue);

                output.seek(nextValue);
                output.readInt();
                output.writeInt(previousValue);

            }


        }
        catch (FileNotFoundException e) {
            logger.error("Unable to create " + FILE, e);
        }
        catch (IOException e) {
            logger.error("Unable to operate on the " + FILE, e);
        }

    }

    public static List<Administrator> getListAdmins() {
        List<Administrator> administratorsList = new ArrayList<>();
        try (RandomAccessFile input = new RandomAccessFile(FILE,"rw")){

                int value = 0;
                do {
                    input.seek(value);
                    int index = input.readInt();
                    int oldValue = input.readInt();
                    value = input.readInt();
                    String user = input.readUTF();
                    String password = input.readUTF();
                    String statut = input.readUTF();
                    Administrator administrator = new Administrator(user,password,statut);
                    administratorsList.add(administrator);

                }while (value !=-1);


        }
        catch (FileNotFoundException e) {
            logger.error("Unable to create " + FILE, e);
        }
        catch (IOException e) {
            logger.error("Unable to operate on the " + FILE, e);
        }
        return administratorsList;
    }

    private static void addSuperAdminToFile() {
        File ressourcesFolder = new File(FOLDER);
        ressourcesFolder.mkdir();

        try(RandomAccessFile output = new RandomAccessFile(FILE,"rw")){
            output.writeInt(0);
            output.writeInt(-1);
            output.writeInt(-1);
            output.writeUTF("Super User");
            output.writeUTF("Super Password");
            output.writeUTF("Super Administrateur");

        }
        catch (FileNotFoundException e){
            logger.error("The file was not found ");
        }
        catch (IOException e){
            logger.error("L'écriture n'a pas marché ");
        }
    }


    public static void addAdministratorToFile( String username, String password, String statut) {

        File ressourcesFolder = new File(FOLDER);
        ressourcesFolder.mkdir();
        if(checkIfAdminExists(getListAdmins() , username) != null){
            logger.info("L'administrateur existe déjà");
            return;
        }

        try(RandomAccessFile output = new RandomAccessFile(FILE,"rw")){


           if(output.length()==0){
               addSuperAdminToFile();
           }


           output.readInt();
           output.readInt();
           int value = output.readInt();

           int oldvalue =0;


           while (value!= -1){

               output.seek(value);
               oldvalue =output.readInt();
               output.readInt();
               value = output.readInt();

           }

           // Ecrire sur l'ancienne node pour la ratacher à la nouvelle

           output.seek(oldvalue);
           output.readInt();
           output.readInt();
           output.writeInt((int) output.length());


           // Ecrire la node

           output.seek((int) output.length());
           output.writeInt((int) output.length());
           output.writeInt(oldvalue);
           output.writeInt(-1);
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

    public static Administrator checkLogs(String usernameWanted , String password) {
        Administrator admin = checkIfAdminExists(getListAdmins(),usernameWanted);
        if ( admin != null){
            logger.info("L'administrateur existe");
            System.out.println(admin.getPassword() + "|" + password);
            if(admin.getPassword().equals(password)){
                return admin;
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
            System.out.println(usernameWanted + "  " +admin.getUsername());
            if(admin.getUsername().equals(usernameWanted)){

                return admin;
            }
        }
        return null;

    }


}