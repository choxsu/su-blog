package com.choxsu.service;

import java.io.InputStream;
import java.io.PrintStream;

/**
 * @author chox su
 * @date 2018/02/01 11:01
 */
public class Ts {

    private static native void setOut0(PrintStream out);

    private static native void hello();

    static {
        System.loadLibrary("setOut0");
    }

    public static void main(String[] args) {
        PrintStream printStream = null;
        setOut0(printStream);
        printStream.println("hello world");
    }

}
