package lib.codegames.io;

import lib.codegames.debug.LogCG;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;

public class FileCG extends File {

    public FileCG(String pathname) {
        super(pathname);
    }

    public FileCG(String parent, String child) {
        super(parent, child);
    }

    public FileCG(File parent, String child) {
        super(parent, child);
    }

    public FileCG(URI uri) {
        super(uri);
    }

    public byte[] readAllByte() {
        try {
            byte[] bytes = readAllByteWithException();
            LogCG.d("Read Successfully" + " || " + this.getAbsoluteFile());
            return bytes;
        }catch (IOException e) {
            LogCG.d(e.getMessage() + " || " + this.getAbsoluteFile());
            return new byte[0];
        }
    }

    public byte[] readAllByteWithException() throws IOException {
        InputStream inputStream = new FileInputStream(this);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        inputStream.close();
        return bytes;
    }

    public String readAllText() {
        return readAllByte().toString();
    }

    public String readAllTextWithException() throws IOException {
        return readAllByteWithException().toString();
    }

    public ArrayList<String> readAllLines() {
        ArrayList<String> lines = new ArrayList<>();
        try {
            lines = readAllLinesWithException();
            LogCG.d("Read Successfully" + " || " + this.getAbsoluteFile());
            return lines;
        }catch (IOException e) {
            LogCG.d(e.getMessage() + " || " + this.getAbsoluteFile());
            return lines;
        }
    }

    public ArrayList<String> readAllLinesWithException() throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(this));

        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }

        reader.close();

        return lines;
    }

    public boolean write(byte[] bytes) {
        try {
            writeWithException(bytes);
            LogCG.d("Write Successfully" + " || " + this.getAbsoluteFile());
            return true;
        }catch (IOException e) {
            LogCG.d(e.getMessage() + " || " + this.getAbsoluteFile());
            return false;
        }
    }

    public void writeWithException(byte[] bytes) throws IOException {
        createFileOrDirectory(this);
        OutputStream outputStream = new FileOutputStream(this, false);
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    public boolean write(String text) {
        return write(text.getBytes());
    }

    public void writeWithException(String text) throws IOException {
        writeWithException(text.getBytes());
    }

    public boolean append(byte[] bytes) {
        try {
            appendWithException(bytes);
            LogCG.d("Append Successfully" + " || " + this.getAbsoluteFile());
            return true;
        }catch (IOException e) {
            LogCG.d(e.getMessage() + " || " + this.getAbsoluteFile());
            return false;
        }
    }

    public void appendWithException(byte[] bytes) throws IOException {
        createFileOrDirectory(this);
        OutputStream outputStream = new FileOutputStream(this, true);
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    public boolean append(String text) {
        return append(text.getBytes());
    }

    public void appendWithException(String text) throws IOException {
        appendWithException(text.getBytes());
    }

    public boolean appendLine(String text) {
        return append("\n" + text);
    }

    public void appendLineWithException(String text) throws IOException {
        appendWithException("\n" + text);
    }

    public boolean copyTo(File file, int bufferSize) {

        try {
            copyToWithException(file, bufferSize);
            LogCG.d("Copy Successfully" + " || " + this.getAbsolutePath() + " -> " + file.getAbsolutePath());
            return true;
        }catch (IOException e) {
            LogCG.d(e.getMessage() + " || " + this.getAbsoluteFile());
            return false;
        }

    }

    public void copyToWithException(File file, int bufferSize) throws IOException {

        InputStream inputStream = new FileInputStream(this);
        OutputStream outputStream = new FileOutputStream(file);

        byte[] bytes = new byte[bufferSize];
        while (inputStream.read(bytes) != -1) {
            outputStream.write(bytes);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();

    }

    public boolean copyTo(File file) {
        return copyTo(file, 4096);
    }

    public void copyToWithException(File file) throws IOException {
        copyToWithException(file, 4096);
    }

    private Boolean createFileOrDirectory(File file) throws IOException {
        if(!exists()) {
            if(isDirectory())
                return mkdirs();
            else {
                return file.getParentFile().mkdirs() && file.createNewFile();
            }
        }
        return true;
    }

}
