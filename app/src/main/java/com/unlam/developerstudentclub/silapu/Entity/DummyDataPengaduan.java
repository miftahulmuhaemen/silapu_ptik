package com.unlam.developerstudentclub.silapu.Entity;

import java.util.ArrayList;

public class DummyDataPengaduan {
    public static String[][] data = new String[][]{
            {"Soekarno", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Presiden_Sukarno.jpg/418px-Presiden_Sukarno.jpg", "DOC",
                    "Soekarno's Case", "1/20/2018","Pada masa penjajahan, dahulu kala ada sebuah kisah aselole.", "Penjajahan"},
            {"Soeharto", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/President_Suharto%2C_1993.jpg/468px-President_Suharto%2C_1993.jpg", "DOC",
                    "Soeharto's Case", "1/20/2018","Pada masa penjajahan, dahulu kala ada sebuah kisah aselole.", "Penjajahan"},
            {"Soekarno", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Presiden_Sukarno.jpg/418px-Presiden_Sukarno.jpg", "DOC",
                    "Soekarno's Case", "1/20/2018","Pada masa penjajahan, dahulu kala ada sebuah kisah aselole.", "Penjajahan"},
            {"Soekarno", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Presiden_Sukarno.jpg/418px-Presiden_Sukarno.jpg", "DOC",
                    "Soekarno's Case", "1/20/2018","Pada masa penjajahan, dahulu kala ada sebuah kisah aselole.", "Penjajahan"},
            {"Soekarno", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Presiden_Sukarno.jpg/418px-Presiden_Sukarno.jpg", "DOC",
                    "Soekarno's Case", "1/20/2018","Pada masa penjajahan, dahulu kala ada sebuah kisah aselole.", "Penjajahan"},
            {"Soekarno", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Presiden_Sukarno.jpg/418px-Presiden_Sukarno.jpg", "DOC",
                    "Soekarno's Case", "1/20/2018","Pada masa penjajahan, dahulu kala ada sebuah kisah aselole.", "Penjajahan"},
            {"Soekarno", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Presiden_Sukarno.jpg/418px-Presiden_Sukarno.jpg", "DOC",
                    "Soekarno's Case", "1/20/2018","Pada masa penjajahan, dahulu kala ada sebuah kisah aselole.", "Penjajahan"},
            {"Soekarno", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Presiden_Sukarno.jpg/418px-Presiden_Sukarno.jpg", "DOC",
                    "Soekarno's Case", "1/20/2018","Pada masa penjajahan, dahulu kala ada sebuah kisah aselole.", "Penjajahan"},
            {"Soekarno", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Presiden_Sukarno.jpg/418px-Presiden_Sukarno.jpg", "DOC",
                    "Soekarno's Case", "1/20/2018","Pada masa penjajahan, dahulu kala ada sebuah kisah aselole.", "Penjajahan"},
            {"Soekarno", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Presiden_Sukarno.jpg/418px-Presiden_Sukarno.jpg", "DOC",
                    "Soekarno's Case", "1/20/2018","Pada masa penjajahan, dahulu kala ada sebuah kisah aselole.", "Penjajahan"},
            {"Soekarno", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Presiden_Sukarno.jpg/418px-Presiden_Sukarno.jpg", "DOC",
                    "Soekarno's Case", "1/20/2018","Pada masa penjajahan, dahulu kala ada sebuah kisah aselole.", "Penjajahan"}
    };

    public static ArrayList<PengaduanItem> getListData(){
        PengaduanItem item = null;
        ArrayList<PengaduanItem> list = new ArrayList<>();
        for (int i = 0; i <data.length; i++) {
            item = new PengaduanItem();
            item.setSender(data[i][0]);
            item.setProfilImg(data[i][1]);
            item.setFiletype(data[i][2]);
            item.setFilename(data[i][3]);
            item.setFiledates(data[i][4]);
            item.setContent(data[i][5]);
            item.setClassification(data[i][6]);

            list.add(item);
        }

        return list;
    }
}
