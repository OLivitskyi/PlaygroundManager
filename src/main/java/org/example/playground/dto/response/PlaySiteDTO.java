package org.example.playground.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaySiteDTO {
    private String id;
    private String name;
    private int capacity;
    private int doubleSwings;
    private int carousel;
    private int slide;
    private int ballPit;
}
