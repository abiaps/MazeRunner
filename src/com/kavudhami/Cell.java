package com.kavudhami;

/**
 * Created by Kavudhami on 9/2/2017.
 */
public class Cell {

    private int width = 0;
    private int height = 0;
    private int value = -1;
    private Cell predecessor = null;
    private int life;

    public Cell(int w, int h, int val){
        this.width = w;
        this.height = h;
        this.value = val;
        life = 3;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getValue(){
        return value;
    }

    public Cell getPredecessor(){
        return predecessor;
    }

    public void setWidth(int w) { this.width = w; }

    public void setHeight(int h) { this.height = h; }

    public void setValue(int val) { this.value = val; }

    public void setPredecessor(Cell cell){
        this.predecessor = cell;
    }

    public void die(){
        this.life--;
    }

    public int getLife(){ return this.life; }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Cell other = (Cell) obj;
        return other.getWidth() == this.getWidth()
                && other.getHeight() == this.getHeight()
                && other.getValue() == this.getValue();

    }
}
