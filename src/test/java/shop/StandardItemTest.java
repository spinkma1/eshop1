package shop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import shop.*;

import static org.junit.jupiter.api.Assertions.*;

class StandardItemTest {
    @Test
    public void TestConstructor(){
        StandardItem item = new StandardItem(420,"Kozel", 16.90f,"Beverages", 1);
        assertEquals(420, item.getID());
        assertEquals("Kozel", item.getName());
        assertEquals(16.90f, item.getPrice(), 0.0);
        assertEquals("Beverages", item.getCategory());
        assertEquals(1, item.getLoyaltyPoints());
    }
    @ParameterizedTest
    @CsvSource({
            "420,Kozel1, 20.0f, Beverages, 99, 5, Branik, 30.0f, Beverages, 1, 420,Kozel1, 20.0f, Beverages, 99",

    })
    void testEquals(int id, String name, float price, String category, int loyaltyPoints,int id2, String name2, float price2, String category2, int loyaltyPoints2,int id3, String name3, float price3, String category3, int loyaltyPoints3) {
        StandardItem itemxd = new StandardItem(id,name,price,category,loyaltyPoints);
        StandardItem itemxdd = new StandardItem(id2,name2,price2,category2,loyaltyPoints2);
        StandardItem itemnevim = new StandardItem(id3,name3,price3,category3,loyaltyPoints3);

        assertEquals(itemnevim, itemxd);
        assertNotEquals(itemxdd, itemxd);
    }

    @ParameterizedTest
    @CsvSource({
            "420, Kozel1, 10.0f, Beverages, 99",
            "5, kozel3, 20.0f, Beverages, 5",
            "0, Braník, 30.0f, Beverages, 1"
    })
    void testCopy(int id, String name, float price, String category, int loyaltyPoints) {
        StandardItem item = new StandardItem(id, name, price, category, loyaltyPoints);
        StandardItem copy = item.copy();
        assertEquals(item, copy);
        assertNotSame(item, copy);
    }
}