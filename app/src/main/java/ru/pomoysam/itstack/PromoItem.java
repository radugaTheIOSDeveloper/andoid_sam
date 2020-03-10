package ru.pomoysam.itstack;

public class PromoItem {

    Integer percent;
    Integer typesPromo;
    String messages;
    Integer percentBy;

    public Integer getPercentBy() {
        return percentBy;
    }

    public void setPercentBy(Integer percentBy) {
        this.percentBy = percentBy;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public Integer getTypesPromo() {
        return typesPromo;
    }

    public void setTypesPromo(Integer typesPromo) {
        this.typesPromo = typesPromo;
    }
}
