package fr.eql.ai116.team.linus.annuaire.model.program;

import fr.eql.ai116.team.linus.annuaire.model.entity.Stagiaire;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BinManager {

    public static final String BIN_PATH = "resources/datas.bin";
    private static final Logger log = LogManager.getLogger();

    private final RandomAccessFile raf;

    public static long root;

    public BinManager() throws IOException {
        raf = new RandomAccessFile(BIN_PATH, "rw");
        root = getRoot();
    }

    // Le main ici est temporaire, je m'en sert pour mes tests
    public static void main(String[] args) {

        try {
            BinManager bManager = new BinManager();
            bManager.clearFile();
            bManager.initialize();
            bManager.displayTree(root,0);
            bManager.removeStagiaire("KAPLA 12_KANAAN_Suhaila");
            bManager.displayTree(root,0);
            /*
            bManager.displayTree(0,0);

            //BinManager bManager = new BinManager();
            //bManager.clearFile();

            bManager.writeNodeAtIndex(new Stagiaire("thomas", "duron", "ai116", 2024, 93), 0);

            System.out.println(bManager.readStagiaireAtIndex(0));

            bManager.addStagiaire(new Stagiaire("mazir", "ouahioune", "ai116", 2024, 75));
            bManager.addStagiaire(new Stagiaire("andras", "schuller", "ai116", 2024, 19));
            bManager.addStagiaire(new Stagiaire("miroslava", "castillo", "ai116", 2024, 94));

            bManager.display(0);

            System.out.println();
            System.out.println(bManager.searchStagiaire("ai116_castillo_miroslava", 0));

            bManager.addStagiaire(new Stagiaire("jean2", "toto2", "BO05", 2024, 94));
            bManager.addStagiaire(new Stagiaire("jean1", "toto1", "BO05", 2024, 94));
            bManager.addStagiaire(new Stagiaire("jean3", "toto3", "BO05", 2024, 94));

            bManager.displayTree(0,0);

            System.out.println();
            List<Stagiaire> stagiaireDeAI116 = bManager.searchPromo("ai116", 0, new ArrayList<Stagiaire>());
            System.out.println(stagiaireDeAI116);
            System.out.println();
            System.out.println(bManager.searchPromo("BO05", 0, new ArrayList<Stagiaire>()));

            long[] miroslaveAndParent = bManager.searchCoupleWithID("ai116_castillo_miroslava", 0);

            System.out.println(bManager.getID(miroslaveAndParent[0]) + ", parent: " + bManager.getID(miroslaveAndParent[1]));
            System.out.println(bManager.getChildCount(miroslaveAndParent[0]));

            System.out.println();
            System.out.println("-- REMOVINGS:");

            bManager.displayTree(0,0);
            bManager.removeStagiaire("ai116_castillo_miroslava");
            bManager.displayTree(0,0);

            System.out.println();
            bManager.removeStagiaire("ai116_schuller_andras");
            bManager.displayTree(0,0);

            System.out.println();
            bManager.removeStagiaire("BO05_toto2_jean2");
            bManager.displayTree(0,0);

            bManager.modifyStagiaire("ai116_ouahioune_mazir", new Stagiaire("mazirrrrr", "ouahioune", "ai116", 2024, 75));
            bManager.displayTree(0,0);

             */

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize() throws IOException {

        List<Stagiaire> fetchedStagiaire = StagiairesSorter.stagiairesListGenerator();

        writeNodeAtIndex(fetchedStagiaire.get(0),root); //La root est write à la main
        for (int i = 1; i < fetchedStagiaire.size(); i++) {
            addStagiaire(fetchedStagiaire.get(i));
        }
    }

    /**
     *
     * Permet d'ajouter un stagiaire dans l'arbre binaire.
     *
     * @param stagiaire Le stagiaire à ajouter
     * @throws IOException
     */
    public boolean addStagiaire(Stagiaire stagiaire) throws IOException {

        long[] couple = searchCoupleWithID(stagiaire.getID(), root);
        if (couple[0] != -1) return false;

        long parent = couple[1];
        log.debug("Adding new stagiaire " + stagiaire.getID() + ", parent is " + getID(parent));
        if (isIDOnRight(getID(parent), stagiaire.getID())) {
            setRight(parent, raf.length());
        } else {
            setLeft(parent, raf.length());
        }
        writeNodeAtIndex(stagiaire, raf.length());
        log.debug("adding done");
        return true;
    }

    /**
     * Permet de supprimer un stagiaire de l'arbre binaire.
     * @param ID l'ID du stagiaire à supprimer
     * @throws IOException
     */
    public boolean removeStagiaire(String ID) throws IOException {
        long[] coupleToDelete = searchCoupleWithID(ID, root);
        if (coupleToDelete[0] == -1) return false;
        else {
            removeStagiaire(coupleToDelete);
            return true;
        }
    }

    public void modifyStagiaire(String IDoldStagiaire, Stagiaire newStagiaire) throws IOException {
        log.debug("modifying " + IDoldStagiaire + " to " + newStagiaire.getID());
        removeStagiaire(searchCoupleWithID(IDoldStagiaire, root));
        addStagiaire(newStagiaire);
        log.debug("modify done");
    }

    /**
     * Fonction qui cherche un stagiaire et qui renvois ces datas
     *
     * @param ID
     * @param currentIndex
     * @return
     */
    public Stagiaire searchStagiaire(String ID, long currentIndex) throws IOException {

        long indexFounded = searchIndexWithID(ID, currentIndex);
        if (indexFounded != -1) return readStagiaireAtIndex(indexFounded);
        else return null;
    }

    /**
     * Recherche dans l'arbre de tout les ID commençant par la promoID.
     * La route dans l'arbre est déterminée par la comparaison du currentID avec la promoID.
     *
     * @param promoID
     * @param currentIndex
     * @param currentStagiaireFounded
     * @return
     */
    public List<Stagiaire> searchPromo(String promoID, long currentIndex, List<Stagiaire> currentStagiaireFounded) throws IOException {

        // On regarde si le currentID commence par promoID
        if (getID(currentIndex).startsWith(promoID)) {

            if (getLeft(currentIndex) != -1)
                searchPromo(promoID, getLeft(currentIndex), currentStagiaireFounded);

            currentStagiaireFounded.add(readStagiaireAtIndex(currentIndex));

            if (getRight(currentIndex) != -1)
                searchPromo(promoID, getRight(currentIndex), currentStagiaireFounded);

        } else {
            if (promoID.compareToIgnoreCase(getID(currentIndex)) < 0) {
                if (getLeft(currentIndex) != -1)
                    searchPromo(promoID, getLeft(currentIndex), currentStagiaireFounded);
            } else {
                if (getRight(currentIndex) != -1)
                    searchPromo(promoID, getRight(currentIndex), currentStagiaireFounded);
            }
        }

        return currentStagiaireFounded;
    }

    public List<Stagiaire> getAll(long nodeIndex, List<Stagiaire> currentList) throws IOException {
        long leftNode = getLeft(nodeIndex);
        if (leftNode != -1) currentList = getAll(leftNode,currentList);

        currentList.add(readStagiaireAtIndex(nodeIndex));

        long rightNode = getRight(nodeIndex);
        if (rightNode != -1) currentList = getAll(rightNode,currentList);

        return currentList;
    }

    /**
     * Fonction qui recherche tout les stagiare et donne les promos
     * @return
     * @throws IOException
     */
    public Set<String> getAllPromos() throws IOException {
        Set<String> allPromos = new HashSet<>();

        List<Stagiaire> allStagiaires = getAll(root,new ArrayList<>());
        for (Stagiaire stagiaire : allStagiaires) {
            allPromos.add(stagiaire.getPromotion());
        }

        return allPromos;
    }

    //region WRITE_READ
    /**
     * Va lire dans le fichier binaire à l'index donné. Retourne les données lues sous la forme d'un stagiaire.
     *
     * @param binIndex L'index de la node qui contient les du stagiaire voulu
     * @return
     */
    private Stagiaire readStagiaireAtIndex(long binIndex) throws IOException {

        raf.seek(binIndex);
        raf.readUTF();
        raf.readLong();
        raf.readLong();
        String firstName = raf.readUTF();
        String lastName = raf.readUTF();
        String promotion = raf.readUTF();
        int year = raf.readInt();
        int department = raf.readInt();
        return new Stagiaire(firstName, lastName, promotion, year, department);
    }

    /**
     * va écrire une node sans enfant qui contient les données du stagiaire à l'index dans le fichier binaire.
     *
     * @param stagiaire
     * @param binIndex
     */
    private void writeNodeAtIndex(Stagiaire stagiaire, long binIndex) throws IOException {

        raf.seek(binIndex);
        raf.writeUTF(stagiaire.getID());
        raf.writeLong(-1);
        raf.writeLong(-1);

        raf.writeUTF(stagiaire.getFirstName());
        raf.writeUTF(stagiaire.getLastName());
        raf.writeUTF(stagiaire.getPromotion());
        raf.writeInt(stagiaire.getYear());
        raf.writeInt(stagiaire.getDepartment());
    }
    //endregion
    //region UTILITY

    /**
     * Fait des souts qui va lire l'arbre dans l'ordre
     * @param nodeIndex
     * @throws IOException
     */
    private void display(long nodeIndex) throws IOException {

        long leftNode = getLeft(nodeIndex);
        if (leftNode != -1) display(leftNode);

        System.out.println(getID(nodeIndex));

        long rightNode = getRight(nodeIndex);
        if (rightNode != -1) display(rightNode);
    }

    /**
     * Fait des souts qui va lire les nodes
     * @param nodeIndex
     * @throws IOException
     */
    public void displayTree(long nodeIndex, int deepness) throws IOException {

        for (int i = 0; i < deepness; i++) {
            System.out.print("  ");
        }
        System.out.println(getID(nodeIndex) + "(" + nodeIndex+ ")");

        long leftNode = getLeft(nodeIndex);
        if (leftNode != -1) displayTree(leftNode, deepness + 1);

        long rightNode = getRight(nodeIndex);
        if (rightNode != -1) displayTree(rightNode, deepness + 1);
    }

    /**
     * permet de supprimer tout ce qu'il y a dans le fichier binaire
     * @throws IOException
     */
    public void clearFile() throws IOException {
        new FileOutputStream(BIN_PATH).close();
        root = getRoot();
    }

    /**
     * Test pour savoir si IDChild est à droite de IDparent dans l'arbre
     * @param IDparent
     * @param IDchild
     * @return
     */
    private boolean isIDOnRight(String IDparent, String IDchild) {
        int comparison = IDparent.compareToIgnoreCase(IDchild);
        return comparison <= 0;
    }

    /**
     * Permet d'inverser les parents et les enfants de deux nodes dans l'arbre.
     * @param coupleA Couple node-parent de la node A
     * @param coupleB Couple node-parent de la node B
     * @throws IOException
     */
    private void inverseConnexions(long[] coupleA, long[] coupleB) throws IOException {

        List<Long> allIndexs = new ArrayList<>();
        allIndexs.add(coupleA[0]);
        for (long l : getChilds(coupleA[0])) {
            allIndexs.add(l);
        }
        if (coupleA[1] != -1) { // Est potentiellement la root ?
            allIndexs.add(coupleA[1]);
            for (long l : getChilds(coupleA[1])) {
                allIndexs.add(l);
            }
        }
        allIndexs.add(coupleB[0]);
        for (long l : getChilds(coupleB[0])) {
            allIndexs.add(l);
        }
        allIndexs.add(coupleB[1]);
        for (long l : getChilds(coupleB[1])) {
            allIndexs.add(l);
        }

        for (int i = 0; i < allIndexs.size(); i++) {
            if (allIndexs.get(i) == coupleA[0]) allIndexs.set(i,coupleB[0]);
            else if (allIndexs.get(i) == coupleB[0]) allIndexs.set(i,coupleA[0]);
        }

        for (int i = 0; i < allIndexs.size()/3; i++) {
            long current = allIndexs.get((i*3));
            long right = allIndexs.get((i*3)+1);
            long left = allIndexs.get((i*3)+2);

            setChilds(current, new long[]{right, left});
        }

        if (coupleA[1] == -1) {
            setRoot(coupleB[0]);
        }
    }
    //endregion
    //region REMOVING_METHODS

    /**
     * Permet de choisir quel method appeler lors du déréférencement d'une node
     * @param couple
     * @throws IOException
     */
    private void removeStagiaire(long[] couple) throws IOException {
        log.debug("Trying to remove " + getID(couple[0]) + " (" + couple[0] + ")");
        int childCount = getChildCount(couple[0]);

        switch (childCount) {
            default:
                log.debug("0 child");
                removeNode_ZeroChildren(couple);
                break;
            case 1:
                log.debug("1 child");
                removeNode_OneChildren(couple);
                break;
            case 2:
                log.debug("2 childs");
                removeNode_TwoChildren(couple);
                break;
        }
        log.debug("Removing done");
    }

    /**
     * Méthode pour déréférencer une node sans enfant
     * @param couple
     * @throws IOException
     */
    private void removeNode_ZeroChildren(long[] couple) throws IOException {

        if (couple[1] == -1) {
            setRoot(Long.BYTES);
        } else {
            if (getRight(couple[1]) == couple[0]) {
                setRight(couple[1],-1);
            } else {
                setLeft(couple[1],-1);
            }
        }
    }

    /**
     * Méthode pour déréférencer une node avec 1 enfant
     * @param couple
     * @throws IOException
     */
    private void removeNode_OneChildren(long[] couple) throws IOException {

        long[] childOfNodeToRemove = getChilds(couple[0]);
        long goodChild = childOfNodeToRemove[0]==-1?childOfNodeToRemove[1]:childOfNodeToRemove[0];

        if (couple[1] == -1) {
            setRoot(goodChild);
        } else {
            if (getRight(couple[1]) == couple[0]) {
                setRight(couple[1],goodChild);
            } else {
                setLeft(couple[1],goodChild);
            }
        }
    }

    /**
     * Méthode pour déréférencer une node avec 2 enfants
     * @param couple
     * @throws IOException
     */
    private void removeNode_TwoChildren(long[] couple) throws IOException {

        long[] switchingNode = searchLefterNode(new long[]{getRight(couple[0]),couple[0]});

        System.out.println(Arrays.toString(switchingNode));
        //displayTree(root,0);
        inverseConnexions(couple, switchingNode);
        //displayTree(root,0);

        long[] newCouple;
        if (couple[0] == switchingNode[1]) {
            newCouple = new long[]{switchingNode[1],switchingNode[0]};
        } else {
            newCouple = new long[]{couple[0],switchingNode[1]};
        }
        System.out.println(Arrays.toString(newCouple));
        removeStagiaire(newCouple);

    }
    //endregion
    //region SEARCHING_METHODS

    /**
     * Fonction récursive qui recherche une node, et qui continue la recherche dans la bonne direction.
     *
     * @param IDToSearch
     * @param currentIndex
     * @return
     */
    private long searchIndexWithID(String IDToSearch, long currentIndex) throws IOException {

        int comparison = IDToSearch.compareToIgnoreCase(getID(currentIndex)); // ID est supérieur ou inférieur à l'ID de currentIndex ?
        if (comparison == 0) {
            return currentIndex;
        } else if (comparison < 0) {
            long leftNode = getLeft(currentIndex);
            if (leftNode != -1) return searchIndexWithID(IDToSearch, leftNode);
            else return leftNode;
        } else {
            long rightNode = getRight(currentIndex);
            if (rightNode != -1) return searchIndexWithID(IDToSearch, rightNode);
            else return rightNode;
        }
    }

    /**
     * Fonction récursive qui recherche une node et son parent, et qui continue la recherche dans la bonne direction.
     *
     * @param IDToSearch
     * @param currentIndex
     * @return le couple node-parent
     * @throws IOException
     */
    private long[] searchCoupleWithID(String IDToSearch, long currentIndex) throws IOException {

        int comparison = IDToSearch.compareToIgnoreCase(getID(currentIndex)); // ID est supérieur ou inférieur à l'ID de currentIndex ?
        long[] result = new long[]{-1, -1};

        if (comparison == 0) {
            //System.out.println("Founded !");
            return new long[]{currentIndex, -1};
        } else if (comparison < 0) {
            long leftNode = getLeft(currentIndex);
            if (leftNode != -1) result = searchCoupleWithID(IDToSearch, leftNode);
        } else {
            long rightNode = getRight(currentIndex);
            if (rightNode != -1) result = searchCoupleWithID(IDToSearch, rightNode);
        }

        if (result[1] == -1) result[1] = currentIndex;

        return result;
    }

    /**
     * Fonction qui recher la node la plus à droite dans l'arbre en partant de nodeIndex et qui renvoie l'index de la node et de son parent.
     * @param coupleIndex
     * @return le couple node-parent
     * @throws IOException
     */
    private long[] searchLefterNode(long[] coupleIndex) throws IOException {
        long[] result;
        long right = getLeft(coupleIndex[0]);
        if (right != -1) {
            result = searchLefterNode(new long[]{right,coupleIndex[0]});}
        else result = coupleIndex;
        if (result[1] == -1) result[1] = coupleIndex[1];
        return result;
    }

    //endregion
    //region NODE_GET_SET
    private void setChilds(long index, long[] newChilds) throws IOException {
        setLeft(index, newChilds[0]);
        setRight(index, newChilds[1]);
    }

    private long[] getChilds(long nodeIndex) throws IOException {
        return new long[]{getLeft(nodeIndex), getRight(nodeIndex)};
    }

    private int getChildCount(long nodeIndex) throws IOException {
        int amount = 0;
        long[] childs = getChilds(nodeIndex);
        for (long child : childs) {
            if (child != -1) amount++;
        }
        return amount;
    }

    private long getLeft(long nodeIndex) throws IOException {
        raf.seek(nodeIndex);
        raf.readUTF();
        return raf.readLong();
    }

    private long getRight(long nodeIndex) throws IOException {
        raf.seek(nodeIndex);
        raf.readUTF();
        raf.readLong();
        return raf.readLong();
    }

    private String getID(long nodeIndex) throws IOException {
        raf.seek(nodeIndex);
        return raf.readUTF();
    }

    private void setLeft(long nodeIndex, long value) throws IOException {
        raf.seek(nodeIndex);
        raf.readUTF();
        raf.writeLong(value);
    }

    private void setRight(long nodeIndex, long value) throws IOException {
        raf.seek(nodeIndex);
        raf.readUTF();
        raf.readLong();
        raf.writeLong(value);
    }

    private long getRoot() throws IOException {
        if (raf.length() < Long.BYTES) setRoot(Long.BYTES);

        raf.seek(0);
        return raf.readLong();
    }

    private void setRoot(long index) throws IOException {
        root = index;
        raf.seek(0);
        raf.writeLong(index);
    }
    //endregion
}