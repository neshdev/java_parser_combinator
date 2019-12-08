package com.company;

public class Text {
    private final Character[] arr;

    public Text(String str){
        this.arr = new Character[str.length()];
        for (int i = 0; i < str.length(); i++) {
            arr[i] = str.charAt(i);
        }
        this.pointer = 0;
    }

    int pointer;

    public Character head(){
        return arr[pointer];
    }

    public Text advance(){
        pointer++;
        return this;
    }

    public boolean canAdvance(){
        return (pointer) < arr.length - 1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = pointer; i < arr.length; i++) {
            sb.append(arr[i]);
        }
        return sb.toString();
    }
}
