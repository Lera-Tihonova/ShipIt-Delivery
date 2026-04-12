import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class ParcelBoxTest {
    private ParcelBox<StandardParcel> box;

    @BeforeEach
    public void setUp() {
        box = new ParcelBox<>(20);
    }

    @Test
    public void testAddParcelWhenWeightNotExceeded() {
        StandardParcel parcel = new StandardParcel("Книги", 5, "Москва", 1);
        box.addParcel(parcel);

        assertEquals(1, box.getParcelCount());
        assertEquals(5, box.getCurrentWeight());
        assertTrue(box.getAllParcels().contains(parcel));
    }

    @Test
    public void testAddMultipleParcelsWithinLimit() {
        StandardParcel parcel1 = new StandardParcel("Книги", 5, "Москва", 1);
        StandardParcel parcel2 = new StandardParcel("Одежда", 7, "СПб", 1);

        box.addParcel(parcel1);
        box.addParcel(parcel2);

        assertEquals(2, box.getParcelCount());
        assertEquals(12, box.getCurrentWeight());
    }

    @Test
    public void testAddParcelWhenWeightExceeded() {
        StandardParcel parcel1 = new StandardParcel("Тяжелая", 15, "Адрес", 1);
        StandardParcel parcel2 = new StandardParcel("Еще одна", 10, "Адрес", 1);

        box.addParcel(parcel1);
        box.addParcel(parcel2);

        assertEquals(1, box.getParcelCount());
        assertEquals(15, box.getCurrentWeight());
        assertTrue(box.getAllParcels().contains(parcel1));
        assertFalse(box.getAllParcels().contains(parcel2));
    }

    @Test
    public void testAddParcelExactlyAtMaxWeight() {
        StandardParcel parcel = new StandardParcel("Точно по весу", 20, "Адрес", 1);
        box.addParcel(parcel);

        assertEquals(1, box.getParcelCount());
        assertEquals(20, box.getCurrentWeight());
    }

    @Test
    public void testGetAllParcelsReturnsCopy() {
        StandardParcel parcel = new StandardParcel("Тест", 5, "Адрес", 1);
        box.addParcel(parcel);

        List<StandardParcel> parcels = box.getAllParcels();
        assertEquals(1, parcels.size());

        parcels.clear();
        assertEquals(1, box.getParcelCount());
    }
}