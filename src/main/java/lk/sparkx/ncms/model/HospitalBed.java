package lk.sparkx.ncms.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter  @NoArgsConstructor
public class HospitalBed {
    private String id;
    private String hospitalId;
    private String patientId;
}
