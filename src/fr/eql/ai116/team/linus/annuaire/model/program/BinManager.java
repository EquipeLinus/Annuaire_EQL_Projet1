package fr.eql.ai116.team.linus.annuaire.model.program;

import fr.eql.ai116.team.linus.annuaire.model.entity.Stagiaire;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class BinManager {

    public static final String BIN_PATH = "resources/datas.bin";

    public static void main(String[] args) {
        BinManager bManager = new BinManager();
        bManager.setNodeAtIndex(new Stagiaire("thomas", "duron", "ai116",2024,93),0);

        System.out.println(bManager.getStagiaireAtNodeIndex(0));

        bManager.addStagiaire(new Stagiaire("mazir", "ouahioune", "ai116",2024,75));
        bManager.addStagiaire(new Stagiaire("andras", "schuller", "ai116",2024,19));
        bManager.addStagiaire(new Stagiaire("miroslava", "castillo", "ai116",2024,94));

        bManager.display(0);

        System.out.println();
        System.out.println(bManager.searchStagiaire("ai116_castillo_miroslava",0));

        bManager.addStagiaire(new Stagiaire("jean1", "toto1", "BO05",2024,94));
        bManager.addStagiaire(new Stagiaire("jean2", "toto2", "BO05",2024,94));
        bManager.addStagiaire(new Stagiaire("jean3", "toto3", "BO05",2024,94));

        System.out.println();
        List<Stagiaire> stagiaireDeAI116 = bManager.searchPromo("ai116",0, new ArrayList<Stagiaire>());
        System.out.println(stagiaireDeAI116);
        System.out.println(bManager.searchPromo("BO05",0, new ArrayList<Stagiaire>()));
    }

    public void importDataInBin(List<Stagiaire> allStagiaire) {
        for (Stagiaire stagiaire : allStagiaire) {
            addStagiaire(stagiaire);
        }
    }

    /**
     * Concataine les données du stagiaire pour créer un ID.
     * @param stagiaire
     * @return
     */
    private String getStagiaireId(Stagiaire stagiaire) {
        return stagiaire.getPromotion() + "_" + stagiaire.getLastName() + "_" + stagiaire.getFirstName();
    }

    /**
     * Va lire dans le fichie rbinaire à l'index donné. Retourne les données lues sous la forme d'un stagiaire.
     * @param binIndex L'index de la node qui contient les du stagiaire voulu
     * @return
     */
    private Stagiaire getStagiaireAtNodeIndex(long binIndex) {

        try (RandomAccessFile raf = new RandomAccessFile(BIN_PATH, "rw")) {

            raf.seek(binIndex);
            raf.readUTF();
            raf.readLong();
            raf.readLong();
            String firstName = raf.readUTF();
            String lastName = raf.readUTF();
            String promotion = raf.readUTF();
            int year = raf.readInt();
            int department = raf.readInt();
            return new Stagiaire(firstName,lastName,promotion,year,department);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * va écrire un stagiaire à l'index dans le fichier binaire.
     * @param stagiaire
     * @param binIndex
     */
    private void setNodeAtIndex(Stagiaire stagiaire, long binIndex) {

        try (RandomAccessFile raf = new RandomAccessFile(BIN_PATH, "rw")) {

            raf.seek(binIndex);
            raf.writeUTF(getStagiaireId(stagiaire));
            raf.writeLong(-1);
            raf.writeLong(-1);

            raf.writeUTF(stagiaire.getFirstName());
            raf.writeUTF(stagiaire.getLastName());
            raf.writeUTF(stagiaire.getPromotion());
            raf.writeInt(stagiaire.getYear());
            raf.writeInt(stagiaire.getDepartment());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Chercher dans l'arbre jusqu'à trouver la node parent. Puis, on lie ce parent à la dernière position
     * dans le fichier binaire. Ensuite, on crée la node du stagiaire à la dernière position dans le
     * fichier binaire.
     * @param stagiaire
     */
    public void addStagiaire(Stagiaire stagiaire) {
        try (RandomAccessFile raf = new RandomAccessFile(BIN_PATH, "rw")) {

            long currentNodeIndex = 0;
            while (true) {

                raf.seek(currentNodeIndex);
                String currentNodeId = raf.readUTF();

                int comparison = currentNodeId.compareToIgnoreCase(getStagiaireId(stagiaire));

                long parentNodeIndex = currentNodeIndex;

                currentNodeIndex = comparison <= 0? getNextIndexFromIndex(parentNodeIndex): getPreviousIndexFromIndex(parentNodeIndex);

                if (currentNodeIndex == -1) {
                    long newNodeIndex = raf.length();

                    raf.seek(parentNodeIndex);
                    raf.readUTF();
                    if (comparison <= 0) {
                        raf.readLong();
                    }
                    raf.writeLong(newNodeIndex);

                    setNodeAtIndex(stagiaire,newNodeIndex);
                    break;
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeStagiaire(Stagiaire stagiaire) {
        removeStagiaire(getStagiaireId(stagiaire));
    }

    public void removeStagiaire(String stagiaireID) {

    }

    void display(long nodeIndex) {
        if (getPreviousIndexFromIndex(nodeIndex) != -1) display(getPreviousIndexFromIndex(nodeIndex));
        System.out.println(getIDFromIndex(nodeIndex));
        if (getNextIndexFromIndex(nodeIndex) != -1) display(getNextIndexFromIndex(nodeIndex));
    }

    public long getPreviousIndexFromIndex(long nodeIndex) {
        try (RandomAccessFile raf = new RandomAccessFile(BIN_PATH, "rw")) {
            raf.seek(nodeIndex);
            raf.readUTF();
            return raf.readLong();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public long getNextIndexFromIndex(long nodeIndex) {
        try (RandomAccessFile raf = new RandomAccessFile(BIN_PATH, "rw")) {
            raf.seek(nodeIndex);
            raf.readUTF();
            raf.readLong();
            return raf.readLong();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getIDFromIndex(long nodeIndex) {
        try (RandomAccessFile raf = new RandomAccessFile(BIN_PATH, "rw")) {
            raf.seek(nodeIndex);
            return raf.readUTF();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * fonction récursive qui recherche une node, et qui continue la recherche dans la bonne direction.
     * @param ID
     * @param currentIndex
     * @return
     */
    private Stagiaire searchStagiaire(String ID, long currentIndex) {
        System.out.println(getIDFromIndex(currentIndex));

        try {
            int comparison = ID.compareToIgnoreCase(getIDFromIndex(currentIndex)); // ID est supérieur ou inférieur à l'ID de currentIndex ?
            if (comparison == 0) {
                return getStagiaireAtNodeIndex(currentIndex);
            } else if (comparison < 0) {
                return searchStagiaire(ID, getPreviousIndexFromIndex(currentIndex));
            } else {
                return searchStagiaire(ID, getNextIndexFromIndex(currentIndex));
            }
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * Recherche dans l'arbre de tout les ID commençant par la promoID.
     * La route dans l'arbre est déterminée par la comparaison du currentID avec la promoID.
     * @param promoID
     * @param currentIndex
     * @param currentStagiaireFounded
     * @return
     */
    private List<Stagiaire> searchPromo(String promoID, long currentIndex, List<Stagiaire> currentStagiaireFounded) {
        System.out.println(getIDFromIndex(currentIndex));

        // On regarde si le currentID commence par promoID
        if (getIDFromIndex(currentIndex).startsWith(promoID)) {

            if (getPreviousIndexFromIndex(currentIndex) != -1)
                searchPromo(promoID,getPreviousIndexFromIndex(currentIndex),currentStagiaireFounded);

            currentStagiaireFounded.add(getStagiaireAtNodeIndex(currentIndex));

            if (getNextIndexFromIndex(currentIndex) != -1)
                searchPromo(promoID,getNextIndexFromIndex(currentIndex),currentStagiaireFounded);

        } else {
            if (promoID.compareToIgnoreCase(getIDFromIndex(currentIndex)) < 0) {
                if (getPreviousIndexFromIndex(currentIndex) != -1)
                    searchPromo(promoID,getPreviousIndexFromIndex(currentIndex),currentStagiaireFounded);
            } else {
                if (getNextIndexFromIndex(currentIndex) != -1)
                    searchPromo(promoID,getNextIndexFromIndex(currentIndex),currentStagiaireFounded);
            }
        }

        return currentStagiaireFounded;
    }
}
