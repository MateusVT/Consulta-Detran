package consulta.detran;

import com.DeathByCaptcha.AccessDeniedException;
import com.DeathByCaptcha.Captcha;
import com.DeathByCaptcha.Exception;
import com.DeathByCaptcha.SocketClient;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;
import javax.xml.parsers.ParserConfigurationException;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Element;

/**
 *
 * @author Torres
 */
public class ParserAcess {

    public static final String URL_MAIN = "http://www.extratodebito.detran.pr.gov.br/detranextratos/gerarExtratoPontuacao.do?action=viewExtract";
    public static final String URL_CAPTCHA = "http://www.extratodebito.detran.pr.gov.br/detranextratos/jcaptcha.jpg?rndm=0000000000";
    public static final String URL_EXTRATO12 = "http://www.extratodebito.detran.pr.gov.br/detranextratos/gerarExtratoPontuacao.do?action=imprimirPorAba&indiceAba=1";
    public static final String URL_EXTRATO5 = "http://www.extratodebito.detran.pr.gov.br/detranextratos/gerarExtratoPontuacao.do?action=imprimirPorAba&indiceAba=2";
    public static final String URL_EXTRATOCOMPLETO = "http://www.extratodebito.detran.pr.gov.br/detranextratos/gerarExtratoPontuacao.do?action=imprimir";

    Connection.Response urlLogin = null;//Contém a conexão efetuada no primeiro acesso, usa-se o cookie na extração do captcha. 
    Response resultImageResponse = null;//Usado para isolar a imagem do captcha

    //Dados de entrada do programa
    private String CNH;
    private String CPF;
    private String validadeCNH;
    private String captcha;

    //Autenticação do programa
    private String login;
    private String senha;

    //Diretórios
    private String dirSelectedFile;//"C:\Users\Torres\Desktop\dados.xlsx"
    private String dirGeralFile;//"C:\Users\Torres\Desktop"

    SocketClient client;
    Captcha captchaSolver;
    private int captchaSolvedControl = 0;

    String regularExpressionToInt = "[^\\d.]+|\\.(?!\\d)";

    //Armazenam os documentos extraidos
    Document paginaPrincipal = null;//Sem autenticação
    Document paginaUsuario = null;//Sessão com autenticação
    Document tab12Meses = null;//Aba pontuação 12 meses
    Document tab5anos = null;//Aba pontuação 12  meses

    Response extrato12Meses = null;//Imprimir Extrato 12 meses
    Response extrato5Anos = null;//Imprimir Extrato completo
    Response extratoCompleto = null;//Imprimir Extrato completo

    public ParserAcess(String login, String senha, String dirSelectedFile, String dirGeralFile, String CNH, String CPF, String validadeCNH) {

        this.login = login;
        this.senha = senha;

        this.CNH = CNH;
        this.CPF = CPF;
        this.validadeCNH = validadeCNH;

        this.dirSelectedFile = dirSelectedFile;
        this.dirGeralFile = dirGeralFile;

    }

    //Construtor para autenticação no programa.
    public ParserAcess(String login, String senha) {

        this.login = login;
        this.senha = senha;
    }

    //Construtor vazio para uso dos métodos criar Pasta.
    public ParserAcess() {
    }

    public Motorista run() throws IOException, ParserConfigurationException, MalformedURLException, Exception, InterruptedException {
                   Motorista motorista = new Motorista(CNH, CPF, validadeCNH);
            return motorista;
        }
    
    
//    public Motorista run() throws IOException, ParserConfigurationException, MalformedURLException, Exception, InterruptedException {
////        captchaSolverManually();
//// while para tentar interpretar o captcha até acertar
//        ValidadorCPF validador = new ValidadorCPF();
//        System.out.println(validaEntrada());
//        System.out.println("Teste : " + (validador.validaCPF(CPF) && (validaEntrada() == 0)));
//
//        if (validador.validaCPF(CPF) && (validaEntrada() == 0)) {
//            //ESTÁ DANDO NULL POINTER PORQ PRECISA FAZER O doACess antes de validar entrada.
//            while (captchaSolvedControl != 1) {
//                extrairCaptcha();//Extrai o captcha
////            gravarCaptcha();//Uso para conferir o processo de extração e resolução do captcha manualmente
//                captchaSolver();//Resolve o captcha
//                doAcess();//
//                verificaCaptcha();//
//            }
//
////        System.out.println(paginaUsuario);
//            getTab12Meses();
////        getNomeMotoristaReal();
//            getSituacao();
//            getFiscalizacao();
//            getTituloMultas();
//            getPontuacaoMultas();
//            getPontuacaoTotal();
////        criaPasta12Meses(dirGeralFile);
////        criaPastaCompleto(dirGeralFile);
////        gerarExtrato12Meses(dirGeralFile);
////        gerarExtratoCompleto(dirGeralFile);
//            Motorista motorista = new Motorista(getNomeMotoristaReal(), CNH, CPF, validadeCNH, getSituacao(), getFiscalizacao(), getTituloMultas(), getPontuacaoMultas(), getPontuacaoTotal(), getRisco());
////            Motorista motorista = new Motorista(CNH, CPF, validadeCNH, getSituacao(), getFiscalizacao(), getTituloMultas(), getPontuacaoMultas(), getPontuacaoTotal(), getRisco());
////        motorista.printDados();
//            return motorista;
//        } else {
//            System.out.println("Não entrou");
//            // 1 = CNH, 2 = CPF, 3 = VALIDADE, 4 = CAPTCHA
//            Motorista motorista = null;
//            switch (validaEntrada()) {
//                case 1:
//                    motorista = new Motorista("TESTE", "ERROR", CPF, validadeCNH, getSituacao(), getFiscalizacao(), getTituloMultas(), getPontuacaoMultas(), getPontuacaoTotal(), getRisco());
//                    break;
//                case 2:
//                    motorista = new Motorista("TESTE", CNH, "ERROR", validadeCNH, getSituacao(), getFiscalizacao(), getTituloMultas(), getPontuacaoMultas(), getPontuacaoTotal(), getRisco());
//                    break;
//                case 3:
//                    motorista = new Motorista("TESTE", CNH, CPF, "ERROR", getSituacao(), getFiscalizacao(), getTituloMultas(), getPontuacaoMultas(), getPontuacaoTotal(), getRisco());
//                    break;
//                case 4:
//                    motorista = new Motorista("TESTE", CNH, CPF, validadeCNH, getSituacao(), getFiscalizacao(), getTituloMultas(), getPontuacaoMultas(), getPontuacaoTotal(), getRisco());
//                    break;
//                case 5:
//                    motorista = new Motorista(CNH, CPF, validadeCNH);
//                    break;
//            }
//            return motorista;
//        }
//    }

    public void extrairCaptcha() throws IOException {

        try {

            urlLogin = Jsoup.connect(URL_MAIN)//Conecta a página principal (cookie)
                    .timeout(10 * 20000)
                    .method(Connection.Method.GET)
                    .execute();

        } catch (SocketException | SocketTimeoutException e) {
            System.out.println("Conexão com a internet perdida.\n");
        }

        //Extrai captcha da página
        try {

            resultImageResponse = Jsoup.connect(URL_CAPTCHA)
                    .cookies(urlLogin.cookies())
                    .timeout(10 * 10000)
                    .ignoreContentType(true).execute();
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Conexão perdida!");

        }

    }

    public void gravarCaptcha() throws FileNotFoundException, IOException {

        FileOutputStream outPutCaptcha = new FileOutputStream(new java.io.File(dirGeralFile + "/captcha.png"));
        outPutCaptcha.write(resultImageResponse.bodyAsBytes());// resultImageResponse.body() é onde a imagem está

    }

    public void captchaSolver() throws IOException, Exception, InterruptedException {

        client = new SocketClient(login, senha);//Autenticação do DeathByCaptcha
//        SocketClient client = new SocketClient("khelmo", "fluzao4torrada");//Autenticação do DeathByCaptcha
        try {
            captchaSolver = client.decode(resultImageResponse.bodyAsBytes());
            captcha = captchaSolver.text;
            System.out.println("Captcha : " + captcha);
        } catch (NullPointerException e) {
        }

    }

    public void captchaSolverManually() {

        System.out.println("Digite o Captcha : ");
        Scanner scanner = new Scanner(System.in);
        captcha = scanner.nextLine();

    }

    public void doAcess() throws IOException, ParserConfigurationException, Exception {

        try {
            //Pega o html da página depois de efetuar o método post
            paginaUsuario = (Document) Jsoup.connect(URL_MAIN)//Realiza a autenticação com os dados de entrada 
                    .timeout(10 * 20000)
                    .data("cookieexists", "true")
                    .data("cnh", CNH)
                    .data("tipoCnh", "2")// 1 = Sem foto, 2 = Com foto;
                    .data("cpf", CPF)
                    .data("validadeCnh", validadeCNH)
                    .data("senha", captcha)
                    .cookies(urlLogin.cookies())
                    .post();

        } catch (SocketException | SocketTimeoutException e) {
            System.out.println("Conexão com a internet perdida.\n");
        }
//        System.out.println(paginaUsuario.toString());
//        validaEntrada();

    }

    public void verificaCaptcha() throws IOException, Exception {

        String title = paginaUsuario.getElementsByTag("title").toString().replaceAll("<title>", "").replaceAll("</title>", "");

        if (title.equals("Detran PR - Consulta Pontuação")) {//Quando a página contém essa String a autenticação falhou
            captchaSolvedControl = 0;
            client.report(captchaSolver);
        }
        if (title.equals("Detran-PR ")) {
            captchaSolvedControl = 1;
        }
    }

    public int validaEntrada() throws IOException, Exception, InterruptedException {
        extrairCaptcha();
        captchaSolver();
        try {
            //Pega o html da página depois de efetuar o método post
            paginaUsuario = (Document) Jsoup.connect(URL_MAIN)//Realiza a autenticação com os dados de entrada 
                    .timeout(10 * 20000)
                    .data("cookieexists", "true")
                    .data("cnh", CNH)
                    .data("tipoCnh", "2")// 1 = Sem foto, 2 = Com foto;
                    .data("cpf", CPF)
                    .data("validadeCnh", validadeCNH)
                    .data("senha", captcha)
                    .cookies(urlLogin.cookies())
                    .post();

        } catch (SocketException | SocketTimeoutException e) {
            System.out.println("Conexão com a internet perdida.\n");
        }
//        System.out.println(paginaUsuario.toString());
        try {
            int indexWarning = paginaUsuario.toString().indexOf("icon_msg_aviso.png");
            String[] title = paginaUsuario.toString().substring(indexWarning, indexWarning + 95).replaceAll("icon_msg_aviso.png\"> ", "").split("</p>");
            if (title[0].contains("Dígito verificador da CNH inválido.")) {
                System.out.println("CNH");
                return 1;
            }
            if (title[0].contains("CPF informado difere do cadastrado para o condutor")) {
                System.out.println("CPF");
                return 2;
            }
            if (title[0].contains("Data de validade da CNH incorreta.")) {
                System.out.println("DATA");
                return 3;
            }
            if (title[0].contains("Código da imagem não confere!")) {
                System.out.println("CAPTCHA");
                return 4;
            }

            System.out.println(title[0]);
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            return 5;
        }

        return 0;
    }

    public void getTab12Meses() {

        //Indexes que definem o inicio e o fim do html da aba (Pontos 12 meses)
        int inicioDiv = paginaUsuario.toString().indexOf("Infrações ocorridas nos últimos 12 meses");
        int fimDiv = paginaUsuario.toString().indexOf("id=\"content-multas\">");

        String tabString = paginaUsuario.toString().substring(inicioDiv, fimDiv);//Aba "splitada"
        tab12Meses = Jsoup.parse(tabString);//Parser da aba para extração dos dados
    }

    public void getTab5Anos() {

        //Indexes que definem o inicio e o fim do html da aba (Pontos 12 meses)
        int inicioDiv = paginaUsuario.toString().indexOf("Infrações ocorridas nos últimos 5 anos");
        int fimDiv = paginaUsuario.toString().indexOf("id=\"content-multas\">");

        String tabString = paginaUsuario.toString().substring(inicioDiv, fimDiv);//Aba "splitada"
        tab5anos = Jsoup.parse(tabString);//Parser da aba para extração dos dados
    }

    public boolean autentication() throws IOException, FileNotFoundException, Exception, InterruptedException {
        extrairCaptcha();
        client = new SocketClient(login, senha);//Autenticação do DeathByCaptcha
        try {
            Captcha temp = client.decode(resultImageResponse.bodyAsBytes());
            return true;

        } catch (AccessDeniedException e) {
            return false;

        } catch (IOException e1) {
            return false;

        } catch (NullPointerException ne) {
            return false;
        }
    }

    public String getNomeMotoristaReal() {

        Element nome = paginaUsuario.select("div#extrato_condutor_nome > div").first();
        String nomeMotorista = nome.toString()
                .replaceAll("<div>\n" + " ", "")
                .replaceAll("</div>", "");
//                .replaceAll("\\P{Print}", "");//REMOVENDO STRING ESPECIAIS

        return nomeMotorista.substring(0, nomeMotorista.length() - 1);
    }

    public String getSituacao() {

        String situacaoMot;
        try {
            Element situacao = paginaUsuario.select("div#extrato_condutor_situacaocnh > div").first();
            situacaoMot = situacao.toString()
                    .replaceAll("<div>\n" + " ", "")
                    .replaceAll("<div>\n", "")
                    .replaceAll("</div>", "").trim();
//                .replaceAll("\\P{Print}", "");//R;EMOVENDO STRING ESPECIAIS
//        System.out.println(situacaoMot);
            return situacaoMot;
        } catch (NullPointerException e) {

            return "ERROR";
        }

    }

    public String getFiscalizacao() {

        String fiscalizacao;
        try {

            Element situacao = paginaUsuario.select("div#extrato_condutor_situacaocnh > div").last();

            fiscalizacao = situacao.toString()
                    .replaceAll("<div>\n" + " ", "")
                    .replaceAll("</div>", "")
                    .replaceAll("<font color=\"red\">", "")
                    .replaceAll("</font>", "");
//                .replaceAll("\\P{Print}", "");//REMOVENDO STRING ESPECIAIS

            return fiscalizacao.substring(0, fiscalizacao.length() - 1);
        } catch (NullPointerException e) {

            return "ERROR";
        }

    }

    public String[] getTituloMultas() {

//        try {
        //Encontra e separa os títulos das infrações em um array
        Elements multasElements = tab12Meses.select("td").select("a");
        String multas[] = multasElements.toString().replaceAll("<a><i class=\"list-icon fa fa-plus-circle\"></i>&nbsp;&nbsp;", "").split("</a>");

        if (multas.length == 1) {
            multas[0] = "Nada consta";
        }

        return multas;
//        } catch (NullPointerException e) {
//            String multas[] = null;
//            multas[0] = "Error";
//            return multas;
//        }

    }

    public String[] getPontuacaoMultas() {
        try {

            ///Encontra e separa os pontos das infrações em um array
            Elements pontosElements = tab12Meses.select("div#detalhes_infracoes_pontos > div");
            String pontosMultas[] = pontosElements.toString().replaceAll("<div>", "").replaceAll("\n", "").replaceAll(regularExpressionToInt, "").split("");

            if (pontosMultas.length == 1) {
                pontosMultas[0] = "Nada consta";
            }

            return pontosMultas;
        } catch (NullPointerException e) {
            String pontosMultas[] = null;
            pontosMultas[0] = "Error";
            return pontosMultas;
        }
    }

    public int getPontuacaoTotal() {

        String pontosMultas[] = getPontuacaoMultas();
        int pontuacaoTotal = 0, i = 0;
        Integer[] pontuacao = new Integer[pontosMultas.length];

        for (String ponto : pontosMultas) {
            try {
                pontuacao[i] = Integer.parseInt(ponto.trim());//Exception in this line
                pontuacaoTotal += pontuacao[i];
            } catch (NumberFormatException e) {
            }
            i++;
        }

        return pontuacaoTotal;

    }

    public String getRisco() {

        int pontuacaoTotal = getPontuacaoTotal();
        if (pontuacaoTotal >= 13) {
            return "SIM";
        } else {
            return "NÃO";
        }

    }

    public void criaPastaCompleto(String diretorio) {
        File file = new File(diretorio + "/Extratos Completos");
        file.mkdir();

    }

    public void criaPasta12Meses(String diretorio) {
        File file = new File(diretorio + "/Extratos 12 Meses");
        file.mkdir();

    }

    public void criaPasta5Anos(String diretorio) {
        File file = new File(diretorio + "/Extratos 5 Anos");
        file.mkdir();

    }

    public String criaPastaPrincipal(String diretorio) {
//        if (Files.exists(diretorio)) {
//            // ...
//        }
        DateFormat dateFormat = new SimpleDateFormat("(dd-MM-yyyy)");
        Date date = new Date();
        File file = new File(diretorio + "/Execução - " + dateFormat.format(date));
        file.mkdir();
        return "/Execução - " + dateFormat.format(date);
    }

    public void gerarExtratoCompleto(String diretorio) throws IOException {
        try {
            //Pega o html da página depois de efetuar o método post
            extratoCompleto = Jsoup.connect(URL_EXTRATOCOMPLETO)//Realiza a autenticação com os dados de entrada 
                    .timeout(10 * 20000)
                    .data("cookieexists", "true")
                    .data("cnh", CNH)
                    .data("cpf", CPF)
                    .data("tipoCnh", "2")// 1 = Sem foto, 2 = Com foto;
                    .data("validadeCnh", validadeCNH)
                    .cookies(urlLogin.cookies())
                    .ignoreContentType(true)
                    .execute();

        } catch (SocketException | SocketTimeoutException | NullPointerException e) {
            System.out.println("Conexão com a internet perdida.\n");
//            System.exit(0);
        }

        FileOutputStream out = new FileOutputStream(new java.io.File(diretorio + "/Extratos Completos" + "/" + getNomeMotoristaReal() + ".pdf"));
        out.write(extratoCompleto.bodyAsBytes());
        out.close();

    }

    public void gerarExtrato12Meses(String diretorio) throws IOException {

        try {
            extrato12Meses = Jsoup.connect(URL_EXTRATO12)//Realiza a autenticação com os dados de entrada 
                    .timeout(10 * 20000)
                    .data("cookieexists", "true")
                    .data("cnh", CNH)
                    .data("cpf", CPF)
                    .data("tipoCnh", "2")// 1 = Sem foto, 2 = Com foto;
                    .data("validadeCnh", validadeCNH)
                    .cookies(urlLogin.cookies())
                    .ignoreContentType(true)
                    .execute();
//                    .post();

        } catch (SocketException | SocketTimeoutException e) {
            System.out.println("Conexão com a internet perdida.\n");
//            System.exit(0);
        }

        FileOutputStream out = new FileOutputStream(new java.io.File(diretorio + "/Extratos 12 Meses" + "/" + getNomeMotoristaReal() + ".pdf"));
        out.write(extrato12Meses.bodyAsBytes());
        out.close();

    }

    public void gerarExtrato5Anos(String diretorio) throws IOException {

        try {
            extrato5Anos = Jsoup.connect(URL_EXTRATO5)//Realiza a autenticação com os dados de entrada 
                    .timeout(10 * 20000)
                    .data("cookieexists", "true")
                    .data("cnh", CNH)
                    .data("cpf", CPF)
                    .data("tipoCnh", "2")// 1 = Sem foto, 2 = Com foto;
                    .data("validadeCnh", validadeCNH)
                    .cookies(urlLogin.cookies())
                    .ignoreContentType(true)
                    .execute();

        } catch (SocketException | SocketTimeoutException e) {
            System.out.println("Conexão com a internet perdida.\n");
        }

        FileOutputStream out = new FileOutputStream(new java.io.File(diretorio + "/Extratos 5 Anos" + "/" + getNomeMotoristaReal() + ".pdf"));
        out.write(extrato5Anos.bodyAsBytes());
        out.close();

    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
