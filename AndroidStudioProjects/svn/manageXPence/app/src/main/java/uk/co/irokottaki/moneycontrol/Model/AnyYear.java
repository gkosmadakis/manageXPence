package uk.co.irokottaki.moneycontrol.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class AnyYear implements Serializable {
    private YearToSet year;
    private String allLinesInFile;


    public AnyYear(YearToSet yearToSet) {

        this.year = yearToSet;
    }

    public AnyYear() {

    }

    public YearToSet getYear() {
        return year;
    }

    public void setAmountAndDescJan(Float amountJan, Set descriptionSet, List<Float> amounts, String fileLine) {

        this.year.setAmountJan(amountJan);
        this.year.setDescriptionsForJan(descriptionSet);
        this.year.setArrayOfamountJan(amounts);
        this.year.setFileLineJan(fileLine);
    }

    public void setAmountAndDescFeb(Float amountFeb, Set descriptionSet, List<Float> amounts, String fileLine) {

        this.year.setAmountFeb(amountFeb);
        this.year.setDescriptionsForFeb(descriptionSet);
        this.year.setArrayOfamountFeb(amounts);
        this.year.setFileLineFeb(fileLine);
    }

    public void setAmountAndDescMar(Float amountMar, Set descriptionSet, List<Float> amounts, String fileLine) {

        this.year.setAmountMar(amountMar);
        this.year.setDescriptionsForMar(descriptionSet);
        this.year.setArrayOfamountMar(amounts);
        this.year.setFileLineMar(fileLine);
    }

    public void setAmountAndDescApr(Float amountApr, Set descriptionSet, List<Float> amounts, String fileLine) {

        this.year.setAmountApr(amountApr);
        this.year.setDescriptionsForApr(descriptionSet);
        this.year.setArrayOfamountApr(amounts);
        this.year.setFileLineApr(fileLine);
    }

    public void setAmountAndDescMay(Float amountMay, Set descriptionSet, List<Float> amounts, String fileLine) {

        this.year.setAmountMay(amountMay);
        this.year.setDescriptionsForMay(descriptionSet);
        this.year.setArrayOfamountMay(amounts);
        this.year.setFileLineMay(fileLine);
    }

    public void setAmountAndDescJun(Float amountJun, Set descriptionSet, List<Float> amounts, String fileLine) {

        this.year.setAmountJun(amountJun);
        this.year.setDescriptionsForJun(descriptionSet);
        this.year.setArrayOfamountJun(amounts);
        this.year.setFileLineJun(fileLine);
    }

    public void setAmountAndDescJul(Float amountJul, Set descriptionSet, List<Float> amounts, String fileLine) {

        this.year.setAmountJul(amountJul);
        this.year.setDescriptionsForJul(descriptionSet);
        this.year.setArrayOfamountJul(amounts);
        this.year.setFileLineJul(fileLine);
    }

    public void setAmountAndDescAug(Float amountAug, Set descriptionSet, List<Float> amounts, String fileLine) {

        this.year.setAmountAug(amountAug);
        this.year.setDescriptionsForAug(descriptionSet);
        this.year.setArrayOfamountAug(amounts);
        this.year.setFileLineAug(fileLine);
    }

    public void setAmountAndDescSep(Float amountSep, Set descriptionSet, List<Float> amounts, String fileLine) {

        this.year.setAmountSep(amountSep);
        this.year.setDescriptionsForSep(descriptionSet);
        this.year.setArrayOfamountSep(amounts);
        this.year.setFileLineSep(fileLine);
    }

    public void setAmountAndDescOct(Float amountOct, Set descriptionSet, List<Float> amounts, String fileLine) {

        this.year.setAmountOct(amountOct);
        this.year.setDescriptionsForOct(descriptionSet);
        this.year.setArrayOfamountOct(amounts);
        this.year.setFileLineOct(fileLine);
    }

    public void setAmountAndDescNov(Float amountNov, Set descriptionSet, List<Float> amounts, String fileLine) {

        this.year.setAmountNov(amountNov);
        this.year.setDescriptionsForNov(descriptionSet);
        this.year.setArrayOfamountNov(amounts);
        this.year.setFileLineNov(fileLine);
    }

    public void setAmountAndDescDec(Float amountDec, Set descriptionSet, List<Float> amounts, String fileLine) {

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
