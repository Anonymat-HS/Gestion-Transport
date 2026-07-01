package com.transport.n3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests - TypeVoiture enum")
public class TypeVoitureTest {

    @Test
    @DisplayName("LITE a 16 places")
    void lite_a16places() {
        // GIVEN
        TypeVoiture type = TypeVoiture.LITE;
        // WHEN
        int places = type.getNbrePlaces();
        // THEN
        assertEquals(16, places);
    }

    @Test
    @DisplayName("PREMIUM a 16 places")
    void premium_a16places() {
        // GIVEN
        TypeVoiture type = TypeVoiture.PREMIUM;
        // WHEN
        int places = type.getNbrePlaces();
        // THEN
        assertEquals(16, places);
    }

    @Test
    @DisplayName("VIP a 8 places")
    void vip_a8places() {
        // GIVEN
        TypeVoiture type = TypeVoiture.VIP;
        // WHEN
        int places = type.getNbrePlaces();
        // THEN
        assertEquals(8, places);
    }

    @Test
    @DisplayName("VVIP a 8 places")
    void vvip_a8places() {
        // GIVEN
        TypeVoiture type = TypeVoiture.VVIP;
        // WHEN
        int places = type.getNbrePlaces();
        // THEN
        assertEquals(8, places);
    }
}
