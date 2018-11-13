package uk.co.irokottaki.moneycontrol.Model;

public class AmountsForYear {
    private AmountsFor2015 year2015;
    private AmountsFor2016 year2016;
    private AmountsFor2017 year2017;
    private AmountsFor2018 year2018;


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

    public void setAmountJan(Float amountJan, String year) {
        switch (year) {
            case "2015":
                year2015.setAmountJan(amountJan);
                break;
            case "2016":
                year2016.setAmountJan(amountJan);
                break;
            case "2017":
                year2017.setAmountJan(amountJan);
                break;
            default:
                year2018.setAmountJan(amountJan);
                break;
        }
    }

    public void setAmountFeb(Float amountFeb, String year) {
        switch (year) {
            case "2015":
                year2015.setAmountFeb(amountFeb);
                break;
            case "2016":
                year2016.setAmountFeb(amountFeb);
                break;
            case "2017":
                year2017.setAmountFeb(amountFeb);
                break;
            default:
                year2018.setAmountFeb(amountFeb);
                break;
        }
    }

    public void setAmountMar(Float amountMar,String year) {
        switch (year) {
            case "2015":
                year2015.setAmountMar(amountMar);
                break;
            case "2016":
                year2016.setAmountMar(amountMar);
                break;
            case "2017":
                year2017.setAmountMar(amountMar);
                break;
            default:
                year2018.setAmountMar(amountMar);
                break;
        }
    }

    public void setAmountApr(Float amountApr, String year) {
        switch (year) {
            case "2015":
                year2015.setAmountApr(amountApr);
                break;
            case "2016":
                year2016.setAmountApr(amountApr);
                break;
            case "2017":
                year2017.setAmountApr(amountApr);
                break;
            default:
                year2018.setAmountApr(amountApr);
                break;
        }
    }

    public void setAmountMay(Float amountMay, String year) {
        switch (year) {
            case "2015":
                year2015.setAmountMay(amountMay);
                break;
            case "2016":
                year2016.setAmountMay(amountMay);
                break;
            case "2017":
                year2017.setAmountMay(amountMay);
                break;
            default:
                year2018.setAmountMay(amountMay);
                break;
        }
    }

    public void setAmountJun(Float amountJun, String year) {
        switch (year) {
            case "2015":
                year2015.setAmountJun(amountJun);
                break;
            case "2016":
                year2016.setAmountJun(amountJun);
                break;
            case "2017":
                year2017.setAmountJun(amountJun);
                break;
            default:
                year2018.setAmountJun(amountJun);
                break;
        }
    }

    public void setAmountJul(Float amountJul, String year) {
        switch (year) {
            case "2015":
                year2015.setAmountJul(amountJul);
                break;
            case "2016":
                year2016.setAmountJul(amountJul);
                break;
            case "2017":
                year2017.setAmountJul(amountJul);
                break;
            default:
                year2018.setAmountJul(amountJul);
                break;
        }
    }

    public void setAmountAug(Float amountAug, String year) {
        switch (year) {
            case "2015":
                year2015.setAmountAug(amountAug);
                break;
            case "2016":
                year2016.setAmountAug(amountAug);
                break;
            case "2017":
                year2017.setAmountAug(amountAug);
                break;
            default:
                year2018.setAmountAug(amountAug);
                break;
        }
    }

    public void setAmountSep(Float amountSep, String year) {
        switch (year) {
            case "2015":
                year2015.setAmountSep(amountSep);
                break;
            case "2016":
                year2016.setAmountSep(amountSep);
                break;
            case "2017":
                year2017.setAmountSep(amountSep);
                break;
            default:
                year2018.setAmountSep(amountSep);
                break;
        }
    }

    public void setAmountOct(Float amountOct, String year) {
        switch (year) {
            case "2015":
                year2015.setAmountOct(amountOct);
                break;
            case "2016":
                year2016.setAmountOct(amountOct);
                break;
            case "2017":
                year2017.setAmountOct(amountOct);
                break;
            default:
                year2018.setAmountOct(amountOct);
                break;
        }
    }

    public void setAmountNov(Float amountNov, String year) {
        switch (year) {
            case "2015":
                year2015.setAmountNov(amountNov);
                break;
            case "2016":
                year2016.setAmountNov(amountNov);
                break;
            case "2017":
                year2017.setAmountNov(amountNov);
                break;
            default:
                year2018.setAmountNov(amountNov);
                break;
        }
    }

    public void setAmountDec(Float amountDec, String year) {
        switch (year) {
            case "2015":
                year2015.setAmountDec(amountDec);
                break;
            case "2016":
                year2016.setAmountDec(amountDec);
                break;
            case "2017":
                year2017.setAmountDec(amountDec);
                break;
            default:
                year2018.setAmountDec(amountDec);
                break;
        }
    }

}
