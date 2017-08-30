/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consulta.detran;

import com.DeathByCaptcha.Exception;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 *
 * @author Torres
 */
public class AutenticationType {

    List<Motorista> motoristas = new ArrayList<>();//Armazena os dados dos motoristas
    LoginsSpreadSheetReader autenticacao = new LoginsSpreadSheetReader();

    public void lePlanilha(JFileChooser chooser) throws IOException {

        autenticacao.lePlanilhaLogins(chooser.getSelectedFile().toString());

    }

    public void multAcess(String login, String senha, JFileChooser chooser) throws IOException, ParserConfigurationException, FileNotFoundException, InvalidFormatException, MalformedURLException, Exception, InterruptedException {

//        System.out.println("Start!");
        int i = 0;

        while (i < autenticacao.getMotoristas().size()) {

            ParserAcess parser = new ParserAcess(
                    login,
                    senha,
                    chooser.getSelectedFile().toString(),
                    chooser.getCurrentDirectory().toString(),
                    autenticacao.getMotoristas().get(i).getCNH(),
                    autenticacao.getMotoristas().get(i).getCPF(),
                    autenticacao.getMotoristas().get(i).getValidadeCNH());//Cria uma lista os valores extraidos

            motoristas.add(parser.run());
            i++;
        }

//        SpreadSheetGenerator gerador = new SpreadSheetGenerator();//Escreve os dados na planilha
//        gerador.geraPlanilha(chooser.getCurrentDirectory().toString(), motoristas);
        motoristas.removeAll(motoristas);
    }

    public List<Motorista> getMotoristas() {
        return motoristas;
    }
}
