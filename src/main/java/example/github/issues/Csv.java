/* Copyright 2020 Svante Schubert
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.github.issues;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

/** 
    This class encapsulates the implementation details of our EN 16931 spreadsheets being saved as "comma separated value" (CSV).
    
    NOTE: 
        GoogleDocs documents are saved by default as CSV using "," as separated but we have "," in our content, which caused problems.
        Therefore, the CSV separator was manually exchanged from "," to ";".
 */
class Csv {

    private static final String COMMA_DELIMITER = ";";
    private static final String CSV_PATH = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "issues";
    private static final String CSV_NAME = "issue-list.csv";
    private static final Integer EXPECTED_ISSUE_COUNT = 51; // plus heading
    static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * This enum contains all possible Columns
     */
    enum Column {
        TITLE("Description", 1),
        MILESTONE("Planned", 2),
        ORDERING("Ordering", 3),
        DOMAIN("Domain", 4),
        ERROR("Error", 5),
        NATIONAL_LAW("National Law", 6),
        MULTIPLE_COUNTRIES("Multiple Countries", 7),
        ALL_COUNTRIES("All Countries", 8),
        TAX_CALCULATION("Tax Calculation", 9);

        private final String mColumnName;
        private final Integer mColumnNo;

        Column(String columnName, Integer columnNo) {
            this.mColumnName = columnName;
            this.mColumnNo = columnNo;
        }

        /**
         * @return Column name
         */
        public String getColumnName() {
            return mColumnName;
        }

        /**
         * @return Column Number
         */
        public Integer getColumnNo() {
            return mColumnNo;
        }
    };

    static ArrayList<List<String>> readCSV() {
        // reading CVS         
        ArrayList<List<String>> allLinesByPrio = new ArrayList<>(EXPECTED_ISSUE_COUNT);
        Path csvPath = FileSystems.getDefault().getPath(CSV_PATH, CSV_NAME);
        try (Stream<String> lines = Files.lines(csvPath)) {
            lines.forEach(line -> createListFromLine(line, allLinesByPrio));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return allLinesByPrio;
    }

    private static void createListFromLine(String line, ArrayList<List<String>> allLinesByPrio) {
//      System.out.println("\nline: " + line);
        List<String> issueProps = getDataFromLine(line);
        allLinesByPrio.add(issueProps);
    }

    // splitting line into data
    private static List<String> getDataFromLine(String line) {
        List<String> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    static boolean isHeader(List<String> lineData) {
        return lineData.get(Csv.Column.TITLE.getColumnNo()).equals(Csv.Column.TITLE.getColumnName());
    }

    static boolean isSummary(List<String> lineData) {
        return lineData.get(Csv.Column.TITLE.getColumnNo()).isEmpty();
    }
}
