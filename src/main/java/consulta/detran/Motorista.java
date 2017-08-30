/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consulta.detran;

import java.util.Arrays;
import org.jsoup.nodes.Document;

/**
 *
 * @author Torres
 */
public class Motorista {

    private Document paginaUsuario;//Sessão com autenticação
    private String nomeMotorista = "Null";
    private String CNH = "Null";
    private String CPF = "Null";
    private String validadeCNH = "Null";
    private String multas[] = {"Null"};
    private String pontosMultas[] = {"Null"};
    private String situacao = "Null";
    private String fiscalizacao = "Null";
    private String risco = "Null";
    private int pontuacaoTotal;

//    public Motorista(String CNH, String CPF, String validadeCNH, String situacao, String fiscalizacao, String[] multas, String[] pontosMultas, int pontuacaoTotal, String risco) {
    public Motorista(String nomeMotorista, String CNH, String CPF, String validadeCNH, String situacao, String fiscalizacao, String[] multas, String[] pontosMultas, int pontuacaoTotal, String risco) {

        this.nomeMotorista = nomeMotorista;
        this.CNH = CNH;
        this.CPF = CPF;
        this.validadeCNH = validadeCNH;
        this.multas = multas;
        this.pontosMultas = pontosMultas;
        this.situacao = situacao;
        this.fiscalizacao = fiscalizacao;
        this.pontuacaoTotal = pontuacaoTotal;
        this.risco = risco;

    }

    public Motorista(String CNH, String CPF, String validadeCNH) {
//    public Motorista(String nomeMotorista, String CNH, String CPF, String validadeCNH) {
//        this.nomeMotorista = nomeMotorista;
        this.CNH = CNH;
        this.CPF = CPF;
        this.validadeCNH = validadeCNH;

    }

    public void printDados() {

        System.out.println(nomeMotorista);
        System.out.println(CNH);
        System.out.println(CPF);
        System.out.println(validadeCNH);
        System.out.println(situacao.replaceAll("\n", ""));
        System.out.println(fiscalizacao);
        System.out.println(Arrays.toString(multas));
        System.out.println(Arrays.toString(pontosMultas));
        System.out.println(pontuacaoTotal);
        System.out.println("=================================");

    }

    public String getRisco() {
        return risco;
    }

    public void setRisco(String risco) {
        this.risco = risco;
    }

    public Document getPaginaUsuario() {
        return paginaUsuario;
    }

    public void setPaginaUsuario(Document paginaUsuario) {
        this.paginaUsuario = paginaUsuario;
    }

    public String getNomeMotorista() {
        return nomeMotorista;
    }

    public void setNomeMotorista(String nomeMotorista) {
        this.nomeMotorista = nomeMotorista;
    }

    public String[] getMultas() {
        return multas;
    }

    public void setMultas(String[] multas) {
        this.multas = multas;
    }

    public String[] getPontosMultas() {
        return pontosMultas;
    }

    public void setPontosMultas(String[] pontosMultas) {
        this.pontosMultas = pontosMultas;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getFiscalizacao() {
        return fiscalizacao;
    }

    public void setFiscalizacao(String fiscalizacao) {
        this.fiscalizacao = fiscalizacao;
    }

    public int getPontuacaoTotal() {
        return pontuacaoTotal;
    }

    public void setPontuacaoTotal(int pontuacaoTotal) {
        this.pontuacaoTotal = pontuacaoTotal;
    }

    public String getCNH() {
        return CNH;
    }

    public String getCPF() {
        return CPF;
    }

    public String getValidadeCNH() {
        return validadeCNH;
    }

}
