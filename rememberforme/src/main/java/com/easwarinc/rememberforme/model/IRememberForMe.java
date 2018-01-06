package com.eshwarinc.rememberforme.model;

import java.util.Date;
import java.util.List;

/**
 * Interface to represent the model for RememberForMe.
 * 
 * @author Sriram
 */
public interface IRememberForMe {
	public String rememberingFor();
	public void rememberForMe(String sentence);
	public List<String> getAllItemsRemembered();
	public List<String> getItemsRememberedFor(String noun);
	public List<String> getItemsRememberedOn(Date date);
}
