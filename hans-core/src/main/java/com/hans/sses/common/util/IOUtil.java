package com.hans.sses.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by kodaji on 15. 9. 16.
 */
public class IOUtil {
    private final static Logger LOG = LoggerFactory.getLogger(IOUtil.class);

    public static void close(Socket socket) {
        try {
            if (socket != null) {
                socket.close();
                socket = null;
            }
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static void close(InputStream in) {
        try {
            if (in != null) {
                in.close();
                in = null;
            }
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static void close(OutputStream out) {
        try {
            if (out != null) {
                out.close();
                out = null;
            }
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static void close(Reader reader) {
        try {
            if (reader != null) {
                reader.close();
                reader = null;
            }
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static void close(Writer writer) {
        try {
            if (writer != null) {
                writer.close();
                writer = null;
            }
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static void close(Closeable io) {
        try {
            if (io != null) {
                io.close();
                io = null;
            }
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static String getHostName() {
        String hostname = null;
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            LOG.error(e.getMessage(), e);
        }
        return hostname;
    }

    public static String getCanonicalHostName() {
        String hostname = null;
        try {
            hostname = InetAddress.getLocalHost().getCanonicalHostName();
        } catch (UnknownHostException e) {
            LOG.error(e.getMessage(), e);
        }
        return hostname;
    }
}
