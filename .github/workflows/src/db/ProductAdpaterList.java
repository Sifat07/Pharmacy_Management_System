/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.math.RoundingMode;
import java.text.DecimalFormat;


/**
 *
 * @author nishan
 */
public class ProductAdpaterList {
    private int serial;
    private int id;
    private String name;
    private double ppu;
    private int available;
    private double total;

    private final DecimalFormat df = new DecimalFormat(".##");
    
    public ProductAdpaterList(int serial, int id, String name, double ppu, int available) {
        df.setRoundingMode(RoundingMode.CEILING);
        this.serial = serial;
        this.id = id;
        this.name = name;
        this.ppu = ppu;
        this.available = available;
        this.total = Double.parseDouble(df.format(ppu * available));
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public double getPpu() {
        return ppu;
    }

    public void setPpu(double ppu) {
        this.ppu = ppu;
    }
    
    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }
    
    public double getTotal() {
        return Double.parseDouble(df.format(total));
    }

    public void setTotal(double total) {
        this.total = Double.parseDouble(df.format(total));
    }
}
