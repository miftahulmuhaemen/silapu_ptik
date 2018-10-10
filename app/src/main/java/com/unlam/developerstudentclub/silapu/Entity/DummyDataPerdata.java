package com.unlam.developerstudentclub.silapu.Entity;

import java.util.ArrayList;

public class DummyDataPerdata {
    public static String[][] data = new String[][]{

            {"Soekarno", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Presiden_Sukarno.jpg/418px-Presiden_Sukarno.jpg",
                    "Soekarno's Case", "Pada masa penjajahan, dahulu kala ada sebuah kisah aselole.", "Penjajahan"},
            {"Soeharto", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/President_Suharto%2C_1993.jpg/468px-President_Suharto%2C_1993.jpg",
                    "Soeharto's Case","Pada masa penjajahan, dahulu kala ada sebuah kisah aselole.", "Penjajahan"},
            {"Soekarno", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Presiden_Sukarno.jpg/418px-Presiden_Sukarno.jpg",
                    "Soekarno's Case", "Pada masa penjajahan, dahulu kala ada sebuah kisah aselole.", "Penjajahan"},
            {"Soekarno", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Presiden_Sukarno.jpg/418px-Presiden_Sukarno.jpg",
                    "Soekarno's Case", "Pada masa penjajahan, dahulu kala ada sebuah kisah aselole.", "Penjajahan"},
            {"Soekarno", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Presiden_Sukarno.jpg/418px-Presiden_Sukarno.jpg",
                    "Soekarno's Case", "Pada masa penjajahan, dahulu kala ada sebuah kisah aselole.", "Penjajahan"},
            {"Soekarno", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Presiden_Sukarno.jpg/418px-Presiden_Sukarno.jpg",
                    "Soekarno's Case", "Pada masa penjajahan, dahulu kala ada sebuah kisah aselole.", "Penjajahan"},
            {"Soekarno", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Presiden_Sukarno.jpg/418px-Presiden_Sukarno.jpg",
                    "Soekarno's Case", "Pada masa penjajahan, dahulu kala ada sebuah kisah aselole.", "Penjajahan"},
            {"Soekarno", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Presiden_Sukarno.jpg/418px-Presiden_Sukarno.jpg",
                    "Soekarno's Case", "Pada masa penjajahan, dahulu kala ada sebuah kisah aselole.", "Penjajahan"},
            {"Soekarno", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Presiden_Sukarno.jpg/418px-Presiden_Sukarno.jpg",
                    "Soekarno's Case", "Pada masa penjajahan, dahulu kala ada sebuah kisah aselole.", "Penjajahan"},
            {"Soekarno", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Presiden_Sukarno.jpg/418px-Presiden_Sukarno.jpg",
                    "Soekarno's Case", "Pada masa penjajahan, dahulu kala ada sebuah kisah aselole.", "Penjajahan"}
    };

    public static ArrayList<PerdataItem> getListData(){
        PerdataItem item = null;
        ArrayList<PerdataItem> list = new ArrayList<>();
        for (int i = 0; i <data.length; i++) {
            item = new PerdataItem();
            item.setSender(data[i][0]);
            item.setProfilImg(data[i][1]);
            item.setPurpose(data[i][2]);
            item.setContent(data[i][3]);
            item.setClassification(data[i][4]);
            list.add(item);
        }

        return list;
    }
}
