package storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import shop.Item;


import org.junit.jupiter.api.Test;
import shop.StandardItem;

import static org.junit.jupiter.api.Assertions.*;

class ItemStockTest {
    private Item item1;
    private ItemStock itemStock;
    @BeforeEach
    public void setUp() {
        item1 = new StandardItem(1, "Pivo", 105444.0f, "Alkohol", 420);
        itemStock = new ItemStock(item1);
    }
    @Test
    public void constructorTest(){

        assertEquals(item1, itemStock.getItem());
        assertEquals(0, itemStock.getCount());
    }
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 40})
    void increaseItemCountTest(int number) {
        int initialCount = itemStock.getCount();
        itemStock.IncreaseItemCount(number);
        assertEquals(initialCount + number, itemStock.getCount());
    }
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 40})
    void decreaseItemCountTest(int number) {
        int initialCount = itemStock.getCount();
        itemStock.decreaseItemCount(number);
        assertEquals(initialCount - number, itemStock.getCount());
    }
}