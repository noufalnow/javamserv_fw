package com.cboard.rental.messaging.DTO;
import lombok.Data;


@Data
public class AcknowledgmentDTO {

    private Long id;
    private String status;

    public AcknowledgmentDTO(Long id, String status) {
        this.id = id;
        this.status = status;
    }
}
