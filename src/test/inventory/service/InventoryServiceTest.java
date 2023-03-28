package inventory.service;

import inventory.repository.InventoryRepository;
import org.junit.jupiter.api.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

public class InventoryServiceTest {

    private static final int STOC = 5;
    private static final int MIN = 2;
    private static final int MAX = 7;
    private static final int MASINA_ID = 1234;
    private InventoryService service;

    @BeforeEach
    void setUp() throws IOException {
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

    @RepeatedTest(3)
    @DisplayName("TC1_ECP: Add new inhouse part successfully")
    @Tag("ECP")
    @Tag("inhousePart")
    @Tag("price")
    @Tag("name")
    void addInhousePartValid_TC1_ECP() {
        // Arrange
        String validName = "InhousePart";
        double validPrice = 23.05;
        // Act
        service.addInhousePart(validName, validPrice, STOC, MIN, MAX, MASINA_ID);
        // Assert
        assert service.getAllParts().size() == 1;
    }

    @Test
    @DisplayName("TC2_ECP: Add new inhouse part with empty name")
    @Tag("ECP")
    @Tag("inhousePart")
    @Tag("name")
    void addInhousePartInvalid_TC2_ECP() {
        String invalidName = "";
        double validPrice = 23.05;
        try {
            service.addInhousePart(invalidName, validPrice, STOC, MIN, MAX, MASINA_ID);
            fail();
        } catch (RuntimeException e) {
            assert e.getMessage().equals("A name has not been entered. ");
        }
    }

    @Test
    @DisplayName("TC3_ECP: Add new inhouse part with price 0")
    @Tag("ECP")
    @Tag("inhousePart")
    @Tag("price")
    void addInhousePartInvalid_TC3_ECP() {
        String validName = "InhousePart";
        double invaldPrice = 0;
        try {
            service.addInhousePart(validName, invaldPrice, STOC, MIN, MAX, MASINA_ID);
            fail();
        } catch (RuntimeException e) {
            assert e.getMessage().equals("The price must be greater than 0. ");
        }
    }

    @Test
    @DisplayName("TC3_BVA: Add new inhouse part successfully")
    @Tag("BVA")
    @Tag("inhousePart")
    @Tag("name")
    void addInhousePartValid_TC3_BVA() {
        String validName = "P";
        double validPrice = 23.05;
        service.addInhousePart(validName, validPrice, STOC, MIN, MAX, MASINA_ID);
        assert service.getAllParts().size() == 1;
    }

    @Test
    @DisplayName("TC4_BVA: Add new inhouse part successfully")
    @Tag("BVA")
    @Tag("inhousePart")
    @Tag("name")
    void addInhousePartValid_TC4_BVA() {
        String validName = "P...................................................................................." +
                "..................................................................................." +
                "....................................................................................123";
        double validPrice = 23.05;
        service.addInhousePart(validName, validPrice, STOC, MIN, MAX, MASINA_ID);
        assert service.getAllParts().size() == 1;
    }

    @Test
    @DisplayName("TC5_BVA: Add new inhouse part successfully")
    @Tag("BVA")
    @Tag("inhousePart")
    @Tag("name")
    void addInhousePartValid_TC5_BVA() {
        String validName = "P...................................................................................." +
                "..................................................................................." +
                "....................................................................................12";
        double validPrice = 23.05;
        service.addInhousePart(validName, validPrice, STOC, MIN, MAX, MASINA_ID);
        assert service.getAllParts().size() == 1;
    }

    @Test
    @DisplayName("TC8_BVA: Add new inhouse part successfully")
    @Tag("BVA")
    @Tag("inhousePart")
    @Tag("price")
    void addInhousePartValid_TC8_BVA() {
        String validName = "P";
        double validPrice = 0.01;
        service.addInhousePart(validName, validPrice, STOC, MIN, MAX, MASINA_ID);
        assert service.getAllParts().size() == 1;
    }

    @Test
    @DisplayName("TC9_BVA: Add new inhouse part with price less than 0")
    @Tag("BVA")
    @Tag("inhousePart")
    @Tag("price")
    void addInhousePartInvalid_TC9_BVA() {
        String validName = "P";
        double invalidPrice = -0.01;
        try {
            service.addInhousePart(validName, invalidPrice, STOC, MIN, MAX, MASINA_ID);
            fail();
        } catch (RuntimeException e) {
            assert e.getMessage().equals("The price must be greater than 0. ");
        }
    }

    @Nested
    @DisplayName("BVA tests for add inhouse part with focus on price upper limit")
    class BVAPriceUpperLimit {
        @Test
        @Tag("BVA")
        @Tag("inhousePart")
        @Tag("price")
        void addInhousePartValid_TC10_BVA() {
            String validName = "P";
            double validPrice = Double.MAX_VALUE;
            service.addInhousePart(validName, validPrice, STOC, MIN, MAX, MASINA_ID);
            assert service.getAllParts().size() == 1;
        }

        @Test
        @Tag("BVA")
        @Tag("inhousePart")
        @Tag("price")
        void addInhousePartValid_TC11_BVA() {
            String validName = "P";
            double validPrice = Double.MAX_VALUE - 0.01;
            service.addInhousePart(validName, validPrice, STOC, MIN, MAX, MASINA_ID);
            assert service.getAllParts().size() == 1;
        }

        @Test
        @Tag("BVA")
        @Tag("inhousePart")
        @Tag("price")
        @Timeout(2)
        void addInhousePartValid_TC12_BVA() {
            String validName = "P";
            double validPrice = Double.MAX_VALUE + 0.01;
            service.addInhousePart(validName, validPrice, STOC, MIN, MAX, MASINA_ID);
            assert service.getAllParts().size() == 1;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }
}