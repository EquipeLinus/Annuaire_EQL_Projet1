package fr.eql.ai116.team.linus.annuaire.model.program;

import fr.eql.ai116.team.linus.annuaire.model.entity.Stagiaire;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

public class BinManager {

    public static final String BIN_PATH = "resources/datas.bin";

    public static void main(String[] args) {
        BinManager bManager = new BinManager();
        bManager.setStagiaireInBinIndex(new Stagiaire("thomas", "duron", "ai116",2024,93),0);

        System.out.println(bManager.getStagiaireInBinIndex(0));

        bManager.addStagiaire(new Stagiaire("mazir", "ouahioune", "ai116",2024,75));
        bManager.addStagiaire(new Stagiaire("andras", "schuller", "ai116",2024,19));
        bManager.addStagiaire(new Stagiaire("miroslava", "castillo", "ai116",2024,94));

        bManager.displayInOrder(0);
    }

    public void importDataInBin(List<Stagiaire> allStagiaire) {
        for (Stagiaire stagiaire : allStagiaire) {
            addStagiaire(stagiaire);
        }
    }

    private String getStagiaireId(Stagiaire stagiaire) {
        return stagiaire.getPromotion() + "_" + stagiaire.getLastName() + "_" + stagiaire.getFirstName();
    }

    private Stagiaire getStagiaireInBinIndex(int binIndex) {

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

    private void setStagiaireInBinIndex(Stagiaire stagiaire, long binIndex) {

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

                    setStagiaireInBinIndex(stagiaire,newNodeIndex);
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

    void displayInOrder(long nodeIndex) {
        if (getPreviousIndexFromIndex(nodeIndex) != -1) displayInOrder(getPreviousIndexFromIndex(nodeIndex));
        System.out.println(getIDFromIndex(nodeIndex));
        if (getNextIndexFromIndex(nodeIndex) != -1) displayInOrder(getNextIndexFromIndex(nodeIndex));
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

}
