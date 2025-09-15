package com.accessibility_helper.controller;

import com.accessibility_helper.model.Recording;
import com.accessibility_helper.service.RecordingService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recordings")
@CrossOrigin(origins = "http://localhost:3000") // React dev server origin
public class RecordingController {

    private final RecordingService recordingService;

    public RecordingController(RecordingService recordingService) {
        this.recordingService = recordingService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadRecording(
            @RequestParam("file") MultipartFile file,
            @RequestParam("transcript") String transcript) {
        try {
            Recording saved = recordingService.saveRecording(file, transcript);
            //return ResponseEntity.ok().body("Recording saved with id: " + saved.getId());
            return ResponseEntity.ok().body(Map.of("id", saved.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to save recording: " + e.getMessage());
            //return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public List<RecordingDTO> getAllRecordings() {
        List<Recording> recordings = recordingService.getAllRecordings();
        String backendUrl = "http://localhost:8080";
        return recordings.stream()
                .map(r -> new RecordingDTO(r.getId(), r.getText(), "http://localhost:8080/api/recordings/" + r.getId() + "/audio"))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/audio")
    public ResponseEntity<byte[]> getAudio(@PathVariable Long id) {
        return recordingService.getAllRecordings().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .map(r -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=recording_" + id + ".webm")
                        .contentType(MediaType.parseMediaType(r.getContentType()))
                        .body(r.getAudioData()))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecording(@PathVariable Long id) {
        boolean exists = recordingService.getAllRecordings().stream()
                .anyMatch(r -> r.getId().equals(id));
        if (!exists) {
            return ResponseEntity.notFound().build();
        }
        recordingService.deleteRecordingById(id);
        return ResponseEntity.ok().body(Map.of("message", "Recording deleted"));
    }


    // DTO class for sending recording metadata to frontend
    public static class RecordingDTO {
        private Long id;
        private String text;
        private String audioUrl;

        public RecordingDTO(Long id, String text, String audioUrl) {
            this.id = id;
            this.text = text;
            this.audioUrl = audioUrl;
        }

        public Long getId() {
            return id;
        }

        public String getText() {
            return text;
        }

        public String getAudioUrl() {
            return audioUrl;
        }
    }
}
