package com.nsahukar.android.base.common.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.auto.value.AutoValue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

/**
 * Created by Nikhil on 26/12/17.
 */

@AutoValue
public abstract class CacheUtils {
    @NonNull public abstract File cacheDir();
    abstract boolean isTablet();

    @NonNull
    public static Builder builder() {
        return new AutoValue_CacheUtils.Builder();
    }

    @AutoValue.Builder
    public interface Builder {
        Builder cacheDir(final File cacheDir);
        Builder isTablet(final boolean isTablet);
        CacheUtils build();
    }

    private String getHtmlBoilerplatePrefix() {
        String fontSize = isTablet() ? "21px" : "18px";
        String firstLetterFontSize = isTablet() ? "42px" : "36px";
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<style type=\"text/css\">" +
                "@font-face {" +
                "font-family: lora;" +
                "src: url('file:///android_asset/fonts/lora_regular.ttf')" +
                "}" +
                "body {" +
                "font-family: lora;" +
                "font-weight: 400;" +
                "font-size: " + fontSize + ";" +
                "text-align: justify;" +
                "}" +
                "#first-letter {" +
                "font-size: " + firstLetterFontSize + ";" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<p>";
    }

    private String getHtmlBoilerplateSuffix() {
        return "</p>" +
                "</body>" +
                "</html>";
    }

    public String getCachedHtmlPath(@NonNull final String title) {
        String fileName = title + ".html";
        File articleHtmlFile = new File(cacheDir(), fileName);
        if (articleHtmlFile.exists()) return articleHtmlFile.getAbsolutePath();
        return null;
    }

    public String putInHtmlCache(@NonNull final String title, @NonNull final String body) {
        BufferedReader bodyReader = new BufferedReader(new StringReader(body));
        String fileName = title + ".html";
        File articleHtmlFile = new File(cacheDir(), fileName);
        PrintWriter bodyInHtmlWriter = null;
        boolean articleWrittenToFileInHtml = false;
        try {
            bodyInHtmlWriter = new PrintWriter(new BufferedWriter(new FileWriter(articleHtmlFile)));
            bodyInHtmlWriter.append(getHtmlBoilerplatePrefix());
            String line;
            boolean firstLine = true;
            while ((line = bodyReader.readLine()) != null) {
                line = line.trim();
                if (TextUtils.isEmpty(line)) {
                    bodyInHtmlWriter.append("</p>");
                    bodyInHtmlWriter.append("<p>");
                } else {
                    if (firstLine) {
                        String firstLetter = line.substring(0, 1);
                        String biggerFirstLetter = "<span id=\"first-letter\">" + firstLetter + "</span>";
                        bodyInHtmlWriter.append(biggerFirstLetter);
                        line = line.substring(1);
                        firstLine = false;
                    }
                    line += " ";
                    bodyInHtmlWriter.append(line);
                }
            }
            bodyInHtmlWriter.append(getHtmlBoilerplateSuffix());
            bodyInHtmlWriter.flush();
            articleWrittenToFileInHtml = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bodyReader.close();
                if (bodyInHtmlWriter != null) {
                    bodyInHtmlWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (articleWrittenToFileInHtml) return articleHtmlFile.getAbsolutePath();
        return null;
    }
}
