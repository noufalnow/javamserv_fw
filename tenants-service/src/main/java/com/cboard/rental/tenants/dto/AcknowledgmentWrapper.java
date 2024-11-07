package com.cboard.rental.tenants.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class AcknowledgmentWrapper {

    @JsonProperty("acknowledgments")
    private List<PaymentScheduleStatusDTO> acknowledgments;

    public List<PaymentScheduleStatusDTO> getAcknowledgments() {
        return acknowledgments;
    }

    public void setAcknowledgments(List<PaymentScheduleStatusDTO> acknowledgments) {
        System.out.println("Setting acknowledgments: " + acknowledgments);
        this.acknowledgments = acknowledgments;
    }

    @Override
    public String toString() {
        return "AcknowledgmentWrapper{" +
               "acknowledgments=" + acknowledgments +
               '}';
    }
}
