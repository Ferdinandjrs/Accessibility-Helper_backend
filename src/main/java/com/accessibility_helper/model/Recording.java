package com.accessibility_helper.model;

import jakarta.persistence.*;

@Entity
@Table(name = "recordings")
public class Recording {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String text;

    @Lob
    @Column(name = "audio_data", columnDefinition = "LONGBLOB")
    private byte[] audioData;

    @Column(name = "content_type")
    private String contentType;

    public Recording() {}

    public Recording(String text, byte[] audioData, String contentType) {
        this.text = text;
        this.audioData = audioData;
        this.contentType = contentType;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getAudioData() {
        return audioData;
    }

    public void setAudioData(byte[] audioData) {
        this.audioData = audioData;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
