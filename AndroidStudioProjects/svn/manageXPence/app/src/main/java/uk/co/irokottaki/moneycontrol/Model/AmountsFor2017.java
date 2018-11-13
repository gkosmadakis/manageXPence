package uk.co.irokottaki.moneycontrol.Model;

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
}
