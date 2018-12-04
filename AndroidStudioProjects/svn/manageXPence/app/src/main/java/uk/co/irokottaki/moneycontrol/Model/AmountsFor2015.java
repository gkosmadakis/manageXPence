package uk.co.irokottaki.moneycontrol.Model;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import uk.co.irokottaki.moneycontrol.YearInterface;

public class AmountsFor2015 extends AmountsForYear implements YearInterface {

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
    private LinkedHashSet<String> descriptionsForJan, descriptionsForFeb, descriptionsForMar, descriptionsForApr, descriptionsForMay,
            descriptionsForJun, descriptionsForJul, descriptionsForAug, descriptionsForSep, descriptionsForOct, descriptionsForNov, descriptionsForDec;
    private ArrayList<Float> arrayOfamountJan, arrayOfamountFeb, arrayOfamountMar, arrayOfamountApr, arrayOfamountMay, arrayOfamountJun,
            arrayOfamountJul, arrayOfamountAug, arrayOfamountSep, arrayOfamountOct, arrayOfamountNov, arrayOfamountDec;
    private String fileLineJan, fileLineFeb, fileLineMar, fileLineApr, fileLineMay, fileLineJun, fileLineJul, fileLineAug, fileLineSep,
            fileLineOct, fileLineNov, fileLineDec;

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

   /* public Float getAmountJan() { return amountJan; }

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



    public void setAmountOct(Float amountOct) { this.amountOct = amountOct; }

    public void setAmountNov(Float amountNov) {
        this.amountNov = amountNov;
    }

    public void setAmountDec(Float amountDec) {
        this.amountDec = amountDec;
    }*/

    @Override
    public Float getAmountJan() {
        return amountJan;
    }

    @Override
    public void setAmountJan(Float amountJan) {
        this.amountJan = amountJan;
    }

    @Override
    public ArrayList<Float> getArrayOfamountJan() {
        return arrayOfamountJan;
    }

    @Override
    public void setArrayOfamountJan(ArrayList<Float> arrayOfamountJan) {
        this.arrayOfamountJan = arrayOfamountJan;
    }

    @Override
    public String getFileLineJan() {
        return fileLineJan;
    }

    @Override
    public void setFileLineJan(String fileLineJan) {
        this.fileLineJan = fileLineJan;
    }

    @Override
    public LinkedHashSet<String> getDescriptionsForJan() {
        return descriptionsForJan;
    }

    @Override
    public void setDescriptionsForJan(LinkedHashSet<String> descriptionsForJan) {
        this.descriptionsForJan = descriptionsForJan;
    }

    @Override
    public Float getAmountFeb() {
        return amountFeb;
    }

    @Override
    public void setAmountFeb(Float amountFeb) {
        this.amountFeb = amountFeb;
    }

    @Override
    public ArrayList<Float> getArrayOfamountFeb() {
        return arrayOfamountFeb;
    }

    @Override
    public void setArrayOfamountFeb(ArrayList<Float> arrayOfamountFeb) {
        this.arrayOfamountFeb = arrayOfamountFeb;
    }

    @Override
    public String getFileLineFeb() {
        return fileLineFeb;
    }

    @Override
    public void setFileLineFeb(String fileLineFeb) {
        this.fileLineFeb = fileLineFeb;
    }

    @Override
    public LinkedHashSet<String> getDescriptionsForFeb() {
        return descriptionsForFeb;
    }

    @Override
    public void setDescriptionsForFeb(LinkedHashSet<String> descriptionsForFeb) {
        this.descriptionsForFeb = descriptionsForFeb;
    }

    @Override
    public Float getAmountMar() {
        return amountMar;
    }

    @Override
    public void setAmountMar(Float amountMar) {
        this.amountMar = amountMar;
    }

    @Override
    public ArrayList<Float> getArrayOfamountMar() {
        return arrayOfamountMar;
    }

    @Override
    public void setArrayOfamountMar(ArrayList<Float> arrayOfamountMar) {
        this.arrayOfamountMar = arrayOfamountMar;
    }

    @Override
    public String getFileLineMar() {
        return fileLineMar;
    }

    @Override
    public void setFileLineMar(String fileLineMar) {
        this.fileLineMar = fileLineMar;
    }

    @Override
    public LinkedHashSet<String> getDescriptionsForMar() {
        return descriptionsForMar;
    }

    @Override
    public void setDescriptionsForMar(LinkedHashSet<String> descriptionsForMar) {
        this.descriptionsForMar = descriptionsForMar;
    }

    @Override
    public Float getAmountApr() {
        return amountApr;
    }

    @Override
    public void setAmountApr(Float amountApr) {
        this.amountApr = amountApr;
    }

    @Override
    public ArrayList<Float> getArrayOfamountApr() {
        return arrayOfamountApr;
    }

    @Override
    public void setArrayOfamountApr(ArrayList<Float> arrayOfamountApr) {
        this.arrayOfamountApr = arrayOfamountApr;
    }

    @Override
    public String getFileLineApr() {
        return fileLineApr;
    }

    @Override
    public void setFileLineApr(String fileLineApr) {
        this.fileLineApr = fileLineApr;
    }

    @Override
    public LinkedHashSet<String> getDescriptionsForApr() {
        return descriptionsForApr;
    }

    @Override
    public void setDescriptionsForApr(LinkedHashSet<String> descriptionsForApr) {
        this.descriptionsForApr = descriptionsForApr;
    }

    @Override
    public Float getAmountMay() {
        return amountMay;
    }

    @Override
    public void setAmountMay(Float amountMay) {
        this.amountMay = amountMay;
    }

    @Override
    public ArrayList<Float> getArrayOfamountMay() {
        return arrayOfamountMay;
    }

    @Override
    public void setArrayOfamountMay(ArrayList<Float> arrayOfamountMay) {
        this.arrayOfamountMay = arrayOfamountMay;
    }

    @Override
    public String getFileLineMay() {
        return fileLineMay;
    }

    @Override
    public void setFileLineMay(String fileLineMay) {
        this.fileLineMay = fileLineMay;
    }

    @Override
    public LinkedHashSet<String> getDescriptionsForMay() {
        return descriptionsForMay;
    }

    @Override
    public void setDescriptionsForMay(LinkedHashSet<String> descriptionsForMay) {
        this.descriptionsForMay = descriptionsForMay;
    }

    @Override
    public Float getAmountJun() {
        return amountJun;
    }

    @Override
    public void setAmountJun(Float amountJun) {
        this.amountJun = amountJun;
    }

    @Override
    public ArrayList<Float> getArrayOfamountJun() {
        return arrayOfamountJun;
    }

    @Override
    public void setArrayOfamountJun(ArrayList<Float> arrayOfamountJun) {
        this.arrayOfamountJun = arrayOfamountJun;
    }

    @Override
    public String getFileLineJun() {
        return fileLineJun;
    }

    @Override
    public void setFileLineJun(String fileLineJun) {
        this.fileLineJun = fileLineJun;
    }

    @Override
    public LinkedHashSet<String> getDescriptionsForJun() {
        return descriptionsForJun;
    }

    @Override
    public void setDescriptionsForJun(LinkedHashSet<String> descriptionsForJun) {
        this.descriptionsForJun = descriptionsForJun;
    }

    @Override
    public Float getAmountJul() {
        return amountJul;
    }

    @Override
    public void setAmountJul(Float amountJul) {
        this.amountJul = amountJul;
    }

    @Override
    public ArrayList<Float> getArrayOfamountJul() {
        return arrayOfamountJul;
    }

    @Override
    public void setArrayOfamountJul(ArrayList<Float> arrayOfamountJul) {
        this.arrayOfamountJul = arrayOfamountJul;
    }

    @Override
    public String getFileLineJul() {
        return fileLineJul;
    }

    @Override
    public void setFileLineJul(String fileLineJul) {
        this.fileLineJul = fileLineJul;
    }

    @Override
    public LinkedHashSet<String> getDescriptionsForJul() {
        return descriptionsForJul;
    }

    @Override
    public void setDescriptionsForJul(LinkedHashSet<String> descriptionsForJul) {
        this.descriptionsForJul = descriptionsForJul;
    }

    @Override
    public Float getAmountAug() {
        return amountAug;
    }

    @Override
    public void setAmountAug(Float amountAug) {
        this.amountAug = amountAug;
    }

    @Override
    public ArrayList<Float> getArrayOfamountAug() {
        return arrayOfamountAug;
    }

    @Override
    public void setArrayOfamountAug(ArrayList<Float> arrayOfamountAug) {
        this.arrayOfamountAug = arrayOfamountAug;
    }

    @Override
    public String getFileLineAug() {
        return fileLineAug;
    }

    @Override
    public void setFileLineAug(String fileLineAug) {
        this.fileLineAug = fileLineAug;
    }

    @Override
    public LinkedHashSet<String> getDescriptionsForAug() {
        return descriptionsForAug;
    }

    @Override
    public void setDescriptionsForAug(LinkedHashSet<String> descriptionsForAug) {
        this.descriptionsForAug = descriptionsForAug;
    }

    @Override
    public Float getAmountSep() {
        return amountSep;
    }

    @Override
    public void setAmountSep(Float amountSep) {
        this.amountSep = amountSep;
    }
    @Override
    public ArrayList<Float> getArrayOfamountSep() {
        return arrayOfamountSep;
    }

    @Override
    public void setArrayOfamountSep(ArrayList<Float> arrayOfamountSep) {
        this.arrayOfamountSep = arrayOfamountSep;
    }

    @Override
    public String getFileLineSep() {
        return fileLineSep;
    }

    @Override
    public void setFileLineSep(String fileLineSep) {
        this.fileLineSep = fileLineSep;
    }

    @Override
    public LinkedHashSet<String> getDescriptionsForSep() {
        return descriptionsForSep;
    }

    @Override
    public void setDescriptionsForSep(LinkedHashSet<String> descriptionsForSep) {
        this.descriptionsForSep = descriptionsForSep;
    }

    @Override
    public Float getAmountOct() {
        return amountOct;
    }

    @Override
    public void setAmountOct(Float amountOct) {
        this.amountOct = amountOct;
    }

    @Override
    public ArrayList<Float> getArrayOfamountOct() {
        return arrayOfamountOct;
    }

    @Override
    public void setArrayOfamountOct(ArrayList<Float> arrayOfamountOct) {
        this.arrayOfamountOct = arrayOfamountOct;
    }

    @Override
    public String getFileLineOct() {
        return fileLineOct;
    }

    @Override
    public void setFileLineOct(String fileLineOct) {
        this.fileLineOct = fileLineOct;
    }

    @Override
    public LinkedHashSet<String> getDescriptionsForOct() {
        return descriptionsForOct;
    }

    @Override
    public void setDescriptionsForOct(LinkedHashSet<String> descriptionsForOct) {
        this.descriptionsForOct = descriptionsForOct;
    }

    @Override
    public Float getAmountNov() {
        return amountNov;
    }

    @Override
    public void setAmountNov(Float amountNov) {
        this.amountNov = amountNov;
    }

    @Override
    public ArrayList<Float> getArrayOfamountNov() {
        return arrayOfamountNov;
    }

    @Override
    public void setArrayOfamountNov(ArrayList<Float> arrayOfamountNov) {
        this.arrayOfamountNov = arrayOfamountNov;
    }

    @Override
    public String getFileLineNov() {
        return fileLineNov;
    }

    @Override
    public void setFileLineNov(String fileLineNov) {
        this.fileLineNov = fileLineNov;
    }

    @Override
    public LinkedHashSet<String> getDescriptionsForNov() {
        return descriptionsForNov;
    }

    @Override
    public void setDescriptionsForNov(LinkedHashSet<String> descriptionsForNov) {
        this.descriptionsForNov = descriptionsForNov;
    }

    @Override
    public Float getAmountDec() {
        return amountDec;
    }

    @Override
    public void setAmountDec(Float amountDec) {
        this.amountDec = amountDec;
    }

    @Override
    public ArrayList<Float> getArrayOfamountDec() {
        return arrayOfamountDec;
    }

    @Override
    public void setArrayOfamountDec(ArrayList<Float> arrayOfamountDec) {
        this.arrayOfamountDec = arrayOfamountDec;
    }

    @Override
    public String getFileLineDec() {
        return fileLineDec;
    }

    @Override
    public void setFileLineDec(String fileLineDec) {
        this.fileLineDec = fileLineDec;
    }

    @Override
    public LinkedHashSet<String> getDescriptionsForDec() {
        return descriptionsForDec;
    }

    @Override
    public void setDescriptionsForDec(LinkedHashSet<String> descriptionsForDec) {
        this.descriptionsForDec = descriptionsForDec;
    }

    /*public void setDescriptionsForOct15(LinkedHashSet description) { descriptionsForOct15 = description; }

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
    }*/
}
