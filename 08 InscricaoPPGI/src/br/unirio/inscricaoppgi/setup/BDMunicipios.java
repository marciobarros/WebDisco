package br.unirio.inscricaoppgi.setup;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import br.unirio.inscricaoppgi.dao.DAOFactory;
import br.unirio.inscricaoppgi.model.Municipio;

import com.google.appengine.tools.remoteapi.RemoteApiInstaller;
import com.google.appengine.tools.remoteapi.RemoteApiOptions;

public class BDMunicipios
{
	public static void main(String[] args) throws IOException
	{
		Scanner scanner = new Scanner(new FileInputStream("/Users/Marcio/Desktop/Codigos/Ensino/InscricaoPPGI2/InscricaoPPGI2/data/pop municipios.data"));

        //RemoteApiOptions options = new RemoteApiOptions().server("URL", 443).credentials("E-MAIL", "SENHA");
        RemoteApiOptions options = new RemoteApiOptions().server("localhost", 8888).credentials("", "");
        RemoteApiInstaller installer = new RemoteApiInstaller();
        installer.install(options);

	    while (scanner.hasNextLine())
	    {
	    	String linha = scanner.nextLine();
	    	int indice = linha.lastIndexOf(',');
	    	
	    	if (indice != -1)
	    	{
	    		String estado = linha.substring(indice + 1).replace("\"", "").trim();
	    		String municipio = linha.substring(0, indice-1).replace("\"", "").trim();
	    		DAOFactory.getMunicipioDAO().put(new Municipio(municipio, estado));
	    		System.out.println(municipio + "\t" + estado);
	    	}
	    }

	    scanner.close();
	    installer.uninstall();
	}
}