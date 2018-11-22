package uk.co.irokottaki.moneycontrol.Model;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class AmountsForYear {
    private AmountsFor2015 year2015;
    private AmountsFor2016 year2016;
    private AmountsFor2017 year2017;
    private AmountsFor2018 year2018;
    private String allLinesInFile;


    public AmountsForYear(AmountsFor2015 year2015, AmountsFor2016 year2016, AmountsFor2017 year2017, AmountsFor2018 year2018) {

        year2015 = new AmountsFor2015(0.0f, 0.0f, 0.0f,0.0f, 0.0f, 0.0f,0.0f ,0.0f, 0.0f ,0.0f ,0.0f ,0.0f );

        year2016 = new AmountsFor2016(0.0f, 0.0f, 0.0f,0.0f, 0.0f, 0.0f,0.0f ,0.0f, 0.0f ,0.0f ,0.0f ,0.0f );

        year2017 = new AmountsFor2017(0.0f, 0.0f, 0.0f,0.0f, 0.0f, 0.0f,0.0f ,0.0f, 0.0f ,0.0f ,0.0f ,0.0f );

        year2018 = new AmountsFor2018(0.0f, 0.0f, 0.0f,0.0f, 0.0f, 0.0f,0.0f ,0.0f, 0.0f ,0.0f ,0.0f ,0.0f );

        this.year2015 = year2015;
        this.year2016 = year2016;
        this.year2017 = year2017;
        this.year2018 = year2018;
    }

    public AmountsForYear() {

    }
    public AmountsFor2017 getYear2017() {
        return year2017;
    }

    public AmountsFor2018 getYear2018() {
        return year2018;
    }

    public AmountsFor2015 getYear2015() {
        return year2015;
    }

    public AmountsFor2016 getYear2016() {
        return year2016;
    }

    public void setAmountAndDescJan(Float amountJan, String year, LinkedHashSet descriptionSet, ArrayList<Float> amounts, String fileLine) {
        switch (year) {
            case "2015":
                year2015.setAmountJan(amountJan);
                break;
            case "2016":
                year2016.setAmountJan(amountJan);
                year2016.setDescriptionsForJan16(descriptionSet);
                year2016.setArrayOfamountJan16(amounts);
                year2016.setFileLineJan16(fileLine);
                break;
            case "2017":
                year2017.setAmountJan(amountJan);
                year2017.setDescriptionsForJan17(descriptionSet);
                year2017.setArrayOfamountJan17(amounts);
                year2017.setFileLineJan17(fileLine);
                break;
            default:
                year2018.setAmountJan(amountJan);
                year2018.setDescriptionsForJan(descriptionSet);
                year2018.setArrayOfamountJan(amounts);
                year2018.setFileLineJan(fileLine);
                break;
        }
    }

    public void setAmountAndDescFeb(Float amountFeb, String year, LinkedHashSet descriptionSet, ArrayList<Float> amounts, String fileLine) {
        switch (year) {
            case "2015":
                year2015.setAmountFeb(amountFeb);
                break;
            case "2016":
                year2016.setAmountFeb(amountFeb);
                year2016.setDescriptionsForFeb16(descriptionSet);
                year2016.setArrayOfamountFeb16(amounts);
                year2016.setFileLineFeb16(fileLine);
                break;
            case "2017":
                year2017.setAmountFeb(amountFeb);
                year2017.setDescriptionsForFeb17(descriptionSet);
                year2017.setArrayOfamountFeb17(amounts);
                year2017.setFileLineFeb17(fileLine);
                break;
            default:
                year2018.setAmountFeb(amountFeb);
                year2018.setDescriptionsForFeb(descriptionSet);
                year2018.setArrayOfamountFeb(amounts);
                year2018.setFileLineFeb(fileLine);
                break;
        }
    }

    public void setAmountAndDescMar(Float amountMar, String year, LinkedHashSet descriptionSet, ArrayList<Float> amounts, String fileLine) {
        switch (year) {
            case "2015":
                year2015.setAmountMar(amountMar);
                break;
            case "2016":
                year2016.setAmountMar(amountMar);
                year2016.setDescriptionsForMar16(descriptionSet);
                year2016.setArrayOfamountMar16(amounts);
                year2016.setFileLineMar16(fileLine);
                break;
            case "2017":
                year2017.setAmountMar(amountMar);
                year2017.setDescriptionsForMar17(descriptionSet);
                year2017.setArrayOfamountMar17(amounts);
                year2017.setFileLineMar17(fileLine);
                break;
            default:
                year2018.setAmountMar(amountMar);
                year2018.setDescriptionsForMar(descriptionSet);
                year2018.setArrayOfamountMar(amounts);
                year2018.setFileLineMar(fileLine);
                break;
        }
    }

    public void setAmountAndDescApr(Float amountApr, String year, LinkedHashSet descriptionSet, ArrayList<Float> amounts, String fileLine) {
        switch (year) {
            case "2015":
                year2015.setAmountApr(amountApr);
                break;
            case "2016":
                year2016.setAmountApr(amountApr);
                year2016.setDescriptionsForApr16(descriptionSet);
                year2016.setArrayOfamountApr16(amounts);
                year2016.setFileLineApr16(fileLine);
                break;
            case "2017":
                year2017.setAmountApr(amountApr);
                year2017.setDescriptionsForApr17(descriptionSet);
                year2017.setArrayOfamountApr17(amounts);
                year2017.setFileLineApr17(fileLine);
                break;
            default:
                year2018.setAmountApr(amountApr);
                year2018.setDescriptionsForApr(descriptionSet);
                year2018.setArrayOfamountApr(amounts);
                year2018.setFileLineApr(fileLine);
                break;
        }
    }

    public void setAmountAndDescMay(Float amountMay, String year, LinkedHashSet descriptionSet, ArrayList<Float> amounts, String fileLine) {
        switch (year) {
            case "2015":
                year2015.setAmountMay(amountMay);
                break;
            case "2016":
                year2016.setAmountMay(amountMay);
                year2016.setDescriptionsForMay16(descriptionSet);
                year2016.setArrayOfamountMay16(amounts);
                year2016.setFileLineMay16(fileLine);
                break;
            case "2017":
                year2017.setAmountMay(amountMay);
                year2017.setDescriptionsForMay17(descriptionSet);
                year2017.setArrayOfamountMay17(amounts);
                year2017.setFileLineMay17(fileLine);
                break;
            default:
                year2018.setAmountMay(amountMay);
                year2018.setDescriptionsForMay(descriptionSet);
                year2018.setArrayOfamountMay(amounts);
                year2018.setFileLineMay(fileLine);
                break;
        }
    }

    public void setAmountAndDescJun(Float amountJun, String year, LinkedHashSet descriptionSet, ArrayList<Float> amounts, String fileLine) {
        switch (year) {
            case "2015":
                year2015.setAmountJun(amountJun);
                break;
            case "2016":
                year2016.setAmountJun(amountJun);
                year2016.setDescriptionsForJun16(descriptionSet);
                year2016.setArrayOfamountJun16(amounts);
                year2016.setFileLineJun16(fileLine);
                break;
            case "2017":
                year2017.setAmountJun(amountJun);
                year2017.setDescriptionsForJun17(descriptionSet);
                year2017.setArrayOfamountJun17(amounts);
                year2017.setFileLineJun17(fileLine);
                break;
            default:
                year2018.setAmountJun(amountJun);
                year2018.setDescriptionsForJun(descriptionSet);
                year2018.setArrayOfamountJun(amounts);
                year2018.setFileLineJun(fileLine);
                break;
        }
    }

    public void setAmountAndDescJul(Float amountJul, String year, LinkedHashSet descriptionSet, ArrayList<Float> amounts, String fileLine) {
        switch (year) {
            case "2015":
                year2015.setAmountJul(amountJul);
                break;
            case "2016":
                year2016.setAmountJul(amountJul);
                year2016.setDescriptionsForJul16(descriptionSet);
                year2016.setArrayOfamountJul16(amounts);
                year2016.setFileLineJul16(fileLine);
                break;
            case "2017":
                year2017.setAmountJul(amountJul);
                year2017.setDescriptionsForJul17(descriptionSet);
                year2017.setArrayOfamountJul17(amounts);
                year2017.setFileLineJul17(fileLine);
                break;
            default:
                year2018.setAmountJul(amountJul);
                year2018.setDescriptionsForJul(descriptionSet);
                year2018.setArrayOfamountJul(amounts);
                year2018.setFileLineJul(fileLine);
                break;
        }
    }

    public void setAmountAndDescAug(Float amountAug, String year, LinkedHashSet descriptionSet, ArrayList<Float> amounts, String fileLine) {
        switch (year) {
            case "2015":
                year2015.setAmountAug(amountAug);
                break;
            case "2016":
                year2016.setAmountAug(amountAug);
                year2016.setDescriptionsForAug16(descriptionSet);
                year2016.setArrayOfamountAug16(amounts);
                year2016.setFileLineAug16(fileLine);
                break;
            case "2017":
                year2017.setAmountAug(amountAug);
                year2017.setDescriptionsForAug17(descriptionSet);
                year2017.setArrayOfamountAug17(amounts);
                year2017.setFileLineAug17(fileLine);
                break;
            default:
                year2018.setAmountAug(amountAug);
                year2018.setDescriptionsForAug(descriptionSet);
                year2018.setArrayOfamountAug(amounts);
                year2018.setFileLineAug(fileLine);
                break;
        }
    }

    public void setAmountAndDescSep(Float amountSep, String year, LinkedHashSet descriptionSet, ArrayList<Float> amounts, String fileLine) {
        switch (year) {
            case "2015":
                year2015.setAmountSep(amountSep);
                break;
            case "2016":
                year2016.setAmountSep(amountSep);
                year2016.setDescriptionsForSep16(descriptionSet);
                year2016.setArrayOfamountSep16(amounts);
                year2016.setFileLineSep16(fileLine);
                break;
            case "2017":
                year2017.setAmountSep(amountSep);
                year2017.setDescriptionsForSep17(descriptionSet);
                year2017.setArrayOfamountSep17(amounts);
                year2017.setFileLineSep17(fileLine);
                break;
            default:
                year2018.setAmountSep(amountSep);
                year2018.setDescriptionsForSep(descriptionSet);
                year2018.setArrayOfamountSep(amounts);
                year2018.setFileLineSep(fileLine);
                break;
        }
    }

    public void setAmountAndDescOct(Float amountOct, String year, LinkedHashSet descriptionSet, ArrayList<Float> amounts, String fileLine) {
        switch (year) {
            case "2015":
                year2015.setAmountOct(amountOct);
                year2015.setDescriptionsForOct15(descriptionSet);
                year2015.setArrayOfamountOct15(amounts);
                year2015.setFileLineOct15(fileLine);
                year2015.setFileLineOct15(fileLine);
                break;
            case "2016":
                year2016.setAmountOct(amountOct);
                year2016.setDescriptionsForOct16(descriptionSet);
                year2016.setArrayOfamountOct16(amounts);
                year2016.setFileLineOct16(fileLine);
                break;
            case "2017":
                year2017.setAmountOct(amountOct);
                year2017.setDescriptionsForOct17(descriptionSet);
                year2017.setArrayOfamountOct17(amounts);
                year2017.setFileLineOct17(fileLine);
                break;
            default:
                year2018.setAmountOct(amountOct);
                year2018.setDescriptionsForOct(descriptionSet);
                year2018.setArrayOfamountOct(amounts);
                year2018.setFileLineOct(fileLine);
                break;
        }
    }

    public void setAmountAndDescNov(Float amountNov, String year, LinkedHashSet descriptionSet, ArrayList<Float> amounts, String fileLine) {
        switch (year) {
            case "2015":
                year2015.setAmountNov(amountNov);
                year2015.setDescriptionsForNov15(descriptionSet);
                year2015.setArrayOfamountNov15(amounts);
                year2015.setFileLineNov15(fileLine);
                year2015.setFileLineNov15(fileLine);
                break;
            case "2016":
                year2016.setAmountNov(amountNov);
                year2016.setDescriptionsForNov16(descriptionSet);
                year2016.setArrayOfamountNov16(amounts);
                year2016.setFileLineNov16(fileLine);
                break;
            case "2017":
                year2017.setAmountNov(amountNov);
                year2017.setDescriptionsForNov17(descriptionSet);
                year2017.setArrayOfamountNov17(amounts);
                year2017.setFileLineNov17(fileLine);
                break;
            default:
                year2018.setAmountNov(amountNov);
                year2018.setDescriptionsForNov(descriptionSet);
                year2018.setArrayOfamountNov(amounts);
                year2018.setFileLineNov(fileLine);
                break;
        }
    }

    public void setAmountAndDescDec(Float amountDec, String year, LinkedHashSet descriptionSet, ArrayList<Float> amounts, String fileLine) {
        switch (year) {
            case "2015":
                year2015.setAmountDec(amountDec);
                year2015.setDescriptionsForDec15(descriptionSet);
                year2015.setArrayOfamountDec15(amounts);
                year2015.setFileLineDec15(fileLine);
                year2015.setFileLineDec15(fileLine);
                break;
            case "2016":
                year2016.setAmountDec(amountDec);
                year2016.setDescriptionsForDec16(descriptionSet);
                year2016.setArrayOfamountDec16(amounts);
                year2016.setFileLineDec16(fileLine);
                break;
            case "2017":
                year2017.setAmountDec(amountDec);
                year2017.setDescriptionsForDec17(descriptionSet);
                year2017.setArrayOfamountDec17(amounts);
                year2017.setFileLineDec17(fileLine);
                break;
            default:
                year2018.setAmountDec(amountDec);
                year2018.setDescriptionsForDec(descriptionSet);
                year2018.setArrayOfamountDec(amounts);
                year2018.setFileLineDec(fileLine);
                break;
        }
    }

    public void setAllLinesInFile(String lines) {

        this.allLinesInFile = lines;
    }

    public String getAllLinesInFile(){

        return allLinesInFile;
    }

}
