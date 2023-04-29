package inventory.service;

import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.repository.InventoryRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InventoryServiceIntegrationWithRepository {
    private static final String NUME = "CevaNume";
    private static final double PRET = 23.32;
    private static final int STOC = 5;
    private static final int MIN = 2;
    private static final int MAX = 7;
    private static final int MASINA_ID = 1234;
    private static InventoryService service;

    @BeforeAll
    static void setUp() throws IOException {
        File file = new File("data/items_test.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write("");

        InventoryRepository repo = new InventoryRepository("data/items_test.txt");
        service = new InventoryService(repo);
    }

    @AfterAll
    static void tearDown() throws IOException {
        File file = new File("data/items_test.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write("");
    }

    @Test
    void test_addInhousePart() {
        assertEquals(0, service.getAllParts().size());

        service.addInhousePart(NUME, PRET, STOC, MIN, MAX, MASINA_ID);
        assertEquals(1, service.getAllParts().size());
    }

    @Test
    void test_lookupPart() {
        assertEquals(1, service.getAllParts().size());

        Part lookupPart = service.lookupPart("CevaNume");

        assert lookupPart instanceof InhousePart;
        assert lookupPart.getPartId() == 1;
        assert lookupPart.getName().equals(NUME);
        assert lookupPart.getPrice() == PRET;
        assert lookupPart.getMax() == MAX;
        assert lookupPart.getMin() == MIN;
        assert lookupPart.getInStock() == STOC;
        assert ((InhousePart) lookupPart).getMachineId() == MASINA_ID;
    }

    @Test
    void test_updateInhousePart() {
        assertEquals(1, service.getAllParts().size());

        String updatedName = "CevaNumeNou";
        double updatedPrice = 3.2;
        int updatedStock = 10;
        int updatedMax = 4;
        int updatedMin = 20;
        int updatedMachineId = 5;

        Part partToUpdate = service.lookupPart("CevaNume");
        service.updateInhousePart(0, partToUpdate.getPartId(), updatedName, updatedPrice, updatedStock, updatedMin, updatedMax, updatedMachineId);

        Part updatedPart = service.lookupPart(updatedName);

        assert updatedPart instanceof InhousePart;
        assert updatedPart.getPartId() == partToUpdate.getPartId();
        assert updatedPart.getName().equals(updatedName);
        assert updatedPart.getPrice() == updatedPrice;
        assert updatedPart.getMax() == updatedMax;
        assert updatedPart.getMin() == updatedMin;
        assert updatedPart.getInStock() == updatedStock;
        assert ((InhousePart) updatedPart).getMachineId() == updatedMachineId;

        assertEquals(1, service.getAllParts().size());
    }

    @Test
    void test_deleteInhousePart() {
        assertEquals(1, service.getAllParts().size());

        Part partToDelete = service.lookupPart("CevaNumeNou");
        service.deletePart(partToDelete);

        assertEquals(0, service.getAllParts().size());
    }


}
