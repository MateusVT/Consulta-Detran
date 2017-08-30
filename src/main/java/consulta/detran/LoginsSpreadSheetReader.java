package consulta.detran;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Torres
 */
public class LoginsSpreadSheetReader {

    FileInputStream file;
    XSSFWorkbook workbook;

    List<Motorista> motoristas = new ArrayList<>();

    public void lePlanilhaLogins(String dirEntrada) throws FileNotFoundException, IOException {
        int linha = 1, coluna = 0;
        file = new FileInputStream(new File(dirEntrada).getAbsolutePath());
        workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);
//        Files.move(Paths.get("/foo.txt"), Paths.get("bar.txt"));
        XSSFRow row1 = sheet.getRow(1);
        XSSFCell cell1 = row1.getCell(0);

        try {
            while (row1 != null) {
                row1 = sheet.getRow(linha);

                Motorista motorista = new Motorista(
//                        row1.getCell(coluna).getStringCellValue(),
                        row1.getCell(coluna + 1).getStringCellValue(),
                        row1.getCell(coluna + 2).getStringCellValue(),
                        row1.getCell(coluna + 3).getStringCellValue()
                );

                motoristas.add(motorista);
                linha++;

            }

        } catch (NullPointerException e) {
            
//            JOptionPane.showMessageDialog(null, "Estrutura do arquivo incompátivel!");
        }

    }

    public List<Motorista> getMotoristas() {
        return motoristas;
    }

}
