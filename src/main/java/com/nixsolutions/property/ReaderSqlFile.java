package com.nixsolutions.property;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReaderSqlFile {

    public String executeSqlScript() {
        String line;
        StringBuilder sql = new StringBuilder();
        InputStream resource = getClass().getClassLoader()
            .getResourceAsStream("query.sql");
        if (resource != null) {
            try (InputStreamReader reader = new InputStreamReader(resource);
                 BufferedReader bufferedReader = new BufferedReader(reader)) {
                while ((line = bufferedReader.readLine()) != null) {
                    sql.append(line);
                    sql.append(" ");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sql.toString();
    }
}
