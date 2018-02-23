package com.camd.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.camd.model.Cliente;
import com.camd.model.Xparcela;


public class ClienteImpl implements ClienteDao {

    private Session session;
    private String connectionUrl = "jdbc:sqlserver://192.168.2.6:1433;"
			+ "databaseName=Corpore;user=rm;password=rm;";
    
    public void setSession(Session session) {
        this.session = session;
    }
	
	@Override
	public void save(Cliente cliente) {
		 this.session.saveOrUpdate(cliente);
		
	}

	@Override
	public List<Cliente> getClientes() {

		Criteria criteria = session.createCriteria(Cliente.class);
		List<Cliente> clienteLista = criteria.list();

		return clienteLista;
	}
	
	
	@Override
	public void limpaTabela() {
		
		String hqlDelete = "delete Xparcela";
		Query query = session.createQuery(hqlDelete);
		query.executeUpdate();
		
		
	}

	@Override
	public List<Cliente> AtualizarParcClie() {
		List<Cliente> listaCl = new ArrayList<>();
		List<Xparcela> listaXp = new ArrayList<>();
		
		List<Xparcela> parcelasClente;
		Cliente cliente;
		Xparcela xp;

		Connection con;
		try {
			con = conex();

			String sql = "SELECT DISTINCT \r\n" + " L.CODCFO,\r\n" + " C.CGCCFO,\r\n" + " C.NOME AS CLIENTE,\r\n"
					+ " C.RUA,\r\n" + " C.NUMERO,\r\n" + " C.COMPLEMENTO,\r\n" + " C.CIDADE,\r\n" + " C.CODETD,\r\n"
					+ " C.CEP\r\n" + "FROM FLAN L (NOLOCK) \r\n"
					+ " INNER JOIN FLANBOLETO LB (NOLOCK) ON (L.CODCOLIGADA = LB.CODCOLIGADA AND L.IDLAN=LB.IDLAN)\r\n"
					+ " INNER JOIN FBOLETO B (NOLOCK) ON (L.CODCOLIGADA = B.CODCOLIGADA AND LB.IDBOLETO = B.IDBOLETO)\r\n"
					+ " INNER JOIN FCFO C (NOLOCK) ON (L.CODCOLIGADA = C.CODCOLIGADA AND L.CODCFO = C.CODCFO)\r\n"
					+ " INNER JOIN GCCUSTO CC (NOLOCK) ON (L.CODCOLIGADA = CC.CODCOLIGADA AND L.CODCCUSTO = CC.CODCCUSTO)\r\n"
					+ "WHERE L.CODCOLIGADA = 1\r\n" + " AND L.STATUSLAN IN (0)\r\n" + " AND L.PAGREC = 1\r\n"
					+ "    AND B.VENCIMENTO >= '2017-01-01'\r\n" + "    AND B.VENCIMENTO <= getdate()\r\n"
					+ " AND B.STATUS = 0\r\n" + " group by L.CODCFO,\r\n" + " C.CGCCFO,\r\n" + " C.NOME,\r\n"
					+ " C.RUA,\r\n" + " C.NUMERO,\r\n" + " C.COMPLEMENTO,\r\n" + " C.CIDADE,\r\n" + " C.CODETD,\r\n"
					+ " C.CEP,\r\n" + " B.VENCIMENTO";
			PreparedStatement stmt = con.prepareStatement(sql);
			// stmt.setString(1, pes.getCpf()); //coloca a String de pes.getCpf() no
			// lugar do ? na sentença SQL
			ResultSet rs = stmt.executeQuery(); // executa query e armazena resultado em rs

			while (rs.next()) { // enquanto tiver resultados, anda para o próxima

				cliente = new Cliente();
				cliente.setCodcfo(rs.getString("CODCFO"));
				cliente.setNome(rs.getString("CLIENTE"));
				cliente.setCpf(rs.getString("CGCCFO"));
				cliente.setRua(rs.getString("RUA"));
				cliente.setNumero(rs.getString("NUMERO"));
				cliente.setComplemento(rs.getString("COMPLEMENTO"));
				cliente.setCidade(rs.getString("CIDADE"));
				cliente.setEstado(rs.getString("CODETD"));
				cliente.setCep(rs.getString("CEP"));
				listaCl.add(cliente);

			
			}

			sql = "SELECT\r\n" + 
					"CC.CODCCUSTO,\r\n" + 
					" L.CODCFO,\r\n" + 
					" L.IDLAN,\r\n" + 
					" B.IDBOLETO,\r\n" + 
					" B.NOSSONUMERO,\r\n" + 
					" B.VALOR,\r\n" + 
					" B.VENCIMENTO,\r\n" + 
					" F.DESCRICAO\r\n" + 
					"FROM FLAN L (NOLOCK) \r\n" + 
					" LEFT JOIN FTB1 F (NOLOCK) ON F.CODCOLIGADA = L.CODCOLIGADA AND F.CODTB1FLX = L.CODTB1FLX\r\n" + 
					" INNER JOIN FLANBOLETO LB (NOLOCK) ON (L.CODCOLIGADA = LB.CODCOLIGADA AND L.IDLAN=LB.IDLAN)\r\n" + 
					" INNER JOIN FBOLETO B (NOLOCK) ON (L.CODCOLIGADA = B.CODCOLIGADA AND LB.IDBOLETO = B.IDBOLETO)\r\n" + 
					" INNER JOIN FCFO C (NOLOCK) ON (L.CODCOLIGADA = C.CODCOLIGADA AND L.CODCFO = C.CODCFO)\r\n" + 
					" INNER JOIN GCCUSTO CC (NOLOCK) ON (L.CODCOLIGADA = CC.CODCOLIGADA AND L.CODCCUSTO = CC.CODCCUSTO)\r\n" + 
					" \r\n" + 
					"WHERE L.CODCOLIGADA = 1\r\n" + 
					" AND L.STATUSLAN IN (0)\r\n" + 
					" AND L.PAGREC = 1\r\n" + 
					"    AND B.VENCIMENTO >= '2017-01-01'\r\n" + 
					"    AND B.VENCIMENTO <= getdate()\r\n" + 
					" AND B.STATUS = 0\r\n" + 
					"order by B.VENCIMENTO, C.NOME";
			stmt = con.prepareStatement(sql);
			// stmt.setString(1, pes.getCpf()); //coloca a String de pes.getCpf() no
			// lugar do ? na sentença SQL
			rs = stmt.executeQuery();

			while (rs.next()) { // enquanto tiver resultados, anda para o próxima

				xp = new Xparcela();
				xp.setCodcfo(rs.getString("CODCFO"));
				xp.setIdlan(rs.getInt("IDLAN"));
				xp.setCcusto(rs.getString("CODCCUSTO"));
				xp.setIdboleto(rs.getInt("IDBOLETO"));
				xp.setNossonumero(rs.getString("NOSSONUMERO"));
				xp.setValor(rs.getDouble("VALOR"));
				xp.setVencimento(rs.getDate("VENCIMENTO"));
				xp.setClassFinanceira(rs.getString("DESCRICAO"));
				xp.setRenegociada(false);
				listaXp.add(xp);

			}

			rs.close();
			stmt.close();
			con.close();

		} catch (SQLException e) {
			System.out.println("Erro ao conectar ao bonco de dados!");
			e.printStackTrace();
		}
		
		

		for (Cliente c : listaCl) {
			parcelasClente = new ArrayList<>();
			for (Xparcela x : listaXp) {
		
				if (c.getCodcfo().contains(x.getCodcfo()) ) {
					x.setCliente(c);
					parcelasClente.add(x);
				}

			}
			System.out.println(" cliente: " +c.getCodcfo()+"-"+ c.getNome() + " possui: " + parcelasClente.size() + " parcelas");
			c.setParcelas(parcelasClente);
		}
		return listaCl;

	}

	private Connection conex() throws SQLException {
		Connection con = DriverManager.getConnection(connectionUrl);

		return con;
	}


}
