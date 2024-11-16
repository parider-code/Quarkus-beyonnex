package org.acme.requests;

import java.io.Serializable;
import lombok.*;

/*@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data*/
public class WordsPairs implements Serializable {

  private String leftString;
  private String rightString;

  public String getLeftString() {
    return leftString;
  }

  public void setLeftString(String leftString) {
    this.leftString = leftString;
  }

  public String getRightString() {
    return rightString;
  }

  public void setRightString(String rightString) {
    this.rightString = rightString;
  }
}
