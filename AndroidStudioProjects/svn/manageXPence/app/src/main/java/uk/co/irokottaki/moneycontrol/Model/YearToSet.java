package uk.co.irokottaki.moneycontrol.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import uk.co.irokottaki.moneycontrol.YearInterface;

public class YearToSet extends AnyYear implements YearInterface {

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
    private Set<String> descriptionsForJan;
    private Set<String> descriptionsForFeb;
    private Set<String> descriptionsForMar;
    private Set<String> descriptionsForApr;
    private Set<String> descriptionsForMay;
    private Set<String> descriptionsForJun;
    private Set<String> descriptionsForJul;
    private Set<String> descriptionsForAug;
    private Set<String> descriptionsForSep;
    private Set<String> descriptionsForOct;
    private Set<String> descriptionsForNov;
    private Set<String> descriptionsForDec;
    private List<Float> arrayOfamountJan;
    private List<Float> arrayOfamountFeb;
    private List<Float> arrayOfamountMar;
    private List<Float> arrayOfamountApr;
    private List<Float> arrayOfamountMay;
    private List<Float> arrayOfamountJun;
    private List<Float> arrayOfamountJul;
    private List<Float> arrayOfamountAug;
    private List<Float> arrayOfamountSep;
    private List<Float> arrayOfamountOct;
    private List<Float> arrayOfamountNov;
    private List<Float> arrayOfamountDec;
    private String fileLineJan;
    private String fileLineFeb;
    private String fileLineMar;
    private String fileLineApr;
    private String fileLineMay;
    private String fileLineJun;
    private String fileLineJul;
    private String fileLineAug;
    private String fileLineSep;
    private String fileLineOct;
    private String fileLineNov;
    private String fileLineDec;

    public YearToSet(Float jan, Float feb, Float mar, Float apr, Float may, Float jun, Float jul,
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
        arrayOfamountJan = new ArrayList();
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


    @Override
    public Float getAmountJan() {
        return amountJan;
    }

    @Override
    public void setAmountJan(Float amountJan) {
        this.amountJan = amountJan;
    }

    @Override
    public List<Float> getArrayOfamountJan() {
        return arrayOfamountJan;
    }

    @Override
    public void setArrayOfamountJan(List<Float> arrayOfamountJan) {
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
    public Set<String> getDescriptionsForJan() {
        return descriptionsForJan;
    }

    @Override
    public void setDescriptionsForJan(Set<String> descriptionsForJan) {
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
    public List<Float> getArrayOfamountFeb() {
        return arrayOfamountFeb;
    }

    @Override
    public void setArrayOfamountFeb(List<Float> arrayOfamountFeb) {
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
    public Set<String> getDescriptionsForFeb() {
        return descriptionsForFeb;
    }

    @Override
    public void setDescriptionsForFeb(Set<String> descriptionsForFeb) {
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
    public List<Float> getArrayOfamountMar() {
        return arrayOfamountMar;
    }

    @Override
    public void setArrayOfamountMar(List<Float> arrayOfamountMar) {
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
    public Set<String> getDescriptionsForMar() {
        return descriptionsForMar;
    }

    @Override
    public void setDescriptionsForMar(Set<String> descriptionsForMar) {
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
    public List<Float> getArrayOfamountApr() {
        return arrayOfamountApr;
    }

    @Override
    public void setArrayOfamountApr(List<Float> arrayOfamountApr) {
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
    public Set<String> getDescriptionsForApr() {
        return descriptionsForApr;
    }

    @Override
    public void setDescriptionsForApr(Set<String> descriptionsForApr) {
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
    public List<Float> getArrayOfamountMay() {
        return arrayOfamountMay;
    }

    @Override
    public void setArrayOfamountMay(List<Float> arrayOfamountMay) {
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
    public Set<String> getDescriptionsForMay() {
        return descriptionsForMay;
    }

    @Override
    public void setDescriptionsForMay(Set<String> descriptionsForMay) {
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
    public List<Float> getArrayOfamountJun() {
        return arrayOfamountJun;
    }

    @Override
    public void setArrayOfamountJun(List<Float> arrayOfamountJun) {
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
    public Set<String> getDescriptionsForJun() {
        return descriptionsForJun;
    }

    @Override
    public void setDescriptionsForJun(Set<String> descriptionsForJun) {
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
    public List<Float> getArrayOfamountJul() {
        return arrayOfamountJul;
    }

    @Override
    public void setArrayOfamountJul(List<Float> arrayOfamountJul) {
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
    public Set<String> getDescriptionsForJul() {
        return descriptionsForJul;
    }

    @Override
    public void setDescriptionsForJul(Set<String> descriptionsForJul) {
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
    public List<Float> getArrayOfamountAug() {
        return arrayOfamountAug;
    }

    @Override
    public void setArrayOfamountAug(List<Float> arrayOfamountAug) {
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
    public Set<String> getDescriptionsForAug() {
        return descriptionsForAug;
    }

    @Override
    public void setDescriptionsForAug(Set<String> descriptionsForAug) {
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
    public List<Float> getArrayOfamountSep() {
        return arrayOfamountSep;
    }

    @Override
    public void setArrayOfamountSep(List<Float> arrayOfamountSep) {
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
    public Set<String> getDescriptionsForSep() {
        return descriptionsForSep;
    }

    @Override
    public void setDescriptionsForSep(Set<String> descriptionsForSep) {
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
    public List<Float> getArrayOfamountOct() {
        return arrayOfamountOct;
    }

    @Override
    public void setArrayOfamountOct(List<Float> arrayOfamountOct) {
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
    public Set<String> getDescriptionsForOct() {
        return descriptionsForOct;
    }

    @Override
    public void setDescriptionsForOct(Set<String> descriptionsForOct) {
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
    public List<Float> getArrayOfamountNov() {
        return arrayOfamountNov;
    }

    @Override
    public void setArrayOfamountNov(List<Float> arrayOfamountNov) {
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
    public Set<String> getDescriptionsForNov() {
        return descriptionsForNov;
    }

    @Override
    public void setDescriptionsForNov(Set<String> descriptionsForNov) {
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
    public List<Float> getArrayOfamountDec() {
        return arrayOfamountDec;
    }

    @Override
    public void setArrayOfamountDec(List<Float> arrayOfamountDec) {
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
    public Set<String> getDescriptionsForDec() {
        return descriptionsForDec;
    }

    @Override
    public void setDescriptionsForDec(Set<String> descriptionsForDec) {
        this.descriptionsForDec = descriptionsForDec;
    }


}
