package lk.sparkx.ncms.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;


@Getter @Setter
public class Patient {
    private String id;
    private String firstName;
    private String lastName;
    private String district;
    private int locationX;
    private int locationY;
    private SeverityLevel severityLevel;
    private Gender gender;
    private String contact;
    private String email;
    private int age;
    private Date admitDate;
    private int admittedBy;
    private Date dischargeDate;
    private int dischargedBy;
}
