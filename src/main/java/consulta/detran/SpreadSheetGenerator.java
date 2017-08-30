package consulta.detran;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.poi.common.usermodel.Hyperlink;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.common.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Torres
 */
public class SpreadSheetGenerator {

    InputStream file;
    XSSFWorkbook workbook;
    XSSFSheet sheet;
    CellStyle styleRed;
    CellStyle styleGreen;
    CellStyle styleNeutral;
    CellStyle styleLink;
    List<Motorista> motoristas;
    String diretorioPrincipal;
    Boolean completoBool;
    Boolean dozeMesesBool;
    Boolean cincoAnosBool;

    public SpreadSheetGenerator(String diretorio, Boolean completo, Boolean dozeMeses, Boolean cincoAnos) {
        this.completoBool = completo;
        this.dozeMesesBool = dozeMeses;
        this.cincoAnosBool = cincoAnos;
        this.diretorioPrincipal = diretorio;
        System.out.println(diretorioPrincipal);

    }

    public void geraPlanilha(String diretorio, List<Motorista> motoristas, Boolean saidaCSV, Boolean saidaXLSX, Boolean todosFunc, Boolean riscoFunc, Boolean irregFunc) throws FileNotFoundException, IOException {
//        file = (FileInputStream) getClass().getResourceAsStream("/modelo.xlsx");
        file = getClass().getResourceAsStream("/modelo.xlsx");
        DateFormat dateFormat = new SimpleDateFormat("(dd-MM-yyyy) - HH.mm");
        Date date = new Date();

        workbook = new XSSFWorkbook(file);
        sheet = workbook.getSheetAt(0);

        styleNeutral = workbook.createCellStyle();
        styleNeutral.setAlignment(CellStyle.ALIGN_LEFT);
        styleNeutral.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleNeutral.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleNeutral.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleNeutral.setBorderRight(HSSFCellStyle.BORDER_THIN);

        styleRed = workbook.createCellStyle();
        styleRed.setFillForegroundColor(IndexedColors.RED.getIndex());
        styleRed.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styleRed.setAlignment(CellStyle.ALIGN_LEFT);
        styleRed.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleRed.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleRed.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleRed.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        styleRed.setAlignment(CellStyle.ALIGN_CENTER);

        styleGreen = workbook.createCellStyle();
        styleGreen.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        styleGreen.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styleGreen.setAlignment(CellStyle.ALIGN_LEFT);
        styleGreen.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleGreen.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleGreen.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleGreen.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        styleGreen.setAlignment(CellStyle.ALIGN_CENTER);

        styleLink = workbook.createCellStyle();
        Font linkFont = workbook.createFont();
        linkFont.setUnderline(Font.U_SINGLE);
        linkFont.setColor(IndexedColors.BLUE.getIndex());
        styleLink.setFont(linkFont);

        this.motoristas = motoristas;

        if (todosFunc) {
            todos();
        }
        if (riscoFunc) {
            emRisco();
        }
        if (irregFunc) {
            irregulares();
        }

        if (saidaCSV) {
            FileOutputStream fileCSV = new FileOutputStream(new File(diretorioPrincipal + "/Relatório - " + dateFormat.format(date) + ".csv"));
            workbook.write(fileCSV);
        }
        if (saidaXLSX) {
            FileOutputStream fileXLSX = new FileOutputStream(new File(diretorioPrincipal + "/Relatório - " + dateFormat.format(date) + ".xlsx"));
            workbook.write(fileXLSX);
        }

        file.close();
    }

    public void todos() {
        CreationHelper createHelper = workbook.getCreationHelper();

        for (int linha = 1; linha < (motoristas.size() + 1); linha++) {

            XSSFRow row1 = sheet.getRow(linha);

            for (int coluna = 0; coluna < 12; coluna++) {

                XSSFCell cell1 = row1.createCell(coluna);

                switch (coluna) {
                    case 0:
                        cell1.setCellStyle(styleNeutral);
                        cell1.setCellValue(motoristas.get(linha - 1).getNomeMotorista());
                        break;
                    case 1:
                        cell1.setCellStyle(styleNeutral);
                        cell1.setCellValue(motoristas.get(linha - 1).getCNH());
                        break;
                    case 2:
                        cell1.setCellStyle(styleNeutral);
                        cell1.setCellValue(motoristas.get(linha - 1).getCPF());
                        break;
                    case 3:
                        cell1.setCellStyle(styleNeutral);
                        cell1.setCellValue(motoristas.get(linha - 1).getValidadeCNH());
                        break;
                    case 4:
                        if (motoristas.get(linha - 1).getSituacao().equals("NORMAL")) {
                            cell1.setCellStyle(styleNeutral);
                            cell1.setCellValue(motoristas.get(linha - 1).getSituacao());
                        } else {
                            cell1.setCellStyle(styleRed);
                            cell1.setCellValue(motoristas.get(linha - 1).getSituacao());
                        }
                        break;
                    case 5:
                        if (motoristas.get(linha - 1).getFiscalizacao().equals("NÃO RECOLHER CNH")) {
                            cell1.setCellStyle(styleNeutral);
                            cell1.setCellValue(motoristas.get(linha - 1).getFiscalizacao());
                        } else {
                            cell1.setCellStyle(styleRed);
                            cell1.setCellValue(motoristas.get(linha - 1).getFiscalizacao());
                        }
                        break;
                    case 6:
                        cell1.setCellStyle(styleNeutral);
                        cell1.setCellValue(Arrays.toString(motoristas.get(linha - 1).getMultas()));
                        break;
                    case 7:
                        cell1.setCellStyle(styleNeutral);
                        cell1.setCellValue(Arrays.toString(motoristas.get(linha - 1).getPontosMultas()));
                        break;
                    case 8:
                        if (motoristas.get(linha - 1).getRisco().equals("SIM")) {
                            cell1.setCellStyle(styleRed);
                            cell1.setCellValue(motoristas.get(linha - 1).getRisco());

                        } else if (motoristas.get(linha - 1).getRisco().equals("NÃO")
                                && (!motoristas.get(linha - 1).getFiscalizacao().equals("NÃO RECOLHER CNH")
                                || !motoristas.get(linha - 1).getSituacao().equals("NORMAL"))) {
                            cell1.setCellStyle(styleRed);
                            cell1.setCellValue("SUSPENSA/CASSADA");

                        } else {
                            cell1.setCellStyle(styleGreen);
                            cell1.setCellValue(motoristas.get(linha - 1).getRisco());
                        }
                        break;
                    case 9:
                        if (dozeMesesBool && !motoristas.get(linha - 1).getNomeMotorista().equals("Null")) {
                            cell1.setCellStyle(styleLink);
                            XSSFHyperlink link12 = (XSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_FILE);
                            String dozeMeses = diretorioPrincipal + "/Extratos 12 Meses/" + motoristas.get(linha - 1).getNomeMotorista() + ".pdf";
                            File filePDF12 = new File(dozeMeses);
                            System.out.println(filePDF12.toURI().toString());
                            link12.setAddress(filePDF12.toURI().toString());
                            cell1.setCellValue("Extrato 12 Meses - " + motoristas.get(linha - 1).getNomeMotorista() + ".pdf");
                            cell1.setHyperlink(link12);
                        } else {
                            cell1.setCellStyle(styleNeutral);
                            cell1.setCellValue("Não gerado");
                        }
                        break;
                    case 10:
                        if (cincoAnosBool && !motoristas.get(linha - 1).getNomeMotorista().equals("Null")) {
                            cell1.setCellStyle(styleLink);
                            XSSFHyperlink link5 = (XSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_FILE);
                            String cincoAnos = diretorioPrincipal + "/Extratos 5 Anos/" + motoristas.get(linha - 1).getNomeMotorista() + ".pdf";
                            File filePDF5 = new File(cincoAnos);
                            System.out.println(filePDF5.toURI().toString());
                            link5.setAddress(filePDF5.toURI().toString());
                            cell1.setCellValue("Extrato 5 Anos - " + motoristas.get(linha - 1).getNomeMotorista() + ".pdf");
                            cell1.setHyperlink(link5);
                        } else {
                            cell1.setCellStyle(styleNeutral);
                            cell1.setCellValue("Não gerado");
                        }
                        break;
                    case 11:
                        if (completoBool && !motoristas.get(linha - 1).getNomeMotorista().equals("Null")) {
                            cell1.setCellStyle(styleLink);
                            XSSFHyperlink linkCompleto = (XSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_FILE);
                            String completo = diretorioPrincipal + "/Extratos Completos/" + motoristas.get(linha - 1).getNomeMotorista() + ".pdf";
                            File filePDFCompleto = new File(completo);
                            System.out.println(filePDFCompleto.toURI().toString());
                            linkCompleto.setAddress(filePDFCompleto.toURI().toString());
                            cell1.setCellValue("Extrato Completo - " + motoristas.get(linha - 1).getNomeMotorista() + ".pdf");
                            cell1.setHyperlink(linkCompleto);
                        } else {
                            cell1.setCellStyle(styleNeutral);
                            cell1.setCellValue("Não gerado");
                        }
                        break;
                    default:
                        System.out.println("Inválido");
                }
            }
        }
    }

    public void emRisco() {
        CreationHelper createHelper = workbook.getCreationHelper();
        try {
            for (int linha = 1; linha < (motoristas.size() + 1); linha++) {
                if (motoristas.get(linha - 1).getRisco().equals("SIM")) {

                    XSSFRow row1 = sheet.getRow(linha);

                    for (int coluna = 0; coluna < 12; coluna++) {

                        XSSFCell cell1 = row1.createCell(coluna);

                        switch (coluna) {
                            case 0:
                                cell1.setCellStyle(styleNeutral);
                                cell1.setCellValue(motoristas.get(linha - 1).getNomeMotorista());
                                break;
                            case 1:
                                cell1.setCellStyle(styleNeutral);
                                cell1.setCellValue(motoristas.get(linha - 1).getCNH());
                                break;
                            case 2:
                                cell1.setCellStyle(styleNeutral);
                                cell1.setCellValue(motoristas.get(linha - 1).getCPF());
                                break;
                            case 3:
                                cell1.setCellStyle(styleNeutral);
                                cell1.setCellValue(motoristas.get(linha - 1).getValidadeCNH());
                                break;
                            case 4:
                                if (motoristas.get(linha - 1).getSituacao().equals("NORMAL")) {
                                    cell1.setCellStyle(styleNeutral);
                                    cell1.setCellValue(motoristas.get(linha - 1).getSituacao());
                                } else {
                                    cell1.setCellStyle(styleRed);
                                    cell1.setCellValue(motoristas.get(linha - 1).getSituacao());
                                }
                                break;
                            case 5:
                                if (motoristas.get(linha - 1).getFiscalizacao().equals("NÃO RECOLHER CNH")) {
                                    cell1.setCellStyle(styleNeutral);
                                    cell1.setCellValue(motoristas.get(linha - 1).getFiscalizacao());
                                } else {
                                    cell1.setCellStyle(styleRed);
                                    cell1.setCellValue(motoristas.get(linha - 1).getFiscalizacao());
                                }
                                break;
                            case 6:
                                cell1.setCellStyle(styleNeutral);
                                cell1.setCellValue(Arrays.toString(motoristas.get(linha - 1).getMultas()));
                                break;
                            case 7:
                                cell1.setCellStyle(styleNeutral);
                                cell1.setCellValue(Arrays.toString(motoristas.get(linha - 1).getPontosMultas()));
                                break;
                            case 8:
                                if (motoristas.get(linha - 1).getRisco().equals("SIM")) {
                                    cell1.setCellStyle(styleRed);
                                    cell1.setCellValue(motoristas.get(linha - 1).getRisco());
                                } else {
                                    cell1.setCellStyle(styleGreen);
                                    cell1.setCellValue(motoristas.get(linha - 1).getRisco());
                                }
                                break;
                            case 9:
                                if (dozeMesesBool) {
                                    cell1.setCellStyle(styleLink);
                                    XSSFHyperlink link12 = (XSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_FILE);
                                    String dozeMeses = diretorioPrincipal + "/Extratos 12 Meses/" + motoristas.get(linha - 1).getNomeMotorista() + ".pdf";
                                    File filePDF12 = new File(dozeMeses);
                                    System.out.println(filePDF12.toURI().toString());
                                    link12.setAddress(filePDF12.toURI().toString());
                                    cell1.setCellValue("Extrato 12 Meses - " + motoristas.get(linha - 1).getNomeMotorista() + ".pdf");
                                    cell1.setHyperlink(link12);
                                } else {
                                    cell1.setCellStyle(styleNeutral);
                                    cell1.setCellValue("Não gerado");
                                }
                                break;
                            case 10:
                                if (cincoAnosBool) {
                                    cell1.setCellStyle(styleLink);
                                    XSSFHyperlink link5 = (XSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_FILE);
                                    String cincoAnos = diretorioPrincipal + "/Extratos 5 Anos/" + motoristas.get(linha - 1).getNomeMotorista() + ".pdf";
                                    File filePDF5 = new File(cincoAnos);
                                    System.out.println(filePDF5.toURI().toString());
                                    link5.setAddress(filePDF5.toURI().toString());
                                    cell1.setCellValue("Extrato 5 Anos - " + motoristas.get(linha - 1).getNomeMotorista() + ".pdf");
                                    cell1.setHyperlink(link5);
                                } else {
                                    cell1.setCellStyle(styleNeutral);
                                    cell1.setCellValue("Não gerado");
                                }
                                break;
                            case 11:
                                if (completoBool) {
                                    cell1.setCellStyle(styleLink);
                                    XSSFHyperlink linkCompleto = (XSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_FILE);
                                    String completo = diretorioPrincipal + "/Extratos Completos/" + motoristas.get(linha - 1).getNomeMotorista() + ".pdf";
                                    File filePDFCompleto = new File(completo);
                                    System.out.println(filePDFCompleto.toURI().toString());
                                    linkCompleto.setAddress(filePDFCompleto.toURI().toString());
                                    cell1.setCellValue("Extrato Completo - " + motoristas.get(linha - 1).getNomeMotorista() + ".pdf");
                                    cell1.setHyperlink(linkCompleto);
                                } else {
                                    cell1.setCellStyle(styleNeutral);
                                    cell1.setCellValue("Não gerado");
                                }
                                break;
                            default:
                                System.out.println("Inválido");
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Não existem funcionários nessa situação!");
        }
    }

    public void irregulares() {
        CreationHelper createHelper = workbook.getCreationHelper();
        try {
            System.out.println("Entrou irregulares");
            for (int linha = 1; linha < (motoristas.size() + 1); linha++) {
                if (!motoristas.get(linha - 1).getSituacao().equals("NORMAL")) {//NÃO ESTA DETECTANDO O NORMAL<<<
                    System.out.println(motoristas.get(linha - 1).getSituacao());
                    XSSFRow row1 = sheet.getRow(linha);

                    for (int coluna = 0; coluna < 9; coluna++) {

                        XSSFCell cell1 = row1.createCell(coluna);

                        switch (coluna) {
                            case 0:
                                cell1.setCellStyle(styleNeutral);
                                cell1.setCellValue(motoristas.get(linha - 1).getNomeMotorista());
                                break;
                            case 1:
                                cell1.setCellStyle(styleNeutral);
                                cell1.setCellValue(motoristas.get(linha - 1).getCNH());
                                break;
                            case 2:
                                cell1.setCellStyle(styleNeutral);
                                cell1.setCellValue(motoristas.get(linha - 1).getCPF());
                                break;
                            case 3:
                                cell1.setCellStyle(styleNeutral);
                                cell1.setCellValue(motoristas.get(linha - 1).getValidadeCNH());
                                break;
                            case 4:
                                if (motoristas.get(linha - 1).getSituacao().equals("NORMAL")) {
                                    cell1.setCellStyle(styleNeutral);
                                    cell1.setCellValue(motoristas.get(linha - 1).getSituacao());
                                } else {
                                    cell1.setCellStyle(styleRed);
                                    cell1.setCellValue(motoristas.get(linha - 1).getSituacao());
                                }
                                break;
                            case 5:
                                if (motoristas.get(linha - 1).getFiscalizacao().equals("NÃO RECOLHER CNH")) {
                                    cell1.setCellStyle(styleNeutral);
                                    cell1.setCellValue(motoristas.get(linha - 1).getFiscalizacao());
                                } else {
                                    cell1.setCellStyle(styleRed);
                                    cell1.setCellValue(motoristas.get(linha - 1).getFiscalizacao());
                                }
                                break;
                            case 6:
                                cell1.setCellStyle(styleNeutral);
                                cell1.setCellValue(Arrays.toString(motoristas.get(linha - 1).getMultas()));
                                break;
                            case 7:
                                cell1.setCellStyle(styleNeutral);
                                cell1.setCellValue(Arrays.toString(motoristas.get(linha - 1).getPontosMultas()));
                                break;
                            case 8:
                                if (motoristas.get(linha - 1).getRisco().equals("SIM")) {
                                    cell1.setCellStyle(styleRed);
                                    cell1.setCellValue(motoristas.get(linha - 1).getRisco());
                                } else {
                                    cell1.setCellStyle(styleGreen);
                                    cell1.setCellValue(motoristas.get(linha - 1).getRisco());
                                }
                                break;
                            case 9:
                                if (dozeMesesBool) {
                                    cell1.setCellStyle(styleLink);
                                    XSSFHyperlink link12 = (XSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_FILE);
                                    String dozeMeses = diretorioPrincipal + "/Extratos 12 Meses/" + motoristas.get(linha - 1).getNomeMotorista() + ".pdf";
                                    File filePDF12 = new File(dozeMeses);
                                    System.out.println(filePDF12.toURI().toString());
                                    link12.setAddress(filePDF12.toURI().toString());
                                    cell1.setCellValue("Extrato 12 Meses - " + motoristas.get(linha - 1).getNomeMotorista() + ".pdf");
                                    cell1.setHyperlink(link12);
                                } else {
                                    cell1.setCellStyle(styleNeutral);
                                    cell1.setCellValue("Não gerado");
                                }
                                break;
                            case 10:
                                if (cincoAnosBool) {
                                    cell1.setCellStyle(styleLink);
                                    XSSFHyperlink link5 = (XSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_FILE);
                                    String cincoAnos = diretorioPrincipal + "/Extratos 5 Anos/" + motoristas.get(linha - 1).getNomeMotorista() + ".pdf";
                                    File filePDF5 = new File(cincoAnos);
                                    System.out.println(filePDF5.toURI().toString());
                                    link5.setAddress(filePDF5.toURI().toString());
                                    cell1.setCellValue("Extrato 5 Anos - " + motoristas.get(linha - 1).getNomeMotorista() + ".pdf");
                                    cell1.setHyperlink(link5);
                                } else {
                                    cell1.setCellStyle(styleNeutral);
                                    cell1.setCellValue("Não gerado");
                                }
                                break;
                            case 11:
                                if (completoBool) {
                                    cell1.setCellStyle(styleLink);
                                    XSSFHyperlink linkCompleto = (XSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_FILE);
                                    String completo = diretorioPrincipal + "/Extratos Completos/" + motoristas.get(linha - 1).getNomeMotorista() + ".pdf";
                                    File filePDFCompleto = new File(completo);
                                    System.out.println(filePDFCompleto.toURI().toString());
                                    linkCompleto.setAddress(filePDFCompleto.toURI().toString());
                                    cell1.setCellValue("Extrato Completo - " + motoristas.get(linha - 1).getNomeMotorista() + ".pdf");
                                    cell1.setHyperlink(linkCompleto);
                                } else {
                                    cell1.setCellStyle(styleNeutral);
                                    cell1.setCellValue("Não gerado");
                                }
                                break;
                            default:
                                System.out.println("Inválido");
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Não existem funcionários nessa situação!");
        }
    }
}
