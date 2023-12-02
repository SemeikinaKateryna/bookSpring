package com.example.bookspring.dao;



public class DaoFactory {
    // Використання паттерну Singleton
    private static IFabric instance = null;

    private DaoFactory() {
    }

    // Фабричний метод
    public static IFabric getDAOInstance(TypeDao type) {
        if (instance == null) {
            synchronized (DaoFactory.class) {
                if (instance == null) {
                    switch (type) {
                        case MY_SQL:
                            instance = new MySqlFabric();
                            break;
                        default:
                            throw new IllegalArgumentException("Unsupported DAO type: " + type);
                    }
                }
            }
        }
        return instance;
    }
}

