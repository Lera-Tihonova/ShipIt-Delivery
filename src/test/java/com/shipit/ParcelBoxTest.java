package com.shipit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class ParcelBoxTest {
    private ParcelBox<StandardParcel> box;
    private static final int MAX_WEIGHT = 10;

    @BeforeEach
    void setUp() {
        box = new ParcelBox<>(MAX_WEIGHT);
    }

    @Test
    void testAddParcelWeightLessThanLimit_Success() {
        StandardParcel parcel = new StandardParcel("Книги", 9, "Москва", 1);
        box.addParcel(parcel);

        assertEquals(1, box.getParcelCount());
        assertEquals(9, box.getCurrentWeight());
        assertTrue(box.getAllParcels().contains(parcel));
    }

    @Test
    void testAddParcelWeightExactlyAtLimit_Success() {
        StandardParcel parcel = new StandardParcel("Книги", 10, "Москва", 1);
        box.addParcel(parcel);

        assertEquals(1, box.getParcelCount());
        assertEquals(10, box.getCurrentWeight());
        assertTrue(box.getAllParcels().contains(parcel));
    }

    @Test
    void testAddParcelWeightExceedsLimit_Failure() {
        StandardParcel parcel = new StandardParcel("Книги", 11, "Москва", 1);
        box.addParcel(parcel);

        assertEquals(0, box.getParcelCount());
        assertEquals(0, box.getCurrentWeight());
        assertFalse(box.getAllParcels().contains(parcel));
    }

    @Test
    void testAddParcelWeightZero_Success() {
        StandardParcel parcel = new StandardParcel("Пустая", 0, "Москва", 1);
        box.addParcel(parcel);

        assertEquals(1, box.getParcelCount());
        assertEquals(0, box.getCurrentWeight());
        assertTrue(box.getAllParcels().contains(parcel));
    }

    @Test
    void testAddMultipleParcelsUntilLimit() {
        StandardParcel parcel1 = new StandardParcel("Книги", 5, "Москва", 1);
        StandardParcel parcel2 = new StandardParcel("Журналы", 5, "СПб", 1);
        StandardParcel parcel3 = new StandardParcel("Брошюры", 1, "Казань", 1);

        box.addParcel(parcel1);
        box.addParcel(parcel2);
        box.addParcel(parcel3);

        assertEquals(2, box.getParcelCount());
        assertEquals(10, box.getCurrentWeight());

        List<StandardParcel> parcels = box.getAllParcels();
        assertTrue(parcels.contains(parcel1));
        assertTrue(parcels.contains(parcel2));
        assertFalse(parcels.contains(parcel3));
    }
}