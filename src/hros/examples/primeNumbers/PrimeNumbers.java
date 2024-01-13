

package hros.examples.primeNumbers;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PrimeNumbers {
    
    private static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: PrimesFinder <data_file>");
            return;
        }
        String filePath = args[0];
        List<Integer> primes = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(new File(filePath));
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell cell = row.getCell(1);
                if (cell != null && cell.getCellType() == CellType.STRING) {
                    String value = cell.getStringCellValue().trim();
                    try {
                        int number = Integer.parseInt(value);
                        if (isPrime(number)) {
                            primes.add(number);
                        }
                    } catch (NumberFormatException e) {
                        // ignore non-integer values
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading data file: " + e.getMessage());
            return;
        }
        for (int prime : primes) {
            System.out.println(prime);
        }
    }
}
