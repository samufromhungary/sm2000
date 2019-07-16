package com.codecool.web.service.simple;

import com.codecool.web.service.LogService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SimpleLogService implements LogService {

    public SimpleLogService() {}

    public void log(String msg) throws IOException {
        List<String> log = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("'['yyyy-MM-dd HH:mm:ss z']'");
        Date date = new Date(System.currentTimeMillis());

        log.add(formatter.format(date) + ": " + msg);
        Path file = Paths.get("log.txt");
        Files.write(file, log, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
    }
}
