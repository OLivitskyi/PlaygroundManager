package org.example.playground.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KidRequest {
    private String name;
    private int age;
    private int ticketNumber;
    private boolean acceptsWaiting;

}
