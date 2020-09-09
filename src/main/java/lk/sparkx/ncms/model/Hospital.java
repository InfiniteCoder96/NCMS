package lk.sparkx.ncms.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter
public class Hospital {
    private int id;
    private String name;
    private String district;
    private int locationX;
    private int locationY;
    private Date buildDate;
}
