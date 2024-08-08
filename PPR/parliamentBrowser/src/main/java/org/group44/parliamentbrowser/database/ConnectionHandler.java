package org.group44.parliamentbrowser.database;

import java.util.List;

import org.group44.parliamentbrowser.exception.BundestagException;
import org.group44.parliamentbrowser.exception.BundestagFileException;
import org.group44.parliamentbrowser.types.Abgeordneter;
import org.group44.parliamentbrowser.types.Rede;
import org.group44.parliamentbrowser.types.Sitzung;
import org.group44.parliamentbrowser.types.Tagesordnung;

public interface ConnectionHandler {

	public void reset() throws BundestagException;
	
	public void createAbgeordnete(List<Abgeordneter> abgeordnete) throws BundestagException;

	public void createReden(List<Rede> reden) throws BundestagException;
	
	public void createSitzungen(List<Sitzung> sitzungen) throws BundestagException;
	
	public void createTagesordnungen(List<Tagesordnung> tagesordnungen) throws BundestagException;
	
	public List<Abgeordneter> readAbgeordnete() throws BundestagFileException;
	
	public List<Rede> readReden() throws BundestagFileException;
	
	public List<Sitzung> readSitzungen() throws BundestagFileException;
	
	public List<Tagesordnung> readTagesordnungen() throws BundestagFileException;
	
}
