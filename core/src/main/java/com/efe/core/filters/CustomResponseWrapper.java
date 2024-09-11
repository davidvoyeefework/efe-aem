package com.efe.core.filters;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class CustomResponseWrapper extends HttpServletResponseWrapper {

    private StringWriter stringWriter;
    private PrintWriter writer;

    public CustomResponseWrapper(HttpServletResponse response, StringWriter stringWriter) {
        super(response);
        this.stringWriter = stringWriter;
        this.writer = new PrintWriter(stringWriter);
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return writer;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        throw new UnsupportedOperationException("getOutputStream() is not supported by this wrapper.");
    }

    public String getOutput() {
        return stringWriter.toString();
    }
}
