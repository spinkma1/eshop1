package shop;

import archive.PurchasesArchive;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import storage.*;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;
import static shop.EShopController.purchaseShoppingCart;

class EShopControllerTest {
    Storage storage = new Storage();
    PurchasesArchive archive;
    @AfterEach
    public void clearUp(){
        Storage storage;
    }
    @Test
    public void FirstTestSequence(){ //1,2,3
        String s1 = "Error in DiscountedItem.parseDate() - wrong date formatUnparseable date: \"66\"";
        String s2 = "Error in DiscountedItem.parseDate() - wrong date formatUnparseable date: \"2023-04-18\"";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        try {
            new DiscountedItem(1, "Dancing Panda v.2", 500, "Cars", 5, "66", "2023-04-18");
        }catch (NullPointerException e){
            String actualOutput = outputStream.toString();
            String[] actualOutputLines = actualOutput.split("\r\n");
            assertEquals(s1, actualOutputLines[0]);
            assertEquals(s2, actualOutputLines[1]);
        }
    }
    @Test
    public void SecondTestSequence() throws NoItemInStorage {//1-4-6
        String s1 = "Error: shopping cart is empty";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        StandardItem item=new StandardItem(1, "Item 1", 45f, "Pivo", 9);
        storage.insertItems(item,6);
        ShoppingCart newCart = new ShoppingCart();
        try {
            purchaseShoppingCart(newCart, "Libuse Novakova", "Kosmonautu 25, Praha 8");
        }catch(NullPointerException e){
            assertEquals(NullPointerException.class, e.getMessage());
        }
        String actualOutput = outputStream.toString();
        String[] actualOutputLines = actualOutput.split("\r\n");
        assertEquals(s1, actualOutputLines[0]);
    }
    @Test
    public void ThirdTestSequence() throws NoItemInStorage {//1-2-5-7-10-11-13-14

        DiscountedItem item= new DiscountedItem(1, "Dancing Panda v.2", 500, "Cars", 5, "12.12.2023", "18.12.2023");
        storage.insertItems(item,10);

        ShoppingCart newCart = new ShoppingCart();
        newCart.addItem(item);

        newCart.addItem(item);

        assertEquals(950,newCart.getTotalPrice());
        newCart.addItem(item);
        assertEquals(1425,newCart.getTotalPrice());
        newCart.removeItem(1);
        newCart.removeItem(1);
        newCart.removeItem(1);
        String s1 = "Error: shopping cart is empty";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        assertEquals(0,newCart.getTotalPrice());
        try {
            purchaseShoppingCart(newCart, "Libuse Novakova", "Kosmonautu 25, Praha 8");
        }catch(NullPointerException e){
            assertEquals(NullPointerException.class, e.getMessage());
        }
        String actualOutput = outputStream.toString();
        String[] actualOutputLines = actualOutput.split("\r\n");
        assertEquals(s1, actualOutputLines[0]);
    }
    @Test
    public void FifthTestSequence() throws NoItemInStorage { //1-4-7-10-11-13-14
        //vytvoření položek do skladu
        StandardItem firstItem = new StandardItem(1, "Motorová pila Husqarna", 2499f, "motorovky", 1000);
        StandardItem secondItem = new StandardItem(2, "Motorová pila Suzuki", 2199f, "motorovky", 800);
        StandardItem thirdItem = new StandardItem(3, "Motorová pila Kazashi", 3399f, "motorovky", 1400);
        Storage store = new Storage();
        //vložení položek do skladu
        store.insertItems(firstItem,2);
        store.insertItems(secondItem,4);
        store.insertItems(thirdItem, 1);
        //nový archiv pro eshopControler
        PurchasesArchive archive = new PurchasesArchive();
        EShopController shop = new EShopController(store, archive);
        ShoppingCart cart = new ShoppingCart();
        //do košíku vložíme stejnou položku vícekrát, než je ve skladu
        for (int i = 0; i < 5; i++) {
            cart.addItem(firstItem);
        }
        try {
            shop.purchaseShoppingCart(cart, "Jan Novák", "Strahov");
        } catch (NoItemInStorage e) {
            assertEquals("No item in storage", e.getMessage());
        }
    }
    @Test
    public void SixthTestSequence () {//1-4-7-10-11-13-15-16
        //vytvoření položek do skladu
        StandardItem firstItem = new StandardItem(1, "Motorová pila Husqarna", 2499f, "motorovky", 1000);
        StandardItem secondItem = new StandardItem(2, "Motorová pila Suzuki", 2199f, "motorovky", 800);
        StandardItem thirdItem = new StandardItem(3, "Motorová pila Kazashi", 3399f, "motorovky", 1400);
        Storage store = new Storage();
        //vložení položek do skladu
        store.insertItems(firstItem,2);
        store.insertItems(secondItem,4);
        //nový archiv pro eshopControler
        PurchasesArchive archive = new PurchasesArchive();
        EShopController shop = new EShopController(store, archive);
        ShoppingCart cart = new ShoppingCart();
        //do kosiku vlozime item, ktery neni ve skladu
        cart.addItem(thirdItem);
        try {
            shop.purchaseShoppingCart(cart, "Pepa", "Babovřesky");
        } catch (NoItemInStorage e) {
            assertEquals("No item in storage", e.getMessage());
        }
    }
    @Test
    public void SeventhTestSequence () throws NoItemInStorage {//1-4-7-10-11-13-15-17
        //vytvoření položek do skladu
        StandardItem firstItem = new StandardItem(1, "Motorová pila Husqarna", 2499f, "Pily", 1);
        StandardItem secondItem = new StandardItem(2, "Motorová pila Suzuki", 2199f, "Pily", 8);
        StandardItem thirdItem = new StandardItem(3, "Motorová pila Kazashi", 3399f, "Pily", 142);
        Storage store = new Storage();
        //vložení položek do skladu
        store.insertItems(firstItem,5);
        store.insertItems(secondItem,4);
        store.insertItems(thirdItem, 2);
        //nový archiv pro eshopControler
        PurchasesArchive archive = new PurchasesArchive();
        EShopController shop = new EShopController(store, archive);
        ShoppingCart cart = new ShoppingCart();
        //do kosiku vlozime item, ktery neni ve skladu
        cart.addItem(thirdItem);
        cart.addItem(thirdItem);
        cart.addItem(firstItem);
        cart.addItem(firstItem);
        cart.addItem(secondItem);
        //provedeme nákup z naplněného košíku
        shop.purchaseShoppingCart(cart, "Jan Novákk", "Strahov");
        //ověříme, že se ze skladu odepsaly položky
        Assertions.assertEquals(0, store.getItemCount(thirdItem));
        Assertions.assertEquals(3, store.getItemCount(firstItem));
        Assertions.assertEquals(3, store.getItemCount(secondItem));
        //ověříme, že se do inventáře zapsaly prodané položky
        Assertions.assertEquals(2, archive.getHowManyTimesHasBeenItemSold(thirdItem));
        Assertions.assertEquals(2, archive.getHowManyTimesHasBeenItemSold(firstItem));
        Assertions.assertEquals(1, archive.getHowManyTimesHasBeenItemSold(secondItem));
    }
}


