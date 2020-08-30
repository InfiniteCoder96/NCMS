package lk.sparkx.ncms.payload;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by: thisum
 * Date      : 2020-08-21
 * Time      : 00:28
 *
 *  Update Logs
 *  2020-08-30 14.01 PASINDU - Added Lombok dependency and annotations
 **/
@Getter @Setter @NoArgsConstructor
public class ApiResponse
{
    private String success;
    private String response;
}
