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
        bManager.addStagiaire(new Stagiaire("jean3", "toto3", "BO05",2024,94));
        bManager.addStagiaire(new Stagiaire("jean2", "toto2", "BO05",2024,94));

        System.out.println();
        List<Stagiaire> stagiaireDeAI116 = bManager.searchPromo("ai116",0, new ArrayList<Stagiaire>());
        System.out.println(stagiaireDeAI116);
        System.out.println();
        System.out.println(bManager.searchPromo("BO05",0, new ArrayList<Stagiaire>()));


        System.out.println();
        System.out.println(bManager.getIDFromNodeIndex(bManager.searchNodeParent("ai116_ouahioune_mazir",0)));
        System.out.println(bManager.getIDFromNodeIndex(bManager.searchNodeParent("ai116_schuller_andras",0)));

        System.out.println();
        bManager.display(0);
        bManager.removeStagiaire("ai116_castillo_miroslava");
        System.out.println("----");
        bManager.display(0);

        System.out.println();
        bManager.display(0);
        System.out.println("Parent of toto: " + bManager.getIDFromNodeIndex(bManager.searchNodeParent("BO05_toto1_jean1",0)));

        bManager.removeStagiaire("ai116_schuller_andras");
        System.out.println("----");
        bManager.display(0);

        System.out.println("Parent of toto: " + bManager.getIDFromNodeIndex(bManager.searchNodeParent("BO05_toto1_jean1",0)));
    }

    public void importDataInBin(List<Stagiaire> allStagiaire) {
        for (Stagiaire stagiaire : allStagiaire) {
            addStagiaire(stagiaire);
        }
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

    //region PUBLIC_FUNCTION
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

                currentNodeIndex = comparison <= 0? getRightNodeIndexFromNodeIndex(parentNodeIndex): getLeftNodeIndexFromNodeIndex(parentNodeIndex);

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

    public void removeStagiaire(String ID) {
        removeStagiaire(searchStagiaireAndGetIndex(ID,0));
    }

    public void removeStagiaire(long indexToRemove) {
        int childCount = getChildCount(indexToRemove);

        switch (childCount) {
            default:
                removeNode_ZeroChildren(indexToRemove);
                break;
            case 1:
                removeNode_OneChildren(indexToRemove);
                break;
            case 2:
                removeNode_TwoChildren();
                break;
        }
    }
    //endregion
    //region UTILITY
    /**
     * Concataine les données du stagiaire pour créer un ID.
     * @param stagiaire
     * @return
     */
    private String getStagiaireId(Stagiaire stagiaire) {
        return stagiaire.getPromotion() + "_" + stagiaire.getLastName() + "_" + stagiaire.getFirstName();
    }

    void display(long nodeIndex) {
        if (getLeftNodeIndexFromNodeIndex(nodeIndex) != -1) display(getLeftNodeIndexFromNodeIndex(nodeIndex));
        System.out.println(getIDFromNodeIndex(nodeIndex));
        if (getRightNodeIndexFromNodeIndex(nodeIndex) != -1) display(getRightNodeIndexFromNodeIndex(nodeIndex));
    }
    //endregion
    //region CHILDS/PARENT
    private long[] getNodeChildren(long nodeIndex) {
        return new long[]{ getLeftNodeIndexFromNodeIndex(nodeIndex), getRightNodeIndexFromNodeIndex(nodeIndex)};
    }

    private int getChildCount(long nodeIndex) {
        int amount = 0;
        long[] childs = getNodeChildren(nodeIndex);
        for (long child : childs) {
            if (child != -1) amount++;
        }
        return amount;
    }
    //endregion
    //region REMOVING_METHODS
    private void removeNode_ZeroChildren(long indexToRemove) {
        long parentIndex = searchNodeParent(getIDFromNodeIndex(indexToRemove),0);

        long[] childs = getNodeChildren(parentIndex);
        for (int i = 0; i < childs.length; i++) {
            if (childs[i] == indexToRemove) {

                try (RandomAccessFile raf = new RandomAccessFile(BIN_PATH, "rw")) {

                    raf.seek(parentIndex);
                    raf.readUTF();
                    if (i == 1) raf.readLong();
                    raf.writeLong(-1);

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void removeNode_OneChildren(long indexToRemove) {
        long parentIndex = searchNodeParent(getIDFromNodeIndex(indexToRemove),0);

        long[] childsOfParent = getNodeChildren(parentIndex);
        for (int i = 0; i < childsOfParent.length; i++) {
            if (childsOfParent[i] == indexToRemove) {

                try (RandomAccessFile raf = new RandomAccessFile(BIN_PATH, "rw")) {

                    raf.seek(parentIndex);
                    raf.readUTF();
                    if (i == 1) raf.readLong();

                    long[] childsOfChild = getNodeChildren(indexToRemove);
                    for (long l : childsOfChild) {
                        if (l != -1) raf.writeLong(l);
                    }

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void removeNode_TwoChildren() {

    }
//endregion
    //region SEARCHING_METHODS
    /**
     * Fonction qui cherche un stagiaire et qui renvois ces datas
     * @param ID
     * @param currentIndex
     * @return
     */
    private Stagiaire searchStagiaire(String ID, long currentIndex) {

        Long indexFounded = searchStagiaireAndGetIndex(ID, currentIndex);
        if (indexFounded != null) return getStagiaireAtNodeIndex(indexFounded);
        else return null;
    }

    /**
     * fonction récursive qui recherche une node, et qui continue la recherche dans la bonne direction.
     * @param ID
     * @param currentIndex
     * @return
     */
    private Long searchStagiaireAndGetIndex(String ID, long currentIndex) {
        System.out.println("Searching for " + ID + ", currently at: " + getIDFromNodeIndex(currentIndex));

        try {
            int comparison = ID.compareToIgnoreCase(getIDFromNodeIndex(currentIndex)); // ID est supérieur ou inférieur à l'ID de currentIndex ?
            if (comparison == 0) {
                return currentIndex;
            } else if (comparison < 0) {
                return searchStagiaireAndGetIndex(ID, getLeftNodeIndexFromNodeIndex(currentIndex));
            } else {
                return searchStagiaireAndGetIndex(ID, getRightNodeIndexFromNodeIndex(currentIndex));
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
        System.out.println(getIDFromNodeIndex(currentIndex));

        // On regarde si le currentID commence par promoID
        if (getIDFromNodeIndex(currentIndex).startsWith(promoID)) {

            if (getLeftNodeIndexFromNodeIndex(currentIndex) != -1)
                searchPromo(promoID, getLeftNodeIndexFromNodeIndex(currentIndex),currentStagiaireFounded);

            currentStagiaireFounded.add(getStagiaireAtNodeIndex(currentIndex));

            if (getRightNodeIndexFromNodeIndex(currentIndex) != -1)
                searchPromo(promoID, getRightNodeIndexFromNodeIndex(currentIndex),currentStagiaireFounded);

        } else {
            if (promoID.compareToIgnoreCase(getIDFromNodeIndex(currentIndex)) < 0) {
                if (getLeftNodeIndexFromNodeIndex(currentIndex) != -1)
                    searchPromo(promoID, getLeftNodeIndexFromNodeIndex(currentIndex),currentStagiaireFounded);
            } else {
                if (getRightNodeIndexFromNodeIndex(currentIndex) != -1)
                    searchPromo(promoID, getRightNodeIndexFromNodeIndex(currentIndex),currentStagiaireFounded);
            }
        }

        return currentStagiaireFounded;
    }

    private Long searchNodeParent(String IDToFind, long currentIndex) {
        int comparison = IDToFind.compareToIgnoreCase(getIDFromNodeIndex(currentIndex)); // ID est supérieur ou inférieur à l'ID de currentIndex ?

        long childIndex;
        if (comparison < 0) {
            childIndex = getLeftNodeIndexFromNodeIndex(currentIndex);
        } else {
            childIndex = getRightNodeIndexFromNodeIndex(currentIndex);
        }

        //System.out.println("TESTING CHILD OF " + getIDFromIndex(currentIndex) + ": " + getIDFromIndex(childIndex));
        if (childIndex == -1) return null;

        if (getIDFromNodeIndex(childIndex).equals(IDToFind)) {
            return currentIndex;
        } else {
            return searchNodeParent(IDToFind, childIndex);
        }
    }
//endregion
    //region GET_DATA_FROM_NODE
    public long getLeftNodeIndexFromNodeIndex(long nodeIndex) {
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

    public long getRightNodeIndexFromNodeIndex(long nodeIndex) {
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

    public String getIDFromNodeIndex(long nodeIndex) {
        try (RandomAccessFile raf = new RandomAccessFile(BIN_PATH, "rw")) {
            raf.seek(nodeIndex);
            return raf.readUTF();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //endregion
}
