package com.unlam.developerstudentclub.silapu.Entity;

import java.util.ArrayList;

public class DummyDataPerdata {
    public static String[][] data = new String[][]{

            {"Soekarno",
                    "Soekarno's Case", "Pada masa penjajahan, dahulu kala ada sebuah kisah aselole.", "Penjajahan"},
            {"Soeharto",
                    "Soeharto's Case","Pada masa penjajahan, dahulu kala ada sebuah kisah aselole.", "Penjajahan"},
            {"Soekarno",
                    "Soekarno's Case", "Pada masa penjajahan, dahulu kala ada sebuah kisah aselole.", "Penjajahan"},
            {"Soeharto",
                    "Soeharto's Case","Pada masa penjajahan, dahulu kala ada sebuah kisah aselole.", "Penjajahan"},
            {"Soekarno",
                    "Soekarno's Case", "Pada masa penjajahan, dahulu kala ada sebuah kisah aselole.", "Penjajahan"},
            {"Soeharto",
                    "Soeharto's Case","Pada masa penjajahan, dahulu kala ada sebuah kisah aselole.", "Penjajahan"}
    };

    public static ArrayList<PerdataItem> getListData(){
        PerdataItem item = null;
        ArrayList<PerdataItem> list = new ArrayList<>();
        for (int i = 0; i <data.length; i++) {
            item = new PerdataItem();
            item.setSender(data[i][0]);
            item.setPurpose(data[i][1]);
            item.setContent(data[i][2]);
            item.setClassification(data[i][3]);
            list.add(item);
        }

        return list;
    }
}
