package org.texttechnologylab.project.helper.helperimplementation;
import org.apache.poi.hssf.OldExcelFormatException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Der Basiscode zum Auslesen für Excel-dateien stammt aus verschiedenen Youtube-tutorials, diesen
 * habe ich dann angepasst, um alle Dateien aus einem Directory auszulesen.
 * ebenfalls die Speicherung in Datenstruckturen habe ich im Code ergänzt.
 */
public class AbstimmungsReader {

    /**
     * Überbleibsel aus dem Code aus den Tutorials.
     * Hierüber kann die readExelFiles Methode Testweise ausgeführt werden.
     * @param args
     */
    public static void main(String[] args) {
        String folderPath = "C:\\Users\\felix\\OneDrive\\Desktop\\Uni\\Semester 3\\PPR\\PPR-Programme\\uebung-2\\src\\main\\resources\\Abstimmungen";
        readExcelFiles(folderPath);
    }

    /**
     * Methode, die ein Directory von Excel-Files übergeben bekommt
     * und eine Liste der Dateien erzeugt.
     * Ruft Methode auf, um je nach File-Typ die Datei auszulesen.
     * @param folderPath
     */
    public static void readExcelFiles(String folderPath) {
        File folder = new File(folderPath);

        // Prüfe, ob Pfad Verzeichnis ist.
        if (!folder.isDirectory()) {
            System.out.println("Der angegebene Pfad ist kein Verzeichnis.");
            return;
        }

        // Erstelle Liste mit allen Dateien im Verzeichnis
        File[] files = folder.listFiles();

        // Prüft ob verzeichnis leer
        if (files == null || files.length == 0) {
            System.out.println("Keine Dateien im Verzeichnis gefunden.");
            return;
        }

        // Für jede Datei, Prüfe welche Endung und führe auslesen durch.
        for (File file : files) {
            if (file.isFile() && (file.getName().endsWith(".xls") || file.getName().endsWith(".xlsx"))) {
                System.out.println("Verarbeite Datei: " + file.getName());
                readExcelFile(file);
                System.out.println("-------------------------------");
            }
        }
    }

    /**
     * Methode bekommt jedes File einzeln übergeben und liest dieses entsprechend der Endung aus.
     * Speichert die Informationen aus der Excel in datenstrukturen ab.
     * @param file
     */
    private static void readExcelFile(File file) {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            Workbook workbook;

            // Überprüfen Sie den Dateityp und wählen Sie den entsprechenden Workbook-Typ
            if (file.getName().endsWith(".xls")) {
                System.out.println("Lese XLS-Datei...");
                try {
                    workbook = new HSSFWorkbook(inputStream); // Für XLS (Excel 97-2003)
                } catch (OldExcelFormatException e) {
                    System.out.println("Fehler beim Lesen der XLS-Datei. Versuche es mit HSSFWorkbook erneut...");
                    workbook = WorkbookFactory.create(inputStream); // BIFF5-Dateien mit WorkbookFactory öffnen
                }
            } else {
                System.out.println("Lese XLSX-Datei...");
                workbook = new XSSFWorkbook(inputStream); // Für XLSX (Excel 2007 und neuer)
            }

            // Wählen Sie das erste Blatt aus
            Sheet sheet = workbook.getSheetAt(0);

            // Iteriere über die Zeilen und Zellen
            for (Row row : sheet) {
                for (Cell cell : row) {
                    // Zellenwert ausgeben (abhängig vom Zellentyp)
                    switch (cell.getCellType()) {
                        case STRING:
                            System.out.print(cell.getStringCellValue() + "\t");
                            break;
                        case NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "\t");
                            break;
                        case BOOLEAN:
                            System.out.print(cell.getBooleanCellValue() + "\t");
                            break;
                        default:
                            System.out.print("\t");
                    }
                }
                System.out.println(); // Neue Zeile nach jeder Zeile
            }

            // Excel-Datei schließen
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
