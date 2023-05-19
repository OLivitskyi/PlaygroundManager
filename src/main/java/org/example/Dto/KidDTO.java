package org.example.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KidDTO {
    private String name;
    private int age;
    private int ticketNumber;
    private boolean acceptsWaiting;

}
