package archive;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import shop.Item;
import shop.Order;
import shop.ShoppingCart;
import shop.StandardItem;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import archive.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PurchasesArchiveTest {
    ArrayList<Item> items=new ArrayList<Item>();
    HashMap<Integer, ItemPurchaseArchiveEntry> itemPurchaseArchive2= new HashMap<Integer, ItemPurchaseArchiveEntry>();

    ArrayList<Order> orderArchive=new ArrayList<Order>();
    PurchasesArchive pa = new PurchasesArchive(itemPurchaseArchive2, orderArchive);
    StandardItem Kozel = new StandardItem(1, "Item 1", 45f, "Pivo", 9);
    StandardItem Braník = new StandardItem(2, "Item 2", 5f, "Pivo", 9);
    StandardItem Plzeň = new StandardItem(3, "Item 3", 5f, "Pivo", 9);
    @Test
    public void printItemPurchaseStatistics() {
        //PREPARE!
        items.add(Braník);
        items.add(Kozel);
        items.add(Plzeň); //object CART MAME
        ShoppingCart cart = new ShoppingCart(items);
        Order order = new Order(cart, "Novák", "Praha", 4);
        ItemPurchaseArchiveEntry ipa = new ItemPurchaseArchiveEntry(Kozel);
        ItemPurchaseArchiveEntry ipa2 = new ItemPurchaseArchiveEntry(Braník);
        ItemPurchaseArchiveEntry ipa3 = new ItemPurchaseArchiveEntry(Plzeň);
        itemPurchaseArchive2.put(1, ipa);
        itemPurchaseArchive2.put(2, ipa2);
        itemPurchaseArchive2.put(3, ipa3);
        orderArchive.add(order);


        String s1 = "ITEM PURCHASE STATISTICS:";
        String s2 = "ITEM  Item   ID 1   NAME Item 1   CATEGORY Pivo   PRICE 45.0   LOYALTY POINTS 9   HAS BEEN SOLD 1 TIMES";
        String s3 = "ITEM  Item   ID 2   NAME Item 2   CATEGORY Pivo   PRICE 5.0   LOYALTY POINTS 9   HAS BEEN SOLD 1 TIMES";
        String s4 = "ITEM  Item   ID 3   NAME Item 3   CATEGORY Pivo   PRICE 5.0   LOYALTY POINTS 9   HAS BEEN SOLD 1 TIMES";


        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        pa.printItemPurchaseStatistics();

        String actualOutput = outputStream.toString();
        String[] actualOutputLines = actualOutput.split("\r\n");

        assertEquals(s2, actualOutputLines[1]);
        assertEquals(s1, actualOutputLines[0]);
        assertEquals(s3, actualOutputLines[2]);
        assertEquals(s4, actualOutputLines[3]);
    }

    @Test
    void getHowManyTimesHasBeenItemSold() {
        items.add(Braník);
        items.add(Kozel);
        items.add(Plzeň); //object CART MAME
        ShoppingCart cart = new ShoppingCart(items);
        Order order = new Order(cart, "Novák", "Praha", 4);
        ItemPurchaseArchiveEntry ipa = new ItemPurchaseArchiveEntry(Kozel);
        ItemPurchaseArchiveEntry ipa2 = new ItemPurchaseArchiveEntry(Braník);
        ItemPurchaseArchiveEntry ipa3 = new ItemPurchaseArchiveEntry(Plzeň);
        itemPurchaseArchive2.put(1, ipa);
        itemPurchaseArchive2.put(2, ipa2);
        itemPurchaseArchive2.put(3, ipa3);
        orderArchive.add(order);

        int idk=pa.getHowManyTimesHasBeenItemSold(Braník);
        int result=1;
        assertEquals(result,idk);
        }
    @Test
    void getHowManyTimesHasBeenItemSoldZeroSold() {
        items.add(Braník);
        items.add(Kozel);
        items.add(Plzeň); //object CART MAME
        ShoppingCart cart = new ShoppingCart(items);
        Order order = new Order(cart, "Novák", "Praha", 4);
        ItemPurchaseArchiveEntry ipa = new ItemPurchaseArchiveEntry(Kozel);
        ItemPurchaseArchiveEntry ipa2 = new ItemPurchaseArchiveEntry(Braník);
        ItemPurchaseArchiveEntry ipa3 = new ItemPurchaseArchiveEntry(Plzeň);
        itemPurchaseArchive2.put(1, ipa);
        itemPurchaseArchive2.put(3, ipa3);
        orderArchive.add(order);

        int idk=pa.getHowManyTimesHasBeenItemSold(Braník);
        int result=0;
        assertEquals(result,idk);
    }
    @Test
    void putOrderToPurchasesArchive() {
        items.add(Braník);
        items.add(Kozel);
        items.add(Plzeň); //object CART MAME
        ShoppingCart cart = new ShoppingCart(items);
        Order order = new Order(cart, "Novák", "Praha", 4);
        ItemPurchaseArchiveEntry ipa = new ItemPurchaseArchiveEntry(Kozel);
        itemPurchaseArchive2.put(1, ipa);

        pa.putOrderToPurchasesArchive(order);
        assertEquals(1,pa.getHowManyTimesHasBeenItemSold(Braník));
        assertEquals(2,pa.getHowManyTimesHasBeenItemSold(Kozel));
    }
    @Test
    void ItemPurchaseArchiveEntryTest(){
        items.add(Braník);
        items.add(Kozel);
        items.add(Plzeň); //object CART MAME
        ShoppingCart cart = new ShoppingCart(items);
        Order order = new Order(cart, "Novák", "Praha", 4);
        ItemPurchaseArchiveEntry ipa = new ItemPurchaseArchiveEntry(Kozel);
        assertEquals(1,ipa.getCountHowManyTimesHasBeenSold());
        ipa.increaseCountHowManyTimesHasBeenSold(1);
        assertEquals(2,ipa.getCountHowManyTimesHasBeenSold());
        assertEquals(Kozel,ipa.getRefItem());
    }
    @Test
    void mockitoOrderArchive(){
        items.add(Braník);
        items.add(Kozel);
        items.add(Plzeň);
        ShoppingCart cart = new ShoppingCart(items);
        Order order = new Order(cart, "Novák", "Praha", 4);
        ArrayList<Order> mockedOrderArchive=mock(ArrayList.class);
        mockedOrderArchive.add(order);

        verify(mockedOrderArchive).add(order);
    }
    @Test
    void mockitoItemPurchaseArchiveEntry(){
        ItemPurchaseArchiveEntry test =mock(ItemPurchaseArchiveEntry.class);
        test.increaseCountHowManyTimesHasBeenSold(5);
        when(test.getCountHowManyTimesHasBeenSold()).thenReturn(5);
        test.increaseCountHowManyTimesHasBeenSold(5);
        when(test.getRefItem()).thenReturn(Braník);
        assertEquals(test.getCountHowManyTimesHasBeenSold(), 5);
        assertEquals(test.getRefItem(),Braník);

    }


}