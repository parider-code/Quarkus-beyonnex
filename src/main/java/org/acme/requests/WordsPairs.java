package org.acme.requests;

import java.io.Serializable;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WordsPairs implements Serializable {

  private String leftString;
  private String rightString;

}
