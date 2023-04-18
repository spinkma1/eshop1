package shop;
import org.junit.jupiter.api.Test;
import shop.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    public void FirstMethodTest(){
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new StandardItem(1, "Knížka", 120.0f, "Knihy", 5));
        cart.addItem(new StandardItem(2, "Boty", 1800.0f, "Obuv", 99));
        cart.addItem(new StandardItem(3, "Škoda Fabia", 1800000.0f, "Auta", 9999));
        Order order = new Order(cart, "Jan Novák", "Adresa Nováka", 7894);

        assertEquals("Jan Novák", order.customerName);
        assertEquals("Adresa Nováka", order.customerAddress);
        assertEquals(7894, order.state);
        ArrayList<Item> items = order.getItems();
        assertEquals(3, items.size());
        assertEquals(cart.getCartItems(), items);
    }

    @Test
    public void SecondMethodTest(){
        ShoppingCart cart = new ShoppingCart();
        Order order = new Order(cart, "Jan Novák", "Adresa Nováka");

        assertEquals("Jan Novák", order.customerName);
        assertEquals("Adresa Nováka", order.customerAddress);
        assertEquals(0, order.state);
        ArrayList<Item> items = order.getItems();
        assertEquals(0, items.size());
        assertEquals(cart.getCartItems(), items);
    }

    @Test
    public void NullValueTest(){
        try{
            Order Order = new Order(null, "Novák", "adresa", 5);
            fail();
        }catch(NullPointerException e){
            assertTrue(e instanceof NullPointerException);
        }

    }
}