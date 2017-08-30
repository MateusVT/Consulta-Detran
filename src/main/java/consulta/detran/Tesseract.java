/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consulta.detran;

import java.io.File;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

/**
 *
 * @author Torres
 */
public class Tesseract {

    public void captchaSolver() {
        File imageFile = new File("C:\\Users\\Torres\\Desktop\\captcha\\jcaptcha (7).jpg");
        try {
            ITesseract instance = new Tesseract1();
            File tessDataFolder = LoadLibs.extractTessResources("tessdata");
            instance.setDatapath(tessDataFolder.getAbsolutePath());
            String result = instance.doOCR(imageFile);
            System.out.println("Resultado : " + result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
