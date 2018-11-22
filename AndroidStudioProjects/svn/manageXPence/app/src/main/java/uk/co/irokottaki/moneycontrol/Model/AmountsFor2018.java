package uk.co.irokottaki.moneycontrol.Model;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class AmountsFor2018 extends AmountsForYear {

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
    private LinkedHashSet<String> descriptionsForJan, descriptionsForFeb, descriptionsForMar, descriptionsForApr, descriptionsForMay, descriptionsForJun,
            descriptionsForJul, descriptionsForAug, descriptionsForSep, descriptionsForOct, descriptionsForNov, descriptionsForDec;
    private ArrayList<Float> arrayOfamountJan, arrayOfamountFeb, arrayOfamountMar, arrayOfamountApr, arrayOfamountMay, arrayOfamountJun, arrayOfamountJul,
            arrayOfamountAug, arrayOfamountSep, arrayOfamountOct, arrayOfamountNov, arrayOfamountDec;
    private String fileLineJan, fileLineFeb, fileLineMar, fileLineApr, fileLineMay, fileLineJun,
            fileLineJul, fileLineAug, fileLineSep,
            fileLineOct, fileLineNov, fileLineDec;

    public AmountsFor2018(Float jan, Float feb, Float mar, Float apr, Float may, Float jun, Float jul,
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
        descriptionsForJan = new LinkedHashSet<>();//store unique descriptions
        descriptionsForFeb = new LinkedHashSet<>();
        descriptionsForMar = new LinkedHashSet<>();
        descriptionsForApr = new LinkedHashSet<>();
        descriptionsForMay = new LinkedHashSet<>();//store unique descriptions
        descriptionsForJun = new LinkedHashSet<>();
        descriptionsForJul = new LinkedHashSet<>();
        descriptionsForAug = new LinkedHashSet<>();
        descriptionsForSep = new LinkedHashSet<>();//store unique descriptions
        descriptionsForOct = new LinkedHashSet<>();
        descriptionsForNov = new LinkedHashSet<>();
        descriptionsForDec = new LinkedHashSet<>();
        arrayOfamountJan = new ArrayList<>();
        arrayOfamountFeb = new ArrayList<>();
        arrayOfamountMar = new ArrayList<>();
        arrayOfamountApr = new ArrayList<>();
        arrayOfamountMay = new ArrayList<>();
        arrayOfamountJun = new ArrayList<>();
        arrayOfamountJul = new ArrayList<>();
        arrayOfamountAug = new ArrayList<>();
        arrayOfamountSep = new ArrayList<>();
        arrayOfamountOct = new ArrayList<>();
        arrayOfamountNov = new ArrayList<>();
        arrayOfamountDec = new ArrayList<>();
        fileLineJan = "";
        fileLineFeb = "";
        fileLineMar = "";
        fileLineApr = "";
        fileLineMay = "";
        fileLineJun = "";
        fileLineJul = "";
        fileLineAug = "";
        fileLineSep = "";
        fileLineOct = "";
        fileLineNov = "";
        fileLineDec = "";
    }

    public Float getAmountJan() {
        return amountJan;
    }

    public Float getAmountFeb() {
        return amountFeb;
    }

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

    public Float getAmountSep() { return amountSep; }

    public Float getAmountOct() { return amountOct; }

    public Float getAmountNov() {
        return amountNov;
    }

    public Float getAmountDec() {
        return amountDec;
    }

    public void setAmountJan(Float amountJan) {
        this.amountJan = amountJan;
    }

    public void setAmountFeb(Float amountFeb) {
        this.amountFeb = amountFeb;
    }

    public void setAmountMar(Float amountMar) {
        this.amountMar = amountMar;
    }

    public void setAmountApr(Float amountApr) { this.amountApr = amountApr; }

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

    public void setAmountOct(Float amountOct) {
        this.amountOct = amountOct;
    }

    public void setAmountNov(Float amountNov) {
        this.amountNov = amountNov;
    }

    public void setAmountDec(Float amountDec) {
        this.amountDec = amountDec;
    }

    public LinkedHashSet<String> getDescriptionsForJan() {
        return descriptionsForJan;
    }

    public LinkedHashSet<String> getDescriptionsForFeb() {
        return descriptionsForFeb;
    }

    public LinkedHashSet<String> getDescriptionsForMar() {
        return descriptionsForMar;
    }

    public LinkedHashSet<String> getDescriptionsForApr() {
        return descriptionsForApr;
    }

    public LinkedHashSet<String> getDescriptionsForMay() {
        return descriptionsForMay;
    }

    public LinkedHashSet<String> getDescriptionsForJun() {
        return descriptionsForJun;
    }

    public LinkedHashSet<String> getDescriptionsForJul() {
        return descriptionsForJul;
    }

    public LinkedHashSet<String> getDescriptionsForAug() {
        return descriptionsForAug;
    }

    public LinkedHashSet<String> getDescriptionsForSep() {
        return descriptionsForSep;
    }

    public LinkedHashSet<String> getDescriptionsForOct() {
        return descriptionsForOct;
    }

    public LinkedHashSet<String> getDescriptionsForNov() {
        return descriptionsForNov;
    }

    public LinkedHashSet<String> getDescriptionsForDec() {
        return descriptionsForDec;
    }

    public void setDescriptionsForJan(LinkedHashSet<String> descriptionsForJan) {
        this.descriptionsForJan = descriptionsForJan;
    }

    public void setDescriptionsForFeb(LinkedHashSet<String> descriptionsForFeb) {
        this.descriptionsForFeb = descriptionsForFeb;
    }

    public void setDescriptionsForMar(LinkedHashSet<String> descriptionsForMar) {
        this.descriptionsForMar = descriptionsForMar;
    }

    public void setDescriptionsForApr(LinkedHashSet<String> descriptionsForApr) {
        this.descriptionsForApr = descriptionsForApr;
    }

    public void setDescriptionsForMay(LinkedHashSet<String> descriptionsForMay) {
        this.descriptionsForMay = descriptionsForMay;
    }

    public void setDescriptionsForJun(LinkedHashSet<String> descriptionsForJun) {
        this.descriptionsForJun = descriptionsForJun;
    }

    public void setDescriptionsForJul(LinkedHashSet<String> descriptionsForJul) {
        this.descriptionsForJul = descriptionsForJul;
    }

    public void setDescriptionsForAug(LinkedHashSet<String> descriptionsForAug) {
        this.descriptionsForAug = descriptionsForAug;
    }

    public void setDescriptionsForSep(LinkedHashSet<String> descriptionsForSep) {
        this.descriptionsForSep = descriptionsForSep;
    }

    public void setDescriptionsForOct(LinkedHashSet<String> descriptionsForOct) {
        this.descriptionsForOct = descriptionsForOct;
    }

    public void setDescriptionsForNov(LinkedHashSet<String> descriptionsForNov) {
        this.descriptionsForNov = descriptionsForNov;
    }

    public void setDescriptionsForDec(LinkedHashSet<String> descriptionsForDec) {
        this.descriptionsForDec = descriptionsForDec;
    }

    public ArrayList<Float> getArrayOfamountJan() {
        return arrayOfamountJan;
    }

    public ArrayList<Float> getArrayOfamountFeb() {
        return arrayOfamountFeb;
    }

    public ArrayList<Float> getArrayOfamountMar() {
        return arrayOfamountMar;
    }

    public ArrayList<Float> getArrayOfamountApr() {
        return arrayOfamountApr;
    }

    public ArrayList<Float> getArrayOfamountMay() {
        return arrayOfamountMay;
    }

    public ArrayList<Float> getArrayOfamountJun() {
        return arrayOfamountJun;
    }

    public ArrayList<Float> getArrayOfamountJul() {
        return arrayOfamountJul;
    }

    public ArrayList<Float> getArrayOfamountAug() {
        return arrayOfamountAug;
    }

    public ArrayList<Float> getArrayOfamountSep() {
        return arrayOfamountSep;
    }

    public ArrayList<Float> getArrayOfamountOct() {
        return arrayOfamountOct;
    }

    public ArrayList<Float> getArrayOfamountNov() {
        return arrayOfamountNov;
    }

    public ArrayList<Float> getArrayOfamountDec() {
        return arrayOfamountDec;
    }

    public void setArrayOfamountJan(ArrayList<Float> arrayOfamountJan) {
        this.arrayOfamountJan = arrayOfamountJan;
    }

    public void setArrayOfamountFeb(ArrayList<Float> arrayOfamountFeb) {
        this.arrayOfamountFeb = arrayOfamountFeb;
    }

    public void setArrayOfamountMar(ArrayList<Float> arrayOfamountMar) {
        this.arrayOfamountMar = arrayOfamountMar;
    }

    public void setArrayOfamountApr(ArrayList<Float> arrayOfamountApr) {
        this.arrayOfamountApr = arrayOfamountApr;
    }

    public void setArrayOfamountMay(ArrayList<Float> arrayOfamountMay) {
        this.arrayOfamountMay = arrayOfamountMay;
    }

    public void setArrayOfamountJun(ArrayList<Float> arrayOfamountJun) {
        this.arrayOfamountJun = arrayOfamountJun;
    }

    public void setArrayOfamountJul(ArrayList<Float> arrayOfamountJul) {
        this.arrayOfamountJul = arrayOfamountJul;
    }

    public void setArrayOfamountAug(ArrayList<Float> arrayOfamountAug) {
        this.arrayOfamountAug = arrayOfamountAug;
    }

    public void setArrayOfamountSep(ArrayList<Float> arrayOfamountSep) {
        this.arrayOfamountSep = arrayOfamountSep;
    }

    public void setArrayOfamountOct(ArrayList<Float> arrayOfamountOct) {
        this.arrayOfamountOct = arrayOfamountOct;
    }

    public void setArrayOfamountNov(ArrayList<Float> arrayOfamountNov) {
        this.arrayOfamountNov= arrayOfamountNov;
    }

    public void setArrayOfamountDec(ArrayList<Float> arrayOfamountDec) {
        this.arrayOfamountDec = arrayOfamountDec;
    }

    public String getFileLineJan() {
        return fileLineJan;
    }

    public String getFileLineFeb() {
        return fileLineFeb;
    }

    public String getFileLineMar() {
        return fileLineMar;
    }

    public String getFileLineApr() {
        return fileLineApr;
    }

    public String getFileLineMay() {
        return fileLineMay;
    }

    public String getFileLineJun() {
        return fileLineJun;
    }

    public String getFileLineJul() {
        return fileLineJul;
    }

    public String getFileLineAug() {
        return fileLineAug;
    }

    public String getFileLineSep() {
        return fileLineSep;
    }

    public String getFileLineOct() {
        return fileLineOct;
    }

    public String getFileLineNov() {
        return fileLineNov;
    }

    public String getFileLineDec() {
        return fileLineDec;
    }

    public void setFileLineJan(String fileLineJan) {
        this.fileLineJan = fileLineJan;
    }

    public void setFileLineFeb(String fileLineFeb) {
        this.fileLineFeb = fileLineFeb;
    }

    public void setFileLineMar(String fileLineMar) {
        this.fileLineMar = fileLineMar;
    }

    public void setFileLineApr(String fileLineApr) {
        this.fileLineApr = fileLineApr;
    }

    public void setFileLineMay(String fileLineMay) {
        this.fileLineMay = fileLineMay;
    }

    public void setFileLineJun(String fileLineJun) {
        this.fileLineJun = fileLineJun;
    }

    public void setFileLineJul(String fileLineJul) {
        this.fileLineJul = fileLineJul;
    }

    public void setFileLineAug(String fileLineAug) {
        this.fileLineAug = fileLineAug;
    }

    public void setFileLineSep(String fileLineSep) {
        this.fileLineSep = fileLineSep;
    }

    public void setFileLineOct(String fileLineOct) {
        this.fileLineOct = fileLineOct;
    }

    public void setFileLineNov(String fileLineNov) {
        this.fileLineNov = fileLineNov;
    }

    public void setFileLineDec(String fileLineDec) {
        this.fileLineDec = fileLineDec;
    }
}
