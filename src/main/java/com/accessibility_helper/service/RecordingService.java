package com.accessibility_helper.service;

import com.accessibility_helper.model.Recording;
import com.accessibility_helper.repository.RecordingRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class RecordingService {

    private final RecordingRepository recordingRepository;

    public RecordingService(RecordingRepository recordingRepository) {
        this.recordingRepository = recordingRepository;
    }

    public Recording saveRecording(MultipartFile file, String text) throws IOException {
        Recording recording = new Recording();
        recording.setText(text);
        recording.setAudioData(file.getBytes());
        recording.setContentType(file.getContentType());
        return recordingRepository.save(recording);
    }

    public List<Recording> getAllRecordings() {
        return recordingRepository.findAll();
    }

    public void deleteRecordingById(Long id) {
        recordingRepository.deleteById(id);
    }
}
