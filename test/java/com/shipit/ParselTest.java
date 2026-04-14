package com.shipit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParcelTest {


    @Test
    public void testStandardParcelCost_BoundaryValues() {
        StandardParcel parcel1 = new StandardParcel("Пустая", 0, "Москва", 1);
        assertEquals(0, parcel1.calculateDeliveryCost()); // 0 * 2 = 0

        StandardParcel parcel2 = new StandardParcel("Легкая", 1, "Москва", 1);
        assertEquals(2, parcel2.calculateDeliveryCost()); // 1 * 2 = 2

        StandardParcel parcel3 = new StandardParcel("Тяжелая", 100, "Москва", 1);
        assertEquals(200, parcel3.calculateDeliveryCost()); // 100 * 2 = 200
    }

    @Test
    public void testFragileParcelCost_BoundaryValues() {
        FragileParcel parcel1 = new FragileParcel("Пустая", 0, "СПб", 1);
        assertEquals(0, parcel1.calculateDeliveryCost()); // 0 * 4 = 0

        FragileParcel parcel2 = new FragileParcel("Легкая", 1, "СПб", 1);
        assertEquals(4, parcel2.calculateDeliveryCost()); // 1 * 4 = 4

        FragileParcel parcel3 = new FragileParcel("Тяжелая", 100, "СПб", 1);
        assertEquals(400, parcel3.calculateDeliveryCost()); // 100 * 4 = 400
    }

    @Test
    public void testPerishableParcelCost_BoundaryValues() {
        PerishableParcel parcel1 = new PerishableParcel("Пустая", 0, "Казань", 1, 3);
        assertEquals(0, parcel1.calculateDeliveryCost()); // 0 * 3 = 0

        PerishableParcel parcel2 = new PerishableParcel("Легкая", 1, "Казань", 1, 3);
        assertEquals(3, parcel2.calculateDeliveryCost()); // 1 * 3 = 3

        PerishableParcel parcel3 = new PerishableParcel("Тяжелая", 100, "Казань", 1, 3);
        assertEquals(300, parcel3.calculateDeliveryCost()); // 100 * 3 = 300
    }

    @Test
    public void testIsExpired_BoundaryValues() {
        int sendDay = 10;
        int timeToLive = 5; // посылка хранится 5 дней

        PerishableParcel parcel = new PerishableParcel("Молоко", 1, "Адрес", sendDay, timeToLive);

        assertFalse(parcel.isExpired(10), "День отправки - посылка свежая");

        assertFalse(parcel.isExpired(15), "Последний день годности - посылка свежая");

        assertTrue(parcel.isExpired(16), "Первый день после истечения срока - испортилась");

        assertTrue(parcel.isExpired(20), "Далеко за пределами срока - испортилась");
    }

    @Test
    public void testIsExpired_MinimumTimeToLive() {
        int sendDay = 5;
        int timeToLive = 1;

        PerishableParcel parcel = new PerishableParcel("Свежая рыба", 2, "Адрес", sendDay, timeToLive);

        assertFalse(parcel.isExpired(5), "День отправки - свежая");
        assertFalse(parcel.isExpired(6), "Последний день - свежая");
        assertTrue(parcel.isExpired(7), "На следующий день - испортилась");
    }

    @Test
    public void testIsExpired_ZeroTimeToLive() {
        int sendDay = 1;
        int timeToLive = 0; // посылка должна быть доставлена в день отправки

        PerishableParcel parcel = new PerishableParcel("Очень свежая", 1, "Адрес", sendDay, timeToLive);

        assertFalse(parcel.isExpired(1), "День отправки - свежая");
        assertTrue(parcel.isExpired(2), "На следующий день - испортилась");
    }

    @Test
    public void testIsExpired_LargeTimeToLive() {
        int sendDay = 1;
        int timeToLive = 100;

        PerishableParcel parcel = new PerishableParcel("Консервы", 5, "Адрес", sendDay, timeToLive);

        assertFalse(parcel.isExpired(50), "В середине срока - свежая");
        assertFalse(parcel.isExpired(101), "В последний день - свежая");
        assertTrue(parcel.isExpired(102), "После истечения - испортилась");
    }
}