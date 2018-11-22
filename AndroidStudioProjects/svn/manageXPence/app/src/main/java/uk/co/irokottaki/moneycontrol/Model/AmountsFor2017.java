package uk.co.irokottaki.moneycontrol.Model;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class AmountsFor2017 extends AmountsForYear {

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
    private LinkedHashSet<String> descriptionsForJan17, descriptionsForFeb17, descriptionsForMar17, descriptionsForApr17, descriptionsForMay17,
            descriptionsForJun17, descriptionsForJul17, descriptionsForAug17, descriptionsForSep17, descriptionsForOct17, descriptionsForNov17, descriptionsForDec17;
    private ArrayList<Float> arrayOfamountJan17, arrayOfamountFeb17, arrayOfamountMar17, arrayOfamountApr17, arrayOfamountMay17, arrayOfamountJun17,
    arrayOfamountJul17, arrayOfamountAug17, arrayOfamountSep17, arrayOfamountOct17, arrayOfamountNov17, arrayOfamountDec17;
    private String fileLineJan17, fileLineFeb17, fileLineMar17, fileLineApr17, fileLineMay17, fileLineJun17, fileLineJul17, fileLineAug17,
            fileLineSep17, fileLineOct17, fileLineNov17, fileLineDec17;

    public AmountsFor2017(Float jan, Float feb, Float mar, Float apr, Float may, Float jun, Float jul,
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
        descriptionsForJan17 = new LinkedHashSet<>();//store unique descriptions
        descriptionsForFeb17 = new LinkedHashSet<>();
        descriptionsForMar17 = new LinkedHashSet<>();
        descriptionsForApr17 = new LinkedHashSet<>();
        descriptionsForMay17 = new LinkedHashSet<>();//store unique descriptions
        descriptionsForJun17 = new LinkedHashSet<>();
        descriptionsForJul17 = new LinkedHashSet<>();
        descriptionsForAug17 = new LinkedHashSet<>();
        descriptionsForSep17 = new LinkedHashSet<>();//store unique descriptions
        descriptionsForOct17 = new LinkedHashSet<>();
        descriptionsForNov17 = new LinkedHashSet<>();
        descriptionsForDec17 = new LinkedHashSet<>();
        arrayOfamountJan17 = new ArrayList<>();
        arrayOfamountFeb17 = new ArrayList<>();
        arrayOfamountMar17 = new ArrayList<>();
        arrayOfamountApr17 = new ArrayList<>();
        arrayOfamountMay17 = new ArrayList<>();
        arrayOfamountJun17 = new ArrayList<>();
        arrayOfamountJul17 = new ArrayList<>();
        arrayOfamountAug17 = new ArrayList<>();
        arrayOfamountSep17 = new ArrayList<>();
        arrayOfamountOct17 = new ArrayList<>();
        arrayOfamountNov17 = new ArrayList<>();
        arrayOfamountDec17 = new ArrayList<>();
        fileLineJan17 = "";
        fileLineFeb17 = "";
        fileLineMar17 = "";
        fileLineApr17 = "";
        fileLineMay17 = "";
        fileLineJun17 = "";
        fileLineJul17 = "";
        fileLineAug17 = "";
        fileLineSep17 = "";
        fileLineOct17 = "";
        fileLineNov17 = "";
        fileLineDec17 = "";
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

    public Float getAmountSep() {
        return amountSep;
    }

    public Float getAmountOct() {
        return amountOct;
    }

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

    public LinkedHashSet<String> getDescriptionsForJan17() {
        return descriptionsForJan17;
    }

    public LinkedHashSet<String> getDescriptionsForFeb17() {
        return descriptionsForFeb17;
    }

    public LinkedHashSet<String> getDescriptionsForMar17() {
        return descriptionsForMar17;
    }

    public LinkedHashSet<String> getDescriptionsForApr17() {
        return descriptionsForApr17;
    }

    public LinkedHashSet<String> getDescriptionsForMay17() {
        return descriptionsForMay17;
    }

    public LinkedHashSet<String> getDescriptionsForJun17() {
        return descriptionsForJun17;
    }

    public LinkedHashSet<String> getDescriptionsForJul17() {
        return descriptionsForJul17;
    }

    public LinkedHashSet<String> getDescriptionsForAug17() {
        return descriptionsForAug17;
    }

    public LinkedHashSet<String> getDescriptionsForSep17() {
        return descriptionsForSep17;
    }

    public LinkedHashSet<String> getDescriptionsForOct17() {
        return descriptionsForOct17;
    }

    public LinkedHashSet<String> getDescriptionsForNov17() {
        return descriptionsForNov17;
    }

    public LinkedHashSet<String> getDescriptionsForDec17() {
        return descriptionsForDec17;
    }

    public void setDescriptionsForJan17(LinkedHashSet<String> descriptionsForJan17) {
        this.descriptionsForJan17 = descriptionsForJan17;
    }

    public void setDescriptionsForFeb17(LinkedHashSet<String> descriptionsForFeb17) {
        this.descriptionsForFeb17 = descriptionsForFeb17;
    }

    public void setDescriptionsForMar17(LinkedHashSet<String> descriptionsForMar17) {
        this.descriptionsForMar17 = descriptionsForMar17;
    }

    public void setDescriptionsForApr17(LinkedHashSet<String> descriptionsForApr17) {
        this.descriptionsForApr17 = descriptionsForApr17;
    }

    public void setDescriptionsForMay17(LinkedHashSet<String> descriptionsForMay17) {
        this.descriptionsForMay17 = descriptionsForMay17;
    }

    public void setDescriptionsForJun17(LinkedHashSet<String> descriptionsForJun17) {
        this.descriptionsForJun17 = descriptionsForJun17;
    }

    public void setDescriptionsForJul17(LinkedHashSet<String> descriptionsForJul17) {
        this.descriptionsForJul17 = descriptionsForJul17;
    }

    public void setDescriptionsForAug17(LinkedHashSet<String> descriptionsForAug17) {
        this.descriptionsForAug17 = descriptionsForAug17;
    }

    public void setDescriptionsForSep17(LinkedHashSet<String> descriptionsForSep17) {
        this.descriptionsForSep17 = descriptionsForSep17;
    }

    public void setDescriptionsForOct17(LinkedHashSet<String> descriptionsForOct17) {
        this.descriptionsForOct17 = descriptionsForOct17;
    }

    public void setDescriptionsForNov17(LinkedHashSet<String> descriptionsForNov17) {
        this.descriptionsForNov17 = descriptionsForNov17;
    }

    public void setDescriptionsForDec17(LinkedHashSet<String> descriptionsForDec17) {
        this.descriptionsForDec17 = descriptionsForDec17;
    }

    public ArrayList<Float> getArrayOfamountJan17() {
        return arrayOfamountJan17;
    }

    public ArrayList<Float> getArrayOfamountFeb17() {
        return arrayOfamountFeb17;
    }

    public ArrayList<Float> getArrayOfamountMar17() {
        return arrayOfamountMar17;
    }

    public ArrayList<Float> getArrayOfamountApr17() {
        return arrayOfamountApr17;
    }

    public ArrayList<Float> getArrayOfamountMay17() {
        return arrayOfamountMay17;
    }

    public ArrayList<Float> getArrayOfamountJun17() {
        return arrayOfamountJun17;
    }

    public ArrayList<Float> getArrayOfamountJul17() {
        return arrayOfamountJul17;
    }

    public ArrayList<Float> getArrayOfamountAug17() {
        return arrayOfamountAug17;
    }

    public ArrayList<Float> getArrayOfamountSep17() {
        return arrayOfamountSep17;
    }

    public ArrayList<Float> getArrayOfamountOct17() {
        return arrayOfamountOct17;
    }

    public ArrayList<Float> getArrayOfamountNov17() {
        return arrayOfamountNov17;
    }

    public ArrayList<Float> getArrayOfamountDec17() {
        return arrayOfamountDec17;
    }

    public void setArrayOfamountJan17(ArrayList<Float> arrayOfamountJan17) {
        this.arrayOfamountJan17 = arrayOfamountJan17;
    }

    public void setArrayOfamountFeb17(ArrayList<Float> arrayOfamountFeb17) {
        this.arrayOfamountFeb17 = arrayOfamountFeb17;
    }

    public void setArrayOfamountMar17(ArrayList<Float> arrayOfamountMar17) {
        this.arrayOfamountMar17 = arrayOfamountMar17;
    }

    public void setArrayOfamountApr17(ArrayList<Float> arrayOfamountApr17) {
        this.arrayOfamountApr17 = arrayOfamountApr17;
    }

    public void setArrayOfamountMay17(ArrayList<Float> arrayOfamountMay17) {
        this.arrayOfamountMay17 = arrayOfamountMay17;
    }

    public void setArrayOfamountJun17(ArrayList<Float> arrayOfamountJun17) {
        this.arrayOfamountJun17 = arrayOfamountJun17;
    }

    public void setArrayOfamountJul17(ArrayList<Float> arrayOfamountJul17) {
        this.arrayOfamountJul17 = arrayOfamountJul17;
    }

    public void setArrayOfamountAug17(ArrayList<Float> arrayOfamountAug17) {
        this.arrayOfamountAug17 = arrayOfamountAug17;
    }

    public void setArrayOfamountSep17(ArrayList<Float> arrayOfamountSep17) {
        this.arrayOfamountSep17 = arrayOfamountSep17;
    }

    public void setArrayOfamountOct17(ArrayList<Float> arrayOfamountOct17) {
        this.arrayOfamountOct17 = arrayOfamountOct17;
    }

    public void setArrayOfamountNov17(ArrayList<Float> arrayOfamountNov17) {
        this.arrayOfamountNov17 = arrayOfamountNov17;
    }

    public void setArrayOfamountDec17(ArrayList<Float> arrayOfamountDec17) {
        this.arrayOfamountDec17 = arrayOfamountDec17;
    }

    public String getFileLineJan17() {
        return fileLineJan17;
    }

    public String getFileLineFeb17() {
        return fileLineFeb17;
    }

    public String getFileLineMar17() {
        return fileLineMar17;
    }

    public String getFileLineApr17() {
        return fileLineApr17;
    }

    public String getFileLineMay17() {
        return fileLineMay17;
    }

    public String getFileLineJun17() {
        return fileLineJun17;
    }

    public String getFileLineJul17() {
        return fileLineJul17;
    }

    public String getFileLineAug17() {
        return fileLineAug17;
    }

    public String getFileLineSep17() {
        return fileLineSep17;
    }

    public String getFileLineOct17() {
        return fileLineOct17;
    }

    public String getFileLineNov17() {
        return fileLineNov17;
    }

    public String getFileLineDec17() {
        return fileLineDec17;
    }

    public void setFileLineJan17(String fileLineJan17) {
        this.fileLineJan17 = fileLineJan17;
    }

    public void setFileLineFeb17(String fileLineFeb17) {
        this.fileLineFeb17 = fileLineFeb17;
    }

    public void setFileLineMar17(String fileLineMar17) {
        this.fileLineMar17 = fileLineMar17;
    }

    public void setFileLineApr17(String fileLineApr17) {
        this.fileLineApr17 = fileLineApr17;
    }

    public void setFileLineMay17(String fileLineMay17) {
        this.fileLineMay17 = fileLineMay17;
    }

    public void setFileLineJun17(String fileLineJun17) {
        this.fileLineJun17 = fileLineJun17;
    }

    public void setFileLineJul17(String fileLineJul17) {
        this.fileLineJul17 = fileLineJul17;
    }

    public void setFileLineAug17(String fileLineAug17) {
        this.fileLineAug17 = fileLineAug17;
    }

    public void setFileLineSep17(String fileLineSep17) {
        this.fileLineSep17 = fileLineSep17;
    }

    public void setFileLineOct17(String fileLineOct17) {
        this.fileLineOct17 = fileLineOct17;
    }

    public void setFileLineNov17(String fileLineNov17) {
        this.fileLineNov17 = fileLineNov17;
    }

    public void setFileLineDec17(String fileLineDec17) {
        this.fileLineDec17 = fileLineDec17;
    }
}
