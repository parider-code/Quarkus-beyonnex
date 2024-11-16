package org.acme.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ListOfWordsPairs implements Serializable {

    private List<WordsPairs> listOfWordsPairs = new ArrayList<>();

    @Override
    public String toString() {
        return "ListOfWordsPairs{" +
                "listOfWordsPairs=" + listOfWordsPairs +
                '}';
    }
}
