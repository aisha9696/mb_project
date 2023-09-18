package kz.mb.project.mb_project.entity;

import java.io.Serializable;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum UserRole implements Serializable {


    Admin("admin","Администратор", "Aдминистратор"),
    Cacher("casher", "Кассир", "Кассир"),
    Owner("owner", "Владелец","Бизнес иесі" ),
    Stockman("stockman", "Кладовщик","Қоймашы");

    private final String code;
    private final String valueRU;
    private final String valueKZ;

    UserRole(String code, String valueRU, String valueKZ) {
        this.code = code;
        this.valueRU = valueRU;
        this.valueKZ = valueKZ;
    }



}

