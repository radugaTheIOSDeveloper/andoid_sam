package ru.pomoysam.itstack;

public class PaymentItem {

    String confUrl;
    String message;
    String card_text;
    String payment_id;
    Integer errorRequest;
    Integer stutusCanceldCard;
    String statusCodePayment;


    public String getStatusCodePayment() {
        return statusCodePayment;
    }

    public void setStatusCodePayment(String statusCodePayment) {
        this.statusCodePayment = statusCodePayment;
    }

    public Integer getStutusCanceldCard() {
        return stutusCanceldCard;
    }

    public void setStutusCanceldCard(Integer stutusCanceldCard) {
        this.stutusCanceldCard = stutusCanceldCard;
    }

    public String getCard_text() {
        return card_text;
    }

    public void setCard_text(String card_text) {
        this.card_text = card_text;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public Integer getErrorRequest() {
        return errorRequest;
    }

    public void setErrorRequest(Integer errorRequest) {
        this.errorRequest = errorRequest;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getConfUrl() {
        return confUrl;
    }

    public void setConfUrl(String confUrl) {
        this.confUrl = confUrl;
    }
}
