package it.unibo.dinerdash.view.impl;

public class ResizeLogic {
   private int altezzaCl;
   private int larghezzaCl;
   private double percentualeAltezza;
   private double percentualeLargezza;
   private double AlSchermo=1002;             //da mofificare con int 
   private double LargSchermo=1888;
    public ResizeLogic(int heightMonitor, int widthMonitor) {
        this.AlSchermo=heightMonitor;
        this.LargSchermo=widthMonitor;
    }
}
