package com.example.bookspring.observer;

public interface Observable {
    void registerObserver(Observer var1);
    void removeObserver(Observer var1);
    void notifyObserversUpdate();
    void notifyObserversInsert();
    void notifyObserversDelete();
}