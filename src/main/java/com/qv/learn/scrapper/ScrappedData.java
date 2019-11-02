package com.qv.learn.scrapper;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class ScrappedData implements Comparable<ScrappedData> {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long id;

    private Integer chapter;
    private Integer verse;
    private Integer token;
    private Integer juzz;

    private String audioId;
    private String translation;
    private byte[] imageData;
    private String imageAlt;

    @Override
    public int compareTo(ScrappedData o) {

        if( this.chapter == o.chapter && this.verse==o.verse && this.token==o.token )
            return 0;
        else
            return -1;
    }
}
