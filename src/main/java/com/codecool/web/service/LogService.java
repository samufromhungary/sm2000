package com.codecool.web.service;

import com.codecool.web.service.exception.ServiceException;

import java.io.IOException;
import java.util.List;

public interface LogService {
    void log(String msg) throws IOException;
}
