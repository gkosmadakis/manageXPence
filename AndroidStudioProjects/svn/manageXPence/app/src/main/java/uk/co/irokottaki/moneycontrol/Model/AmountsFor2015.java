package uk.co.irokottaki.moneycontrol.Model;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class AmountsFor2015 extends AmountsForYear {

    private Float amountJan;
    private Float amountFeb;
    private Float amountMar;
    private Float amountApr;
    private Float amountMay;
    private Float amountJun;
    private Float amountJul;
    private Float amountAug;
    private Float amountSep;
    private Float amountOct;
    private Float amountNov;
    private Float amountDec;
    private LinkedHashSet<String> descriptionsForOct15,descriptionsForNov15, descriptionsForDec15;
    private ArrayList<Float> arrayOfamountOct15, arrayOfamountNov15, arrayOfamountDec15;
    private String fileLineOct15, fileLineNov15, fileLineDec15;

    public AmountsFor2015(Float jan, Float feb, Float mar, Float apr, Float may, Float jun, Float jul,
                          Float aug, Float sep, Float oct, Float nov, Float dec){


        super();
        amountJan = jan;
        amountFeb = feb;
        amountMar = mar;
        amountApr = apr;
        amountMay = may;
        amountJun = jun;
        amountJul = jul;
        amountAug = aug;
        amountSep = sep;
        amountOct = oct;
        amountNov = nov;
        amountDec = dec;
        descriptionsForOct15 = new LinkedHashSet<>();
        descriptionsForNov15 = new LinkedHashSet<>();
        descriptionsForDec15 = new LinkedHashSet<>();
        arrayOfamountOct15 = new ArrayList<>();
        arrayOfamountNov15 = new ArrayList<>();
        arrayOfamountDec15 = new ArrayList<>();
        fileLineOct15 = "";
        fileLineNov15 = "";
        fileLineDec15 = "";

    }

    public Float getAmountJan() { return amountJan; }

    public Float getAmountFeb() { return amountFeb; }

    public Float getAmountMar() {
        return amountMar;
    }

    public Float getAmountApr() {
        return amountApr;
    }

    public Float getAmountMay() {
        return amountMay;
    }

    public Float getAmountJun() {
        return amountJun;
    }

    public Float getAmountJul() {
        return amountJul;
    }

    public Float getAmountAug() {
        return amountAug;
    }

    public Float getAmountSep() {
        return amountSep;
    }

    public Float getAmountOct() { return amountOct; }

    public Float getAmountNov() {
        return amountNov;
    }

    public Float getAmountDec() {
        return amountDec;
    }

    public void setAmountJan(Float amountJan) { this.amountJan = amountJan; }

    public void setAmountFeb(Float amountFeb) { this.amountFeb = amountFeb; }

    public void setAmountMar(Float amountMar) {
        this.amountMar = amountMar;
    }

    public void setAmountApr(Float amountApr) {
        this.amountApr = amountApr;
    }

    public void setAmountMay(Float amountMay) {
        this.amountMay = amountMay;
    }

    public void setAmountJun(Float amountJun) {
        this.amountJun = amountJun;
    }

    public void setAmountJul(Float amountJul) {
        this.amountJul = amountJul;
    }

    public void setAmountAug(Float amountAug) {
        this.amountAug = amountAug;
    }

    public void setAmountSep(Float amountSep) {
        this.amountSep = amountSep;
    }

    public void setAmountOct(Float amountOct) { this.amountOct = amountOct; }

    public void setAmountNov(Float amountNov) {
        this.amountNov = amountNov;
    }

    public void setAmountDec(Float amountDec) {
        this.amountDec = amountDec;
    }

    public void setDescriptionsForOct15(LinkedHashSet description) { descriptionsForOct15 = description; }

    public void setDescriptionsForNov15(LinkedHashSet description) { descriptionsForNov15 = description; }

    public void setDescriptionsForDec15(LinkedHashSet description) { descriptionsForDec15 = description; }

    public LinkedHashSet<String> getDescriptionsForOct15() { return descriptionsForOct15; }

    public LinkedHashSet<String> getDescriptionsForNov15() { return descriptionsForNov15; }

    public LinkedHashSet<String> getDescriptionsForDec15() { return descriptionsForDec15; }

    public ArrayList<Float> getArrayOfamountOct15() {
        return arrayOfamountOct15;
    }

    public ArrayList<Float> getArrayOfamountNov15() {
        return arrayOfamountNov15;
    }

    public ArrayList<Float> getArrayOfamountDec15() {
        return arrayOfamountDec15;
    }

    public void setArrayOfamountOct15(ArrayList<Float> arrayOfamountOct15) {
        this.arrayOfamountOct15 = arrayOfamountOct15;
    }

    public void setArrayOfamountNov15(ArrayList<Float> arrayOfamountNov15) {
        this.arrayOfamountNov15 = arrayOfamountNov15;
    }

    public void setArrayOfamountDec15(ArrayList<Float> arrayOfamountDec15) {
        this.arrayOfamountDec15 = arrayOfamountDec15;
    }

    public String getFileLineOct15() {
        return fileLineOct15;
    }

    public String getFileLineNov15() {
        return fileLineNov15;
    }

    public String getFileLineDec15() {
        return fileLineDec15;
    }

    public void setFileLineOct15(String fileLineOct15) {
        this.fileLineOct15 = fileLineOct15;
    }

    public void setFileLineNov15(String fileLineNov15) {
        this.fileLineNov15 = fileLineNov15;
    }

    public void setFileLineDec15(String fileLineDec15) {
        this.fileLineDec15 = fileLineDec15;
    }
}
