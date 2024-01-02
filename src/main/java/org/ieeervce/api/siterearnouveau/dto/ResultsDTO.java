package org.ieeervce.api.siterearnouveau.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultsDTO<T> {
    private final boolean ok;
    private final T response;
    private final String message;
    /**
     * Generate good response
     * @param response
     */
    public ResultsDTO(T response) {
        this.response = response;
        this.ok = true;
        this.message = null;
    }
}
