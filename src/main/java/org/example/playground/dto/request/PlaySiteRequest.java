package org.example.playground.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaySiteRequest {
    private String name;
    private int capacity;
    private int doubleSwings;
    private int carousel;
    private int slide;
    private int ballPit;

}
