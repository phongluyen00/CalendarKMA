package com.example.retrofitrxjava.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Article extends ModelResponse {
    private String title;
    private String location;
    private String date;
    private String thumb;
    private String des;
    private String link;
    private String income;
}

