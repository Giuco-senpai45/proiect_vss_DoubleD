package inventory.service;

import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.repository.InventoryRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class InventoryServiceWBTest {
    private static final int STOC = 5;
    private static final int MIN = 2;
    private static final int MAX = 7;
    private static final int MASINA_ID = 1234;
    private static final double PRICE = 23.05;
    private InventoryService service;
    private Part part;

    @BeforeEach
    void setUp() throws IOException {
        File file = new File("data/items_test.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write("");

        InventoryRepository repo = new InventoryRepository("data/items_test.txt");
        service = new InventoryService(repo);
        part = new InhousePart(1, "apiece", PRICE, STOC, MIN, MAX, MASINA_ID);
        repo.addPart(part);
    }

    @AfterAll
    static void tearDown() throws IOException {
        File file = new File("data/items_test.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write("");
    }

    @Test
    void testLookupPart_TC1() {
        String name = "";
        Part foundPart = service.lookupPart(name);
        assert foundPart == null;
    }

    @Test
    void testLookupPart_TC2() {
        String name = "a";
        Part foundPart = service.lookupPart(name);
        assert foundPart.getPartId() == part.getPartId();
    }

    @Test
    void testLookupPart_TC3() {
        String name = "x";
        Part foundPart = service.lookupPart(name);
        assert foundPart == null;
    }

    @Test
    void testLookupPart_TC4() {
        String name = "api";
        Part foundPart = service.lookupPart(name);
        assert foundPart.getPartId() == part.getPartId();
    }

    @Test
    void testLookupPart_TC5() {
        String name = "xyz";
        Part foundPart = service.lookupPart(name);
        assert foundPart == null;
    }
}
