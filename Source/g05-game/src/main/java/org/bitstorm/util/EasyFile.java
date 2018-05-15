/*
 * Easy text file operations Copyright 2003, 2004 Edwin Martin <edwin@bitstorm.org>
 *
 */
package org.bitstorm.util;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Class for easy text file read and write operations.
 *
 * @author Edwin Martin
 *
 */
public class EasyFile {

  private String fileExtension;
  private String filename;
  private String filepath;
  private Frame parent;
  private InputStream textFileReader;
  private OutputStream textFileWriter;
  private String title;

  /**
   * Constructs a EasyFile. Open file with file selector.
   *
   * @param parent parent frame
   * @param title title of fileselector
   */
  public EasyFile(final Frame parent, final String title) {
    this.parent = parent;
    this.title = title;
    textFileReader = null;
    textFileWriter = null;
    fileExtension = null;
  }

  /**
   * Constructs a EasyFile. Read file from stream.
   *
   * @param textFileReader stream to read from
   */
  public EasyFile(final InputStream textFileReader) {
    this.textFileReader = textFileReader;
    textFileWriter = null;
    filepath = null;
    fileExtension = null;
  }

  /**
   * Constructs a EasyFile. Write file to stream.
   *
   * @param textFileWriter stream to write to
   */
  public EasyFile(final OutputStream textFileWriter) {
    this.textFileWriter = textFileWriter;
    textFileReader = null;
    filepath = null;
    fileExtension = null;
  }

  /**
   * Constructs a EasyFile. Open file by filename.
   *
   * @param filepath path of file
   */
  public EasyFile(final String filepath) {
    this.filepath = filepath;
    textFileReader = null;
    textFileWriter = null;
    fileExtension = null;
  }

  /**
   * Constructs a EasyFile. Open file by url.
   *
   * @param url url of file.
   * @throws FileNotFoundException File not found.
   */
  public EasyFile(final URL url) throws IOException {
    filepath = null;
    textFileWriter = null;
    fileExtension = null;

    textFileReader = url.openStream();
  }

  /**
   * Gets filename
   *
   * @return filename
   */
  public String getFileName() {
    return filename == null ? filepath : filename;
  }

  /**
   * Reads a text file into a string.
   *
   * @return contents of file
   * @throws IOException file exceptions.
   */
  public String readText() throws IOException {
    int bytesRead;

    if (textFileReader == null) {
      if (filepath == null) {
        final FileDialog filedialog = new FileDialog(parent, title, FileDialog.LOAD);
        filedialog.setFile(filename);
        filedialog.setVisible(true);
        if (filedialog.getFile() != null) {
          filename = filedialog.getFile();
          filepath = filedialog.getDirectory() + filename;
        } else {
          return "";
        }
      }
      textFileReader = new FileInputStream(filepath);
    }

    final StringBuffer text = new StringBuffer();
    final int bufferLength = 1024;
    final byte[] buffer = new byte[bufferLength];

    try {
      while ((bytesRead = textFileReader.read(buffer, 0, bufferLength)) != -1) {
        text.append(new String(buffer, 0, bytesRead, "UTF-8"));
      }
    } finally {
      textFileReader.close();
    }
    return text.toString();
  }

  /**
   * Sets file extension to use
   *
   * @param s filename
   */
  public void setFileExtension(final String s) {
    fileExtension = s;
  }

  /**
   * Sets filename to use
   *
   * @param s filename
   */
  public void setFileName(final String s) {
    filename = s;
  }

  /**
   * Writes a string to a text file.
   *
   * @param text text to write
   * @throws IOException file exceptions.
   */
  public void writeText(final String text) throws IOException {
    if (textFileWriter == null) {
      if (filepath == null) {
        final FileDialog filedialog = new FileDialog(parent, title, FileDialog.SAVE);
        filedialog.setFile(filename);
        filedialog.setVisible(true);
        if (filedialog.getFile() != null) {
          filename = filedialog.getFile();
          filepath = filedialog.getDirectory() + filename;
          if (fileExtension != null && filepath.indexOf('.') == -1) {
            filepath = filepath + fileExtension;
          }
        } else {
          return;
        }
      }
      textFileWriter = new FileOutputStream(filepath);
    }

    try {
      textFileWriter.write(text.getBytes("UTF-8"));
    } finally {
      textFileWriter.close();
    }
  }
}
