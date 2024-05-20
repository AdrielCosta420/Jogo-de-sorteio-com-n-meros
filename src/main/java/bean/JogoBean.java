package bean;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import java.util.Random;

import dao.JogoDAO;
import entities.Jogo;

@ManagedBean
public class JogoBean {
	private Jogo jogo = new Jogo();
	private List<Jogo> lista;
	private Integer maiorNumSorteadoTabela;
	private Integer maiorNumDasVariaveis;
	private String resultadoVerificacao;

	

	public String getResultadoVerificacao() {
		return resultadoVerificacao;
	}

	public void setResultadoVerificacao(String resultadoVerificacao) {
		this.resultadoVerificacao = resultadoVerificacao;
	}

	public String save() {
		if (jogo != null) {
			jogo.setData(new Date());
			Random random = new Random();
			jogo.setNumeroSorteado(random.nextInt(10) + 1);
			JogoDAO.save(jogo);
			jogo = new Jogo();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Jogo criado!"));
			lista = JogoDAO.listar();
			return "listagem.xhtml";

		}
		return null;
	}

	public String edit(Jogo jogoEdit) {
		if (jogoEdit != null) {
			JogoDAO.edit(jogoEdit);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Jogo atualizado!"));
			lista = JogoDAO.listar();
			return "listagem.xthml";
		}
		return null;
	}

	public String delete(int id) {

		JogoDAO.delete(id);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Jogo excluído!"));
		lista = JogoDAO.listar();
		return "listagem.xthml";

	}

	public List<Jogo> getLista() {
		if (lista == null) {
			lista = JogoDAO.listar();
		}

		return lista;
	}

	public void validateNumber(FacesContext context, UIComponent component, Object value) {
 		int number = (Integer) value;
		if (number < 1 || number > 10) {
			FacesMessage message = new FacesMessage("Número inválido: deve ser entre 1 e 10.");
			throw new ValidatorException(message);
		}
	}

	public Integer getMaxNumeroSorteado() {
		maiorNumSorteadoTabela = JogoDAO.findMaxNumeroSorteado();
		return maiorNumSorteadoTabela;
	}

	public Integer getMaxNumeroDasVariaveis(Jogo jogo) {
		maiorNumDasVariaveis = JogoDAO.findMaxNumeroDasVariaveis(jogo);
	System.out.println("maior num da var" + maiorNumDasVariaveis);
		return maiorNumDasVariaveis;
	}
	
	public void verificarNumeroSorteado(Jogo jogo, int numeroSorteado) {
	    if (numeroSorteado == jogo.getV1() || numeroSorteado == jogo.getV2() || numeroSorteado == jogo.getV3() || numeroSorteado == jogo.getV4() || numeroSorteado == jogo.getV5()) {
	        resultadoVerificacao = "sim";
	    } else {
	        resultadoVerificacao = "não";
	    }
	}

	public void setLista(List<Jogo> lista) {
		this.lista = lista;
	}

	public Jogo getJogo() {
		return jogo;
	}

	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}
	
	public Integer getMaiorNumDasVariaveis() {
		return maiorNumDasVariaveis;
	}

	public void setMaiorNumDasVariaveis(Integer maiorNumDasVariaveis) {
		this.maiorNumDasVariaveis = maiorNumDasVariaveis;
	}

	public Integer getMaiorNumSorteadoTabela() {
		return maiorNumSorteadoTabela;
	}

	public void setMaiorNumSorteadoTabela(Integer maiorNumSorteadoTabela) {
		this.maiorNumSorteadoTabela = maiorNumSorteadoTabela;
	}

}
