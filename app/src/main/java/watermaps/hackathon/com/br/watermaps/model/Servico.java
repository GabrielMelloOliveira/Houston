package watermaps.hackathon.com.br.watermaps.model;

/**
 * Created by User on 30/04/2017.
 */

public class Servico {

    private String tipoServico;
    private double precoServico;
    private boolean fornecedor;
    private double quantidadeAgua;
    private int nota;

    public Servico(String tipoServico, double precoServico, boolean fornecedor, double quantidadeAgua) {
        this.tipoServico = tipoServico;
        this.precoServico = precoServico;
        this.fornecedor = fornecedor;
        this.quantidadeAgua = quantidadeAgua;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }

    public double getPrecoServico() {
        return precoServico;
    }

    public void setPrecoServico(double precoServico) {
        this.precoServico = precoServico;
    }

    public boolean isFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(boolean fornecedor) {
        this.fornecedor = fornecedor;
    }

    public double getQuantidadeAgua() {
        return quantidadeAgua;
    }

    public void setQuantidadeAgua(double quantidadeAgua) {
        this.quantidadeAgua = quantidadeAgua;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "Tipo de serviço: " + tipoServico + " - Preço: R$" + precoServico;
    }
}
