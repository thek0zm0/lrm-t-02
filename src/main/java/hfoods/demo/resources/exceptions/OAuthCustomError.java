package hfoods.demo.resources.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OAuthCustomError implements Serializable {

    private static final long serialVersionUID = 9178661439383356177L;
    private String error;
    @JsonProperty("error_description")
    private String ErrorDescription;
}
