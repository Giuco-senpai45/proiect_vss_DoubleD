package inventory.service;

import inventory.model.InhousePart;
import inventory.model.Inventory;
import inventory.model.Part;
import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class InventoryUnitTests {

    private static final int STOC = 5;
    private static final int MIN = 2;
    private static final int MAX = 7;
    private static final int MASINA_ID = 1234;
    private static final double PRICE = 12.5;
    private static final String NAME = "InhousePart";

    @Nested
    @DisplayName("Unit Tests for Entity - InhousePart")
    class EntityUnitTests {
        @Test
        void testInhousePartConstructor() {
            InhousePart inhousePart = new InhousePart(234, NAME, PRICE, STOC, MIN, MAX, MASINA_ID);

            assertEquals(234, inhousePart.getPartId());
            assertEquals(NAME, inhousePart.getName());
            assertEquals(PRICE, inhousePart.getPrice());
            assertEquals(STOC, inhousePart.getInStock());
            assertEquals(MIN, inhousePart.getMin());
            assertEquals(MAX, inhousePart.getMax());
            assertEquals(MASINA_ID, inhousePart.getMachineId());
        }
    }

    @Nested
    @DisplayName("Unit Tests for Repository - InventoryRepository")
    class RepositoryUnitTests {
        private InhousePart inhousePart;
        @Mock
        private Inventory inventory;
        @InjectMocks
        private InventoryRepository inventoryRepository;

        @BeforeEach
        void init() {
            inventory = mock(Inventory.class);
            inventoryRepository = new InventoryRepository("data/items_test.txt", inventory);
            inhousePart = new InhousePart(234, NAME, PRICE, STOC, MIN, MAX, MASINA_ID);

            when(inventory.getProducts()).thenReturn(FXCollections.observableArrayList());
        }

        @Test
        void testAddInhousePart() {
            doNothing().when(inventory).addPart(any());
            when(inventory.getAllParts()).thenReturn(FXCollections.observableArrayList(inhousePart));

            inventoryRepository.addPart(inhousePart);
            assertEquals(1, inventoryRepository.getAllParts().size());
        }

        @Test
        void testFindInhousePart() {
            when(inventory.lookupPart(anyString())).thenReturn(inhousePart);
            Part foundPart = inventoryRepository.lookupPart("in");

            assertEquals(234, foundPart.getPartId());
            assertEquals(NAME, foundPart.getName());
            assertEquals(PRICE, foundPart.getPrice());
            assertEquals(STOC, foundPart.getInStock());
            assertEquals(MIN, foundPart.getMin());
            assertEquals(MAX, foundPart.getMax());
        }

        @Test
        void testUpdateInhousePart() {
            InhousePart partToUpdate = new InhousePart(234, "ModifiedPartName", PRICE, STOC, MIN, MAX, MASINA_ID);
            doNothing().when(inventory).updatePart(anyInt(), any());
            when(inventory.lookupPart(any())).thenReturn(partToUpdate);
            when(inventory.getAllParts()).thenReturn(FXCollections.observableArrayList());

            inventoryRepository.updatePart(0, partToUpdate);
            Part updatedPart = inventoryRepository.lookupPart("Modified");
            assertEquals("ModifiedPartName", updatedPart.getName());
        }

        @Test
        void testDeleteInhousePart() {
            doNothing().when(inventory).deletePart(any());
            when(inventory.getAllParts()).thenReturn(FXCollections.observableArrayList());

            inventoryRepository.deletePart(inhousePart);
            assertEquals(0, inventoryRepository.getAllParts().size());
        }
    }

    @Nested
    @DisplayName("Unit Tests for Service - InventoryService")
    class ServiceUnitTests {
        private InhousePart inhousePart;
        @Mock
        private InventoryRepository inventoryRepository;
        @InjectMocks
        private InventoryService inventoryService;

        @BeforeEach
        void init() {
            inventoryRepository = mock(InventoryRepository.class);
            inventoryService = new InventoryService(inventoryRepository);
            inhousePart = new InhousePart(234, NAME, PRICE, STOC, MIN, MAX, MASINA_ID);
        }

        @Test
        void testAddValidInhousePart() {
            doNothing().when(inventoryRepository).addPart(any());
            when(inventoryRepository.getAutoPartId()).thenReturn(123);
            when(inventoryRepository.getAllParts()).thenReturn(FXCollections.observableArrayList(inhousePart));

            inventoryService.addInhousePart(NAME, PRICE, STOC, MIN, MAX, MASINA_ID);
            assertEquals(1, inventoryRepository.getAllParts().size());
        }

        @Test
        void testFindInhousePart() {
            when(inventoryRepository.lookupPart(anyString())).thenReturn(inhousePart);
            Part foundPart = inventoryService.lookupPart("in");

            assertEquals(234, foundPart.getPartId());
            assertEquals(NAME, foundPart.getName());
            assertEquals(PRICE, foundPart.getPrice());
            assertEquals(STOC, foundPart.getInStock());
            assertEquals(MIN, foundPart.getMin());
            assertEquals(MAX, foundPart.getMax());
        }

        @Test
        void testUpdateInhousePart() {
            InhousePart partToUpdate = new InhousePart(234, "ModifiedPartName", PRICE, STOC, MIN, MAX, MASINA_ID);
            doNothing().when(inventoryRepository).updatePart(anyInt(), any());
            when(inventoryRepository.getAllParts()).thenReturn(FXCollections.observableArrayList(partToUpdate));

            inventoryService.updateInhousePart(0, 123, "ModifiedPartName", PRICE, STOC, MIN, MAX, MASINA_ID);
            Part updatedPart = inventoryRepository.getAllParts().get(0);
            assertEquals("ModifiedPartName", updatedPart.getName());
        }

        @Test
        void testDeleteInhousePart() {
            doNothing().when(inventoryRepository).deletePart(any());
            when(inventoryRepository.getAllParts()).thenReturn(FXCollections.observableArrayList());

            inventoryService.deletePart(inhousePart);
            assertEquals(0, inventoryRepository.getAllParts().size());
        }
    }
}
