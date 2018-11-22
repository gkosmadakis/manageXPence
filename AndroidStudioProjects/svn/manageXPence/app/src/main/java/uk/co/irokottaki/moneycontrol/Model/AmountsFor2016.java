package uk.co.irokottaki.moneycontrol.Model;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class AmountsFor2016 extends AmountsForYear{

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
    private LinkedHashSet<String> descriptionsForJan16, descriptionsForFeb16, descriptionsForMar16, descriptionsForApr16, descriptionsForMay16,
            descriptionsForJun16, descriptionsForJul16, descriptionsForAug16, descriptionsForSep16, descriptionsForOct16, descriptionsForNov16, descriptionsForDec16;
    private ArrayList<Float> arrayOfamountJan16, arrayOfamountFeb16, arrayOfamountMar16, arrayOfamountApr16, arrayOfamountMay16, arrayOfamountJun16,
    arrayOfamountJul16, arrayOfamountAug16, arrayOfamountSep16, arrayOfamountOct16, arrayOfamountNov16, arrayOfamountDec16;
    private String fileLineJan16, fileLineFeb16, fileLineMar16, fileLineApr16, fileLineMay16, fileLineJun16, fileLineJul16, fileLineAug16, fileLineSep16,
            fileLineOct16, fileLineNov16, fileLineDec16;

    public AmountsFor2016(Float jan, Float feb, Float mar, Float apr, Float may, Float jun, Float jul,
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
        descriptionsForJan16 = new LinkedHashSet<>();//store unique descriptions
        descriptionsForFeb16 = new LinkedHashSet<>();
        descriptionsForMar16 = new LinkedHashSet<>();
        descriptionsForApr16 = new LinkedHashSet<>();
        descriptionsForMay16 = new LinkedHashSet<>();//store unique descriptions
        descriptionsForJun16 = new LinkedHashSet<>();
        descriptionsForJul16 = new LinkedHashSet<>();
        descriptionsForAug16 = new LinkedHashSet<>();
        descriptionsForSep16 = new LinkedHashSet<>();//store unique descriptions
        descriptionsForOct16 = new LinkedHashSet<>();
        descriptionsForNov16 = new LinkedHashSet<>();
        descriptionsForDec16 = new LinkedHashSet<>();
        arrayOfamountJan16 = new ArrayList<>();
        arrayOfamountFeb16 = new ArrayList<>();
        arrayOfamountMar16 = new ArrayList<>();
        arrayOfamountApr16 = new ArrayList<>();
        arrayOfamountMay16 = new ArrayList<>();
        arrayOfamountJun16 = new ArrayList<>();
        arrayOfamountJul16 = new ArrayList<>();
        arrayOfamountAug16 = new ArrayList<>();
        arrayOfamountSep16 = new ArrayList<>();
        arrayOfamountOct16 = new ArrayList<>();
        arrayOfamountNov16 = new ArrayList<>();
        arrayOfamountDec16 = new ArrayList<>();
        fileLineJan16 = "";
        fileLineFeb16 = "";
        fileLineMar16 = "";
        fileLineApr16 = "";
        fileLineMay16 = "";
        fileLineJun16 = "";
        fileLineJul16 = "";
        fileLineAug16 = "";
        fileLineSep16 = "";
        fileLineOct16 = "";
        fileLineNov16 = "";
        fileLineDec16 = "";
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

    public void setAmountOct(Float amountOct) {
        this.amountOct = amountOct;
    }

    public void setAmountNov(Float amountNov) {
        this.amountNov = amountNov;
    }

    public void setAmountDec(Float amountDec) {
        this.amountDec = amountDec;
    }

    public LinkedHashSet<String> getDescriptionsForJan16() {
        return descriptionsForJan16;
    }

    public LinkedHashSet<String> getDescriptionsForFeb16() {
        return descriptionsForFeb16;
    }

    public LinkedHashSet<String> getDescriptionsForMar16() {
        return descriptionsForMar16;
    }

    public LinkedHashSet<String> getDescriptionsForApr16() {
        return descriptionsForApr16;
    }

    public LinkedHashSet<String> getDescriptionsForMay16() {
        return descriptionsForMay16;
    }

    public LinkedHashSet<String> getDescriptionsForJun16() {
        return descriptionsForJun16;
    }

    public LinkedHashSet<String> getDescriptionsForJul16() {
        return descriptionsForJul16;
    }

    public LinkedHashSet<String> getDescriptionsForAug16() {
        return descriptionsForAug16;
    }

    public LinkedHashSet<String> getDescriptionsForSep16() {
        return descriptionsForSep16;
    }

    public LinkedHashSet<String> getDescriptionsForOct16() {
        return descriptionsForOct16;
    }

    public LinkedHashSet<String> getDescriptionsForNov16() {
        return descriptionsForNov16;
    }

    public LinkedHashSet<String> getDescriptionsForDec16() {
        return descriptionsForDec16;
    }

    public void setDescriptionsForJan16(LinkedHashSet<String> descriptionsForJan16) {
        this.descriptionsForJan16 = descriptionsForJan16;
    }

    public void setDescriptionsForFeb16(LinkedHashSet<String> descriptionsForFeb16) {
        this.descriptionsForFeb16 = descriptionsForFeb16;
    }

    public void setDescriptionsForMar16(LinkedHashSet<String> descriptionsForMar16) {
        this.descriptionsForMar16 = descriptionsForMar16;
    }

    public void setDescriptionsForApr16(LinkedHashSet<String> descriptionsForApr16) {
        this.descriptionsForApr16 = descriptionsForApr16;
    }

    public void setDescriptionsForMay16(LinkedHashSet<String> descriptionsForMay16) {
        this.descriptionsForMay16 = descriptionsForMay16;
    }

    public void setDescriptionsForJun16(LinkedHashSet<String> descriptionsForJun16) {
        this.descriptionsForJun16 = descriptionsForJun16;
    }

    public void setDescriptionsForJul16(LinkedHashSet<String> descriptionsForJul16) {
        this.descriptionsForJul16 = descriptionsForJul16;
    }

    public void setDescriptionsForAug16(LinkedHashSet<String> descriptionsForAug16) {
        this.descriptionsForAug16 = descriptionsForAug16;
    }

    public void setDescriptionsForSep16(LinkedHashSet<String> descriptionsForSep16) {
        this.descriptionsForSep16 = descriptionsForSep16;
    }

    public void setDescriptionsForOct16(LinkedHashSet<String> descriptionsForOct16) {
        this.descriptionsForOct16 = descriptionsForOct16;
    }

    public void setDescriptionsForNov16(LinkedHashSet<String> descriptionsForNov16) {
        this.descriptionsForNov16 = descriptionsForNov16;
    }

    public void setDescriptionsForDec16(LinkedHashSet<String> descriptionsForDec16) {
        this.descriptionsForDec16 = descriptionsForDec16;
    }

    public ArrayList<Float> getArrayOfamountJan16() {
        return arrayOfamountJan16;
    }

    public ArrayList<Float> getArrayOfamountFeb16() {
        return arrayOfamountFeb16;
    }

    public ArrayList<Float> getArrayOfamountMar16() {
        return arrayOfamountMar16;
    }

    public ArrayList<Float> getArrayOfamountApr16() {
        return arrayOfamountApr16;
    }

    public ArrayList<Float> getArrayOfamountMay16() {
        return arrayOfamountMay16;
    }

    public ArrayList<Float> getArrayOfamountJun16() {
        return arrayOfamountJun16;
    }

    public ArrayList<Float> getArrayOfamountJul16() {
        return arrayOfamountJul16;
    }

    public ArrayList<Float> getArrayOfamountAug16() {
        return arrayOfamountAug16;
    }

    public ArrayList<Float> getArrayOfamountSep16() {
        return arrayOfamountSep16;
    }

    public ArrayList<Float> getArrayOfamountOct16() {
        return arrayOfamountOct16;
    }

    public ArrayList<Float> getArrayOfamountNov16() {
        return arrayOfamountNov16;
    }

    public ArrayList<Float> getArrayOfamountDec16() {
        return arrayOfamountDec16;
    }

    public void setArrayOfamountJan16(ArrayList<Float> arrayOfamountJan16) {
        this.arrayOfamountJan16 = arrayOfamountJan16;
    }

    public void setArrayOfamountFeb16(ArrayList<Float> arrayOfamountFeb16) {
        this.arrayOfamountFeb16 = arrayOfamountFeb16;
    }

    public void setArrayOfamountMar16(ArrayList<Float> arrayOfamountMar16) {
        this.arrayOfamountMar16 = arrayOfamountMar16;
    }

    public void setArrayOfamountApr16(ArrayList<Float> arrayOfamountApr16) {
        this.arrayOfamountApr16 = arrayOfamountApr16;
    }

    public void setArrayOfamountMay16(ArrayList<Float> arrayOfamountMay16) {
        this.arrayOfamountMay16 = arrayOfamountMay16;
    }

    public void setArrayOfamountJun16(ArrayList<Float> arrayOfamountJun16) {
        this.arrayOfamountJun16 = arrayOfamountJun16;
    }

    public void setArrayOfamountJul16(ArrayList<Float> arrayOfamountJul16) {
        this.arrayOfamountJul16 = arrayOfamountJul16;
    }

    public void setArrayOfamountAug16(ArrayList<Float> arrayOfamountAug16) {
        this.arrayOfamountAug16 = arrayOfamountAug16;
    }

    public void setArrayOfamountSep16(ArrayList<Float> arrayOfamountSep16) {
        this.arrayOfamountSep16 = arrayOfamountSep16;
    }

    public void setArrayOfamountOct16(ArrayList<Float> arrayOfamountOct16) {
        this.arrayOfamountOct16 = arrayOfamountOct16;
    }

    public void setArrayOfamountNov16(ArrayList<Float> arrayOfamountNov16) {
        this.arrayOfamountNov16 = arrayOfamountNov16;
    }

    public void setArrayOfamountDec16(ArrayList<Float> arrayOfamountDec16) {
        this.arrayOfamountDec16 = arrayOfamountDec16;
    }

    public String getFileLineJan16() {
        return fileLineJan16;
    }

    public String getFileLineFeb16() {
        return fileLineFeb16;
    }

    public String getFileLineMar16() {
        return fileLineMar16;
    }

    public String getFileLineApr16() {
        return fileLineApr16;
    }

    public String getFileLineMay16() {
        return fileLineMay16;
    }

    public String getFileLineJun16() {
        return fileLineJun16;
    }

    public String getFileLineJul16() {
        return fileLineJul16;
    }

    public String getFileLineAug16() {
        return fileLineAug16;
    }

    public String getFileLineSep16() {
        return fileLineSep16;
    }

    public String getFileLineOct16() {
        return fileLineOct16;
    }

    public String getFileLineNov16() {
        return fileLineNov16;
    }

    public String getFileLineDec16() {
        return fileLineDec16;
    }

    public void setFileLineJan16(String fileLineJan16) {
        this.fileLineJan16 = fileLineJan16;
    }

    public void setFileLineFeb16(String fileLineFeb16) {
        this.fileLineFeb16 = fileLineFeb16;
    }

    public void setFileLineMar16(String fileLineMar16) {
        this.fileLineMar16 = fileLineMar16;
    }

    public void setFileLineApr16(String fileLineApr16) {
        this.fileLineApr16 = fileLineApr16;
    }

    public void setFileLineMay16(String fileLineMay16) {
        this.fileLineMay16 = fileLineMay16;
    }

    public void setFileLineJun16(String fileLineJun16) {
        this.fileLineJun16 = fileLineJun16;
    }

    public void setFileLineJul16(String fileLineJul16) {
        this.fileLineJul16 = fileLineJul16;
    }

    public void setFileLineAug16(String fileLineAug16) {
        this.fileLineAug16 = fileLineAug16;
    }

    public void setFileLineSep16(String fileLineSep16) {
        this.fileLineSep16 = fileLineSep16;
    }

    public void setFileLineOct16(String fileLineOct16) {
        this.fileLineOct16 = fileLineOct16;
    }

    public void setFileLineNov16(String fileLineNov16) {
        this.fileLineNov16 = fileLineNov16;
    }

    public void setFileLineDec16(String fileLineDec16) {
        this.fileLineDec16 = fileLineDec16;
    }
}
