package dataModel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BtwBedragenTest {

    @Test
    void getBEDRAG_ZONDER_BTW_Test(){
        BtwBedragen btwBedragen = new BtwBedragen(121.00);
        assertEquals(100.00, btwBedragen.getBEDRAG_ZONDER_BTW());
    }

    @Test
    void getBTW_BEDRAG_Test(){
        BtwBedragen btwBedragen = new BtwBedragen(121.00);
        assertEquals(21.00, btwBedragen.getBTW_BEDRAG());
    }

}
