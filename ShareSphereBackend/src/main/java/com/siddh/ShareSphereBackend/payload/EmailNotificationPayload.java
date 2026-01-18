package com.siddh.ShareSphereBackend.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailNotificationPayload {
    private String toEmail;
    private String customerName;
    private String planName;
    private double amount;
}