package com.example.base.test.util.stopwatch;

import org.springframework.util.StopWatch;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class StopWatchUtil {

    public static void exportCsv(StopWatch stopWatch, String filePath) {
        Path path = Paths.get(filePath);
        Path directoryPath = path.getParent();

        createDirectory(directoryPath);

        List<StopWatch.TaskInfo> taskInfoList = List.of(stopWatch.getTaskInfo());

        // CSV 파일 쓰기
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            // CSV 파일 헤더 작성
            writer.println("Task Name,Total Time (nano second)");

            for (StopWatch.TaskInfo taskInfo : taskInfoList) {
                String taskName = taskInfo.getTaskName();
                long totalTimeMillis = taskInfo.getTimeNanos();

                writer.println(taskName +"," + totalTimeMillis);
            }
            System.out.println("CSV report created");
        } catch (IOException e) {
            System.err.println("Error occurred while creating CSV report " + e.getMessage());
        }
    }

    private static void createDirectory(Path path) {
        try {
            Files.createDirectories(path);
            System.out.println("Directory created successfully: " + path);
        } catch (IOException e) {
            System.err.println("Error creating directory: " + e.getMessage());
        }
    }
}
