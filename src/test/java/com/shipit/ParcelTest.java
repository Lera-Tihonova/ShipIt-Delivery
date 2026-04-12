import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParcelTest {

    @Test
    public void testStandardParcelCost() {
        StandardParcel parcel = new StandardParcel("Книги", 5, "Москва", 1);
        assertEquals(10, parcel.calculateDeliveryCost());
    }

    @Test
    public void testFragileParcelCost() {
        FragileParcel parcel = new FragileParcel("Ваза", 3, "СПб", 1);
        assertEquals(12, parcel.calculateDeliveryCost());
    }

    @Test
    public void testPerishableParcelCost() {
        PerishableParcel parcel = new PerishableParcel("Пирог", 2, "Казань", 1, 3);
        assertEquals(6, parcel.calculateDeliveryCost());
    }

    @Test
    public void testParcelCostBoundaryValues() {
        StandardParcel parcel1 = new StandardParcel("Пустая", 0, "Адрес", 1);
        assertEquals(0, parcel1.calculateDeliveryCost());

        StandardParcel parcel2 = new StandardParcel("Тяжелая", 100, "Адрес", 1);
        assertEquals(200, parcel2.calculateDeliveryCost());
    }

    @Test
    public void testIsExpiredNotExpired() {
        PerishableParcel parcel = new PerishableParcel("Молоко", 1, "Адрес", 5, 3);
        assertFalse(parcel.isExpired(7));
    }

    @Test
    public void testIsExpiredExactlyAtLimit() {
        PerishableParcel parcel = new PerishableParcel("Йогурт", 1, "Адрес", 5, 3);
        assertFalse(parcel.isExpired(8));
    }

    @Test
    public void testIsExpiredExpired() {
        PerishableParcel parcel = new PerishableParcel("Рыба", 2, "Адрес", 5, 2);
        assertTrue(parcel.isExpired(8));
    }

    @Test
    public void testIsExpiredSameDay() {
        PerishableParcel parcel = new PerishableParcel("Овощи", 3, "Адрес", 10, 5);
        assertFalse(parcel.isExpired(10));
    }
}