package org.texttechnologylab.project.helper.helperimplementation;

import org.junit.jupiter.api.Test;

import java.util.Map;

class StammdatenReaderTest {

    @Test
    Map<String, Map> stammdatenrd(String s) {
        Map<String, Map> ab1 = stammdatenrd("C:\\Users\\felix\\OneDrive\\Desktop\\Uni\\Semester 3\\PPR\\PPR-Programme\\uebung-2\\src\\main\\resources\\MdB-Stammdaten\\MDB_STAMMDATEN.XML");
    return ab1;
    }

    void ausgabe(){
        System.out.println();
    }
  
}