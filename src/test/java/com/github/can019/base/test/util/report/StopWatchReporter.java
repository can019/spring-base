package com.github.can019.base.test.util.report;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
public class StopWatchReporter {
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    public enum ReportType{
        CSV("csv");

        private String extension;

        ReportType(String extension){
            this.extension = extension;
        }

        public String getExtension() {
            return extension;
        }
    }

    public static void exportReport(StopWatch stopWatch, String fileName, Class clazz, ReportType reportType) {
        String exportPath = getExportPath(fileName, clazz, reportType);
        Path directoryPath = Paths.get(exportPath).getParent();

        createDirectory(directoryPath);

        switch (reportType){
            case CSV -> writeCsv(stopWatch, exportPath);
            default -> throw new RuntimeException("Unsupported ReportType");
        }
    }

    public static String getExportPath(String fileName, Class clazz, ReportType reportType) {
        return String.join("/",
                ReportPath.ROOT_PATH.getPath(),
                clazz.getPackageName(),
                clazz.getSimpleName(),
                dateFormat.format(new Date()),
                fileName + "."+reportType.getExtension());
    }

    private static void writeCsv(StopWatch stopWatch, String filePath) {
        List<StopWatch.TaskInfo> taskInfoList = List.of(stopWatch.getTaskInfo());

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
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
            if(!Files.exists(path)){
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            log.error("Error creating directory: {}", e.getMessage());
        }
    }
}
