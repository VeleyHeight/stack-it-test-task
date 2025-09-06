package org.example;

import org.example.impl.ProcessingFileServiceImpl;

public class Main {
    public static void main(String[] args) {
        ProcessingFileService processingFileService = new ProcessingFileServiceImpl();
        processingFileService.processingFile();
    }
}