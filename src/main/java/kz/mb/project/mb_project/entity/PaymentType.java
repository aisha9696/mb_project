package kz.mb.project.mb_project.entity;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PaymentType implements Serializable {
    Kaspi("Каспи перевод", "Каспи аударма"),
    KaspiQR("Каспи QR", "Каспи QR"),
    KaspiRed("Каспи RED", "Каспи RED"),
    Terminal("Банковский терминал","Банк терминалы" ),
    Cash("Наличкой","Қолма-қол ақша"),
    Dept("В долг","Қарызға");



    private final String valueRU;
    private final String valueKZ;

    PaymentType(String valueRU, String valueKZ) {
        this.valueRU = valueRU;
        this.valueKZ = valueKZ;
    }

    public String getValueRU() {
        return valueRU;
    }

    public String getValueKZ() {
        return valueKZ;
    }
}
