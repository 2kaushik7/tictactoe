package org.sai.tictactoe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Data
@AllArgsConstructor
public class Result {
    private boolean result;
    private String winner;
}
