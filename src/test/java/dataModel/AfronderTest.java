package dataModel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AfronderTest {

    @Test
    void bedragInEurosAfrondenTest(){
        double bedrag = Afronder.bedragInEurosAfronden(10.2222);
        assertEquals(10.22, bedrag);
    }
}
