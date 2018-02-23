package com.camd.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import com.camd.utils.GenerateValidation;

import com.camd.business.XparcelaBusiness;
import com.camd.model.Anotacao;
import com.camd.model.ParcelaR;
import com.camd.model.Taxas;
import com.camd.model.Xparcela;


import java.util.HashMap;
import java.util.Map;
import org.primefaces.context.RequestContext;



@ManagedBean(name = "xparcela")
@ViewScoped 
public class XparcelaBean {


	private List<Xparcela> listaParcela;
	private List<Xparcela> parcelaSelecionadas;
	private List<Xparcela> listaParcelaBkp = new ArrayList<>();
	private Xparcela parcelaSelecionada;
	private DateTimeFormatter dtfPadrao = DateTimeFormat.forPattern("dd/MM/yyyy");
	private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	private Taxas multa2Perc;
	private Taxas taxa0033Perc;
	private Taxas custasBL;
	private double total = 0.00;
	private double totalSemJuros = 0.00;
	private DateTime dataA;
	private String dataAte = formato.format(new Date());
	private Date dataAte2 = new Date();
	private DateTime dataB;
	private Days dias;
	private FacesContext fc;
	private ExternalContext ex;
	private HttpSession session;
	private double totalVparcelas;
	private double valorEntrada = 0.00;
	private String dataEntrada;
	//private Date dataEntrada1;
	private double valorDiferenca = 0.00;
	private int xVezes;
	private List<ParcelaR> parcelasGeradas;
	private double valorLiquido = 0.00;
	private String vencimentoBoleto;
	private String areaAnotacao;
	private List<Anotacao> listNota;
	private FacesMessage msg;
	private List<Taxas> t;
	private String idProcesso;

	private String login ;

	@PostConstruct
	public void init() {
		
		
	//	external = context.getExternalContext();
	//	login = external.getRemoteUser();
	
		System.out.println("estou no init parcelas");

		fc = FacesContext.getCurrentInstance();
		ex = fc.getExternalContext();
		session = (HttpSession) fc.getExternalContext().getSession(false);
		listaParcela = (List<Xparcela>) session.getAttribute("parcelasC");
		login = ex.getRemoteUser();

		getTaxas();

		calculaValores();
	
		BuscaAnotacao();
	}
	
	
	public void deletePar(){
		
	listaParcelaBkp.addAll(listaParcela);
		
				
		listaParcela.remove(parcelaSelecionada);
		msg = new FacesMessage("Linha apagada!"," ");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	
	
	public void calculaSelecionada() {
		valorDiferenca = 0;
		valorEntrada = 0;
		totalSemJuros = 0;
		totalVparcelas = 0;
		xVezes = 0;
try {
	parcelasGeradas.clear();
} catch (Exception e) {
	// TODO: handle exception
}
			
		
		
		for (Xparcela xp : parcelaSelecionadas) {
			totalVparcelas += xp.getTotalJuros();
			totalSemJuros += xp.getValor();
		}
		valorDiferenca = totalVparcelas;
		
	}
	
	
	
	
	//--------vai para a tela de inicio-------------------------
	public String vaiparaCliente() {
		System.out.println("vai para");

		return "/user_common/principal";
	}
	
	//--------vai para a tela de negociações pendentes-------------------------
	public String vaiparaRepPendente() {
		System.out.println("vai para");

		return "/user_common/reneg_pendentes";
	}
	
	
	//---------Salva anotações da renegociação-------------------
	
	public void salvaAnotacao() {
	
			XparcelaBusiness xpb = new XparcelaBusiness();
			listNota = new ArrayList<>();
			Anotacao n = new Anotacao(); 		
	    	n.setTexto(areaAnotacao);
	    	n.setCliente(listaParcela.get(0).getCliente());
	    	n.setData(new Date());
	    	n.setUsuario(ex.getRemoteUser());
	    	listNota.add(n);
	    	xpb.salvaAnotacao(listNota);
	    	areaAnotacao = "";
			listNota = xpb.atualizaNota(listaParcela.get(0).getCliente());
			msg = new FacesMessage("Anotação Salva"," ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		
		
	}
	
	//-------Busca Anotações-------------------------------------
	public void BuscaAnotacao() {
		XparcelaBusiness xpb = new XparcelaBusiness();
		listNota = xpb.atualizaNota(listaParcela.get(0).getCliente());
		
	}
	
	//--------Salva parcelas Regeradas----------------------------
	public void salvaParcelaR() {
		
		XparcelaBusiness xpb = new XparcelaBusiness();
		xpb.saveParcelaR(parcelasGeradas);
		areaAnotacao = "Renegociação realizada apartir dos boletos--> ";
		for (Xparcela lParcela : parcelaSelecionadas) {
			areaAnotacao += lParcela.getIdboleto() + " - ";
			
			lParcela.setRenegociada(true);
			lParcela.setIdProcesso(idProcesso);
			xpb.save(lParcela);
		}
		salvaAnotacao();
		xpb.AtualizarValorTaxa(t);
		if (!listaParcelaBkp.isEmpty()) {
			listaParcelaBkp.removeAll(parcelaSelecionadas);
			listaParcela = listaParcelaBkp;
		}else {
			listaParcela.removeAll(parcelaSelecionadas);
		}
			
		
		
		

		
		parcelasGeradas.clear();
		valorDiferenca = 0;
		valorEntrada = 0;
		totalSemJuros = 0;
		totalVparcelas = 0;
		
		msg = new FacesMessage("Salvo","O parcelamento foi salvo!");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		//return "/user_common/reneg_pendentes";
	}

	// --------Calcula as taxas de juros a serem exibidas na
	// tabela2-------------------------------
	private void calculaValores() {
		dataEntrada = dataAte;
		System.out.println("dataAte " + dataAte);
		System.out.println("taxa taxa0033Perc "+ taxa0033Perc.getPercentual());
		System.out.println("taxa custasBL "+ custasBL.getPercentual());
		System.out.println("taxa multa2Perc "+ multa2Perc.getPercentual());

		dataA = DateTime.parse(dataAte, dtfPadrao);

		totalVparcelas = 0;
		totalSemJuros = 0;
		
		
		
		for (Xparcela xp : listaParcela) {
			Taxas multaPerc = new Taxas(multa2Perc.getNome());
			Taxas custasDevBoleto = new Taxas(custasBL.getNome());
			Taxas jurosMora = new Taxas(taxa0033Perc.getNome());

			multaPerc.setValor(xp.getValor() * multa2Perc.getPercentual() / 100);
			custasDevBoleto.setValor(custasBL.getPercentual());
			dataB = DateTime.parse(xp.getVencimento().toString().substring(0, 10));
			dias = Days.daysBetween(dataB, dataA);
			xp.setDiasAtraso(dias.getDays());
			jurosMora.setValor((xp.getValor() * taxa0033Perc.getPercentual() / 100) * xp.getDiasAtraso());

			total = new Double(
					multaPerc.getValor() + jurosMora.getValor() + custasDevBoleto.getValor() + xp.getValor());
			xp.setTotalJuros(total);
			xp.getTaxas().clear();
			xp.setTaxas(multaPerc);
			xp.setTaxas(jurosMora);
			xp.setTaxas(custasDevBoleto);
								
		
		}
		
	}

	// ------------Atualiza taxas utilizadas no calculo tabela2--------------------
	public void getTaxas() {

		XparcelaBusiness xparcelaBusiness = new XparcelaBusiness();
		t = xparcelaBusiness.getListaTaxas();
		for (Taxas taxa : t) {
			if (taxa.getCod() == 1) {
				multa2Perc = taxa;
			}
			if (taxa.getCod() == 2) {
				taxa0033Perc = taxa;

			}
			if (taxa.getCod() == 3) {
				custasBL = taxa;
			}
		}

	}

	// ----------recalcula as novas parcelas geradas----------------------------
	public void calculaParcelasR() {

		DateTime data = DateTime.parse(dataEntrada, dtfPadrao);
		idProcesso = GenerateValidation.keyValidation();
		parcelasGeradas = new ArrayList<>();
		if (valorEntrada > 0) {
			valorDiferenca = totalVparcelas - valorEntrada;
		}
		
		if (xVezes > 0 && valorDiferenca > 0 ) {
			valorLiquido = valorDiferenca / xVezes;

			for (int i = 0; i <= xVezes; i++) {
				ParcelaR paR = new ParcelaR();
				if (i == 0 && valorEntrada >0) {

					paR.setValor(valorEntrada);
					paR.setCcusto(parcelaSelecionadas.get(0).getCcusto());
					paR.setCliente(parcelaSelecionadas.get(0).getCliente());
					paR.setTipo(parcelaSelecionadas.get(0).getClassFinanceira());
					paR.setStatus("Pendente");
					paR.setIdProcesso(idProcesso);
					paR.setValorMaisTaxa(valorEntrada + custasBL.getPercentual());
					paR.setCriacao(new Date());
					paR.setUsuarioCriacao(login);
					
					DateTime dataMaisDias = DateTime.parse(dataEntrada, dtfPadrao);
					try {

						paR.setVencimento(formato.parse(dataMaisDias.toString(dtfPadrao).toString()));
						data = dataMaisDias;
					} catch (ParseException e) {

						e.printStackTrace();
					}
					if (valorEntrada > 0) {
						parcelasGeradas.add(paR);
					}

				} else if (i == 0) {

					paR.setValor(valorLiquido);
					paR.setCcusto(parcelaSelecionadas.get(0).getCcusto());
					paR.setCliente(parcelaSelecionadas.get(0).getCliente());
					paR.setValorMaisTaxa(valorLiquido + custasBL.getPercentual());
					paR.setTipo(parcelaSelecionadas.get(0).getClassFinanceira());
					paR.setStatus("Pendente");
					paR.setIdProcesso(idProcesso);
					paR.setCriacao(new Date());
					paR.setUsuarioCriacao(login);
					DateTime dataMaisDias = data;
					try {

						paR.setVencimento(formato.parse(dataMaisDias.toString(dtfPadrao).toString()));
						data = dataMaisDias;
					} catch (ParseException e) {

						e.printStackTrace();
					}
					parcelasGeradas.add(paR);
					i++;
				} else if (i>0) {
					paR.setValor(valorLiquido);
					paR.setCcusto(parcelaSelecionadas.get(0).getCcusto());
					paR.setCliente(parcelaSelecionadas.get(0).getCliente());
					paR.setValorMaisTaxa(valorLiquido + custasBL.getPercentual());
					paR.setTipo(parcelaSelecionadas.get(0).getClassFinanceira());
					paR.setStatus("Pendente");
					paR.setIdProcesso(idProcesso);
					paR.setCriacao(new Date());
					paR.setUsuarioCriacao(login);
					DateTime dataMaisDias = data.plusMonths(1);
					try {

						paR.setVencimento(formato.parse(dataMaisDias.toString(dtfPadrao).toString()));
						data = dataMaisDias;
					} catch (ParseException e) {

						e.printStackTrace();
					}
					parcelasGeradas.add(paR);

				}

			}
		}  if(valorDiferenca >0 && xVezes < 1){
			xVezes = 1;
			System.out.println("aki aki");
			valorLiquido = valorDiferenca / xVezes;

			for (int i = 0; i <= xVezes; i++) {
				ParcelaR paR = new ParcelaR();
				if (i == 0 && valorEntrada >=1) {
					
					paR.setValor(valorEntrada);
					paR.setCcusto(parcelaSelecionadas.get(0).getCcusto());
					paR.setCliente(parcelaSelecionadas.get(0).getCliente());
					paR.setTipo(parcelaSelecionadas.get(0).getClassFinanceira());
					paR.setStatus("Pendente");
					paR.setIdProcesso(idProcesso);
					if (xVezes == 1) {
						paR.setValorMaisTaxa(valorEntrada );
					}else {
					paR.setValorMaisTaxa(valorEntrada + custasBL.getPercentual());
					}
					paR.setCriacao(new Date());
					paR.setUsuarioCriacao(login);
					
					DateTime dataMaisDias = DateTime.parse(dataEntrada, dtfPadrao);
					try {

						paR.setVencimento(formato.parse(dataMaisDias.toString(dtfPadrao).toString()));
						data = dataMaisDias;
					} catch (ParseException e) {

						e.printStackTrace();
					}
					if (valorEntrada > 0) {
						parcelasGeradas.add(paR);
					}

				} else if (i == 0) {

					paR.setValor(valorLiquido);
					paR.setCcusto(parcelaSelecionadas.get(0).getCcusto());
					paR.setCliente(parcelaSelecionadas.get(0).getCliente());
					if (xVezes == 1) {
						paR.setValorMaisTaxa(valorLiquido);
					}else {
					paR.setValorMaisTaxa(valorLiquido + custasBL.getPercentual());
					}
					paR.setTipo(parcelaSelecionadas.get(0).getClassFinanceira());
					paR.setStatus("Pendente");
					paR.setIdProcesso(idProcesso);
					paR.setCriacao(new Date());
					paR.setUsuarioCriacao(login);
					DateTime dataMaisDias = data;
					try {

						paR.setVencimento(formato.parse(dataMaisDias.toString(dtfPadrao).toString()));
						data = dataMaisDias;
					} catch (ParseException e) {

						e.printStackTrace();
					}
					parcelasGeradas.add(paR);
					i++;
				} else if (i>0) {
					paR.setValor(valorLiquido);
					paR.setCcusto(parcelaSelecionadas.get(0).getCcusto());
					paR.setCliente(parcelaSelecionadas.get(0).getCliente());
					if (xVezes == 1) {
						paR.setValorMaisTaxa(valorLiquido);
					}else {
					paR.setValorMaisTaxa(valorLiquido + custasBL.getPercentual());
					}
					paR.setTipo(parcelaSelecionadas.get(0).getClassFinanceira());
					paR.setStatus("Pendente");
					paR.setIdProcesso(idProcesso);
					paR.setCriacao(new Date());
					paR.setUsuarioCriacao(login);
					DateTime dataMaisDias = data.plusMonths(1);
					try {

						paR.setVencimento(formato.parse(dataMaisDias.toString(dtfPadrao).toString()));
						data = dataMaisDias;
					} catch (ParseException e) {

						e.printStackTrace();
					}
					parcelasGeradas.add(paR);

				}

			}
		
		
		} if(valorEntrada>0 && valorDiferenca < 1 ){
			xVezes=0;
			ParcelaR paR = new ParcelaR();
			paR.setValor(valorEntrada);
			paR.setCcusto(parcelaSelecionadas.get(0).getCcusto());
			paR.setCliente(parcelaSelecionadas.get(0).getCliente());
			paR.setValorMaisTaxa(valorEntrada );
			paR.setTipo(parcelaSelecionadas.get(0).getClassFinanceira());
			paR.setStatus("Pendente");
			paR.setIdProcesso(idProcesso);
			paR.setCriacao(new Date());
			paR.setUsuarioCriacao(login);
			DateTime dataMaisDias = DateTime.parse(dataEntrada, dtfPadrao);
			try {

				paR.setVencimento(formato.parse(dataMaisDias.toString(dtfPadrao).toString()));
				data = dataMaisDias;
			} catch (ParseException e) {

				e.printStackTrace();
			}
			if (valorEntrada > 0) {
				parcelasGeradas.add(paR);
			}

		}
			//session.setAttribute("parcelaGeradas", parcelasGeradas);
	/*	valorDiferenca=0;
		valorEntrada = totalVparcelas;
		xVezes = 0;*/
	}

	// ---------Metodo do Evento Ajax-------------------------
	// tabela2------calcula dias de vencimentos e aceiona metodo
	// recalcular-----------------------


	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();
		calculaValores();
		if (newValue != null && !newValue.equals(oldValue)) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atualizado!",
					"De: " + oldValue + ", Para:" + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	


	//---------edita valor das parcelas Geradas------------------
	public void onCellEditPar(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		
		
		if (newValue != null && !newValue.equals(oldValue)) {
			
			if (parcelasGeradas.size()>1) {
				int i=event.getRowIndex();
				int j;
				double valorT;
				double valorP;
				if (valorEntrada < 1 && xVezes > 1 ) {
					valorT = (totalVparcelas + (xVezes * custasBL.getPercentual())) - ((Double)newValue);
					j=0;
					valorP = valorT / (xVezes -1);
					for ( ; j < parcelasGeradas.size(); j++) {
						if (j!=i) {
							
							parcelasGeradas.get(j).setValorMaisTaxa(valorP);
							parcelasGeradas.get(j).setValor(valorP - (custasBL.getPercentual()) );
						}else {
							
							parcelasGeradas.get(j).setValor(((Double)newValue) - custasBL.getPercentual());
						}
						
							
							
						}	
					
					msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atualizado!",
							"De: " + oldValue + ", Para:" + newValue);
					FacesContext.getCurrentInstance().addMessage(null, msg);
					
				}else if(valorEntrada < 1 && xVezes < 2 || valorEntrada > 1 && xVezes < 2){
					calculaParcelasR();
					msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atualizado!",
							"Recalculado! ");
					FacesContext.getCurrentInstance().addMessage(null, msg);
					
				}else if (valorEntrada>1 && i==0 ) {
					
					
					valorT = (((totalVparcelas + ((xVezes + 1) * custasBL.getPercentual())) - ((Double)newValue)));
					j=1;
					parcelasGeradas.get(i).setValor(((Double)newValue) - custasBL.getPercentual());
					valorP = (valorT / (xVezes));
					
					for ( ; j < parcelasGeradas.size(); j++) {
						if (j!=i) {
							
							parcelasGeradas.get(j).setValorMaisTaxa(valorP);
							parcelasGeradas.get(j).setValor(valorP - (custasBL.getPercentual()) );
						}else {
							
							parcelasGeradas.get(j).setValor(((Double)newValue) - custasBL.getPercentual());
						}
						
							
							
						}	
					msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atualizado!",
							"De: " + oldValue + ", Para:" + newValue);
					FacesContext.getCurrentInstance().addMessage(null, msg);
				
				
				}else {
					
					System.out.println("oi Else");
					
					valorT = (((totalVparcelas + ((xVezes) * custasBL.getPercentual())) - ((Double)newValue)) - valorEntrada);
					j=1;
					valorP = valorT / (xVezes-1);
					for ( ; j < parcelasGeradas.size(); j++) {
						if (j!=i) {
							
							parcelasGeradas.get(j).setValorMaisTaxa(valorP);
							parcelasGeradas.get(j).setValor(valorP - (custasBL.getPercentual()) );
						}else {
							
							parcelasGeradas.get(j).setValor(((Double)newValue) - custasBL.getPercentual());
						}
						
							
							
						}	
					msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atualizado!",
							"De: " + oldValue + ", Para:" + newValue);
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
				

			}else {
				
				calculaParcelasR();
				
			}

		}

	}

	
    public void dataEntrada(SelectEvent event) {
        
        dataEntrada = formato.format(event.getObject());

    }
    
    public void dataAte(SelectEvent event) {
       
        dataAte = formato.format(event.getObject());
        dataA = DateTime.parse(dataAte, dtfPadrao);

		calculaValores();
		msg = new FacesMessage("Recalculado para data", dataAte);
		FacesContext.getCurrentInstance().addMessage(null, msg);
    	
      
    }
	
	

	// -------------------get&Set-----------------------------------------------------------

	
	public List<Xparcela> getParcelaSelecionadas() {
		return parcelaSelecionadas;
	}


	public Xparcela getParcelaSelecionada() {
		return parcelaSelecionada;
	}


	public void setParcelaSelecionada(Xparcela parcelaSelecionada) {
		this.parcelaSelecionada = parcelaSelecionada;
	}


	public void setParcelaSelecionadas(List<Xparcela> parcelaSelecionadas) {
		this.parcelaSelecionadas = parcelaSelecionadas;
	}

    
    
	public double getTotalVparcelas() {
		return totalVparcelas;
	}



	public String getAreaAnotacao() {
		return areaAnotacao;
	}


	public void setAreaAnotacao(String areaAnotacao) {
		this.areaAnotacao = areaAnotacao;
		
	}


	public List<Anotacao> getListNota() {
		return listNota;
	}


	public void setListNota(List<Anotacao> listNota) {
		this.listNota = listNota;
		
		
	}

/*
	public Date getDataEntrada1() {
		return dataEntrada1;
	}


	public void setDataEntrada1(Date dataEntrada1) {
		this.dataEntrada1 = dataEntrada1;
	}
*/

	public Date getDataAte2() {
		return dataAte2;
	}


	public void setDataAte2(Date dataAte2) {
		this.dataAte2 = dataAte2;
	}


	public double getTotalSemJuros() {
		return totalSemJuros;
	}

	public void setTotalSemJuros(double totalSemJuros) {
		this.totalSemJuros = totalSemJuros;
	}

	public List<Taxas> getT() {
		return t;
	}

	public void setT(List<Taxas> t) {
		this.t = t;

		System.out.println("oi t");
	}

	public String getVencimentoBoleto() {
		return vencimentoBoleto;
	}

	public void setVencimentoBoleto(String vencimentoBoleto) {
		this.vencimentoBoleto = vencimentoBoleto;
	}

	public List<ParcelaR> getParcelasGeradas() {
		return parcelasGeradas;
	}

	public void setParcelasGeradas(List<ParcelaR> parcelasGeradas) {
		this.parcelasGeradas = parcelasGeradas;
	}

	public double getValorLiquido() {
		return valorLiquido;
	}

	public void setValorLiquido(double valorLiquido) {
		this.valorLiquido = valorLiquido;
	}

	public double getValorEntrada() {
		return valorEntrada;
	}

	public void setValorEntrada(double valorEntrada) {
		this.valorEntrada = valorEntrada;
		this.valorDiferenca = totalVparcelas - valorEntrada;
		
	}

	public String getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(String dataEntrada) {
		this.dataEntrada = dataEntrada;
	//	session.setAttribute("dataEntrada", dataEntrada);
	}

	public double getValorDiferenca() {
		return valorDiferenca;
	}

	public void setValorDiferenca(double valorDiferenca) {
		this.valorDiferenca = valorDiferenca;
		this.valorEntrada = totalVparcelas - valorDiferenca;


	}

	public int getxVezes() {
		return xVezes;
	}

	public void setxVezes(int xVezes) {
		this.xVezes = xVezes;

	}

	public void setTotalVparcelas(double totalVparcelas) {
		this.totalVparcelas = totalVparcelas;
	}

	public Taxas getMulta2Perc() {
		return multa2Perc;
	}

	public void setMulta2Perc(Taxas multa2Perc) {
		this.multa2Perc = multa2Perc;
	}

	public Taxas getTaxa0033Perc() {
		return taxa0033Perc;
	}

	public void setTaxa0033Perc(Taxas taxa0033Perc) {
		this.taxa0033Perc = taxa0033Perc;
	}

	public Taxas getCustasBL() {
		return custasBL;
	}

	public void setCustasBL(Taxas custasDevBL) {
		this.custasBL = custasDevBL;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

/*	public String getDataAte() {

		return dataAte;
	}

	public void setDataAte(String dataAte) {
		this.dataAte = dataAte;
		session.setAttribute("dataAte", dataAte);

	}*/

	public List<Xparcela> getListaParcela() {
		return listaParcela;
	}

	public void setListaParcela(List<Xparcela> listaParcela) {
		this.listaParcela = listaParcela;
	}

}
