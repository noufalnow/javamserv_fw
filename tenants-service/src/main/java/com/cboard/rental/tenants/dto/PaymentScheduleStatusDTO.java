package com.cboard.rental.tenants.dto;

//DTO for individual acknowledgment events
import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class PaymentScheduleStatusDTO {
	private Long id;

	@NotNull(message = "Payment status is required")
	private String status;

	@Override
	public String toString() {
		return "PaymentScheduleStatusDTO{" + "id=" + id + ", status='" + status + '\'' + '}';
	}
}
