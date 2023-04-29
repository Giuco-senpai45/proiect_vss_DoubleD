package inventory.repo;

import inventory.model.InhousePart;
import inventory.repository.InventoryRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RepoWithMockTest {
    @InjectMocks
    private static InventoryRepository repo;

    @Mock
    private static InhousePart inhousePart;

    @BeforeAll
        public static void setUp() throws IOException {
        File file = new File("data/items_test.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write("");

        repo = new InventoryRepository("data/items_test.txt");
        inhousePart = Mockito.mock(InhousePart.class);
    }

    @AfterAll
    static void tearDown() throws IOException {
        File file = new File("data/items_test.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write("");
    }

    @Test
    public void test_addInhousePart() {
        assert 0 == repo.getAllParts().size();
        repo.addPart(inhousePart);

        assert 1 == repo.getAllParts().size();
    }

    @Test
    public void test_deletePart() {
        assert 1 == repo.getAllParts().size();
        repo.deletePart(inhousePart);

        assert 0 == repo.getAllParts().size();
    }
}
