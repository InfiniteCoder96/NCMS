package lk.sparkx.ncms.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter
public class Hospital {
    private String id;
    private String name;
    private String district;
    private double locationX;
    private double locationY;
    private Date buildDate;
}
