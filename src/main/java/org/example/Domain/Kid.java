package org.example.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kid {
    private String name;
    private int age;
    private int ticketNumber;
    private boolean acceptsWaiting;

}
