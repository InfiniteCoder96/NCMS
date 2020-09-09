package lk.sparkx.ncms.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter  @NoArgsConstructor
public class User {
    private String username;
    private String password;
    private String name;
    private String moh;
    private String hospital;
}
