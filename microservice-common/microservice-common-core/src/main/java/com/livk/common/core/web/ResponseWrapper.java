package com.livk.common.core.web;

import lombok.SneakyThrows;
import org.springframework.lang.NonNull;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * <p>
 * ResponseWrapper
 * </p>
 *
 * @author livk
 * @date 2022/8/15
 */
public final class ResponseWrapper extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream buffer;

    private final ServletOutputStream out;

    private final PrintWriter writer;

    public ResponseWrapper(HttpServletResponse response) throws IOException {
        super(response);
        buffer = new ByteArrayOutputStream();
        out = new WrapperOutputStream(buffer);
        writer = new PrintWriter(new OutputStreamWriter(buffer, this.getCharacterEncoding()));
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return out;
    }

    @Override
    public PrintWriter getWriter() {
        return writer;
    }

    @Override
    public void flushBuffer() throws IOException {
        if (out != null) {
            out.flush();
        }
        if (writer != null) {
            writer.flush();
        }
    }

    @Override
    public void reset() {
        buffer.reset();
    }

    public byte[] getResponseData() throws IOException {
        this.flushBuffer();
        return buffer.toByteArray();
    }

    private static class WrapperOutputStream extends ServletOutputStream {

        private final ByteArrayOutputStream stream;

        public WrapperOutputStream(ByteArrayOutputStream stream) {
            this.stream = stream;
        }

        @Override
        public void write(int b) {
            stream.write(b);
        }

        @Override
        public void write(@NonNull byte[] b) {
            stream.write(b, 0, b.length);
        }

        @SneakyThrows
        @Override
        public void setWriteListener(WriteListener writeListener) {
            writeListener.onWritePossible();
        }

        @Override
        public boolean isReady() {
            return false;
        }

    }

}

