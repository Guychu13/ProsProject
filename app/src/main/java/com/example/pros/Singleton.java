package com.example.pros;

public class Singleton {

    private static Singleton instance = null;//משתנה של המחלקה, מקובל לקרוא אינסטנס

    public static Singleton getInstance(){//פעולה סטטית זו פעולה שמפעילים על המחלקה
        if(instance == null){
            instance = new Singleton();
        }
        return instance;
    }

    private Singleton() {//פה לאתחל אם יש אתחולים
    }
}
