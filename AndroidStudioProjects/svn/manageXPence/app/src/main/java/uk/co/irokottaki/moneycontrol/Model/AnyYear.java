package uk.co.irokottaki.moneycontrol.Model;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class AnyYear {
    private YearToSet year;
    private String allLinesInFile;


    public AnyYear(YearToSet year) {

        year = new YearToSet(0.0f, 0.0f, 0.0f,0.0f, 0.0f, 0.0f,0.0f ,0.0f, 0.0f ,0.0f ,0.0f ,0.0f );

        this.year = year;
    }

    public AnyYear() {

    }

    public YearToSet getYear() {
        return year;
    }

    public void setAmountAndDescJan(Float amountJan, String year, LinkedHashSet descriptionSet, ArrayList<Float> amounts, String fileLine) {

        this.year.setAmountJan(amountJan);
        this.year.setDescriptionsForJan(descriptionSet);
        this.year.setArrayOfamountJan(amounts);
        this.year.setFileLineJan(fileLine);
    }

    public void setAmountAndDescFeb(Float amountFeb, String year, LinkedHashSet descriptionSet, ArrayList<Float> amounts, String fileLine) {

        this.year.setAmountFeb(amountFeb);
        this.year.setDescriptionsForFeb(descriptionSet);
        this.year.setArrayOfamountFeb(amounts);
        this.year.setFileLineFeb(fileLine);
    }

    public void setAmountAndDescMar(Float amountMar, String year, LinkedHashSet descriptionSet, ArrayList<Float> amounts, String fileLine) {

        this.year.setAmountMar(amountMar);
        this.year.setDescriptionsForMar(descriptionSet);
        this.year.setArrayOfamountMar(amounts);
        this.year.setFileLineMar(fileLine);
    }

    public void setAmountAndDescApr(Float amountApr, String year, LinkedHashSet descriptionSet, ArrayList<Float> amounts, String fileLine) {

        this.year.setAmountApr(amountApr);
        this.year.setDescriptionsForApr(descriptionSet);
        this.year.setArrayOfamountApr(amounts);
        this.year.setFileLineApr(fileLine);
    }

    public void setAmountAndDescMay(Float amountMay, String year, LinkedHashSet descriptionSet, ArrayList<Float> amounts, String fileLine) {

        this.year.setAmountMay(amountMay);
        this.year.setDescriptionsForMay(descriptionSet);
        this.year.setArrayOfamountMay(amounts);
        this.year.setFileLineMay(fileLine);
    }

    public void setAmountAndDescJun(Float amountJun, String year, LinkedHashSet descriptionSet, ArrayList<Float> amounts, String fileLine) {

        this.year.setAmountJun(amountJun);
        this.year.setDescriptionsForJun(descriptionSet);
        this.year.setArrayOfamountJun(amounts);
        this.year.setFileLineJun(fileLine);
    }

    public void setAmountAndDescJul(Float amountJul, String year, LinkedHashSet descriptionSet, ArrayList<Float> amounts, String fileLine) {

        this.year.setAmountJul(amountJul);
        this.year.setDescriptionsForJul(descriptionSet);
        this.year.setArrayOfamountJul(amounts);
        this.year.setFileLineJul(fileLine);
    }

    public void setAmountAndDescAug(Float amountAug, String year, LinkedHashSet descriptionSet, ArrayList<Float> amounts, String fileLine) {

        this.year.setAmountAug(amountAug);
        this.year.setDescriptionsForAug(descriptionSet);
        this.year.setArrayOfamountAug(amounts);
        this.year.setFileLineAug(fileLine);
    }

    public void setAmountAndDescSep(Float amountSep, String year, LinkedHashSet descriptionSet, ArrayList<Float> amounts, String fileLine) {

        this.year.setAmountSep(amountSep);
        this.year.setDescriptionsForSep(descriptionSet);
        this.year.setArrayOfamountSep(amounts);
        this.year.setFileLineSep(fileLine);
    }

    public void setAmountAndDescOct(Float amountOct, String year, LinkedHashSet descriptionSet, ArrayList<Float> amounts, String fileLine) {

        this.year.setAmountOct(amountOct);
        this.year.setDescriptionsForOct(descriptionSet);
        this.year.setArrayOfamountOct(amounts);
        this.year.setFileLineOct(fileLine);
    }

    public void setAmountAndDescNov(Float amountNov, String year, LinkedHashSet descriptionSet, ArrayList<Float> amounts, String fileLine) {

        this.year.setAmountNov(amountNov);
        this.year.setDescriptionsForNov(descriptionSet);
        this.year.setArrayOfamountNov(amounts);
        this.year.setFileLineNov(fileLine);
    }

    public void setAmountAndDescDec(Float amountDec, String year, LinkedHashSet descriptionSet, ArrayList<Float> amounts, String fileLine) {

        this.year.setAmountDec(amountDec);
        this.year.setDescriptionsForDec(descriptionSet);
        this.year.setArrayOfamountDec(amounts);
        this.year.setFileLineDec(fileLine);

    }

    public void setAllLinesInFile(String lines) {

        this.allLinesInFile = lines;
    }

    public String getAllLinesInFile(){

        return allLinesInFile;
    }

}
