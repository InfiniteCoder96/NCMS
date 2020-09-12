package lk.sparkx.ncms.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter  @NoArgsConstructor
public class Doctor {
    private String id;
    private String name;
    private String hospitalId;
    private int isDirector;
}
