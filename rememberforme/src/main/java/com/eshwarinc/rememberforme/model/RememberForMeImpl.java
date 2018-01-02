package com.eshwarinc.rememberforme.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eshwarinc.rememberforme.util.PartOfSpeechFinderImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RememberForMeImpl implements IRememberForMe {

	private String rememberingFor;
	private List<String> items = new ArrayList<String>();
	private Map<String, List<String>> itemsByNounMap = new HashMap<String, List<String>>();
	private Map<String, List<String>> itemsByDateMap = new HashMap<String, List<String>>();

	public RememberForMeImpl(String rememberingFor) {
		this.rememberingFor = rememberingFor;
	}

	public String rememberingFor() {
		return rememberingFor;
	}

	public void rememberForMe(String toRemember) {
		toRemember = toRemember.toLowerCase();
		items.add(toRemember);
		for (String noun : PartOfSpeechFinderImpl.getInstance().getNouns(toRemember)) {
			List<String> list = itemsByNounMap.get(noun);
			if (list == null)
				list = new ArrayList<String>();
			list.add(toRemember);
			itemsByNounMap.put(noun, list);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
		List<String> byDateList = itemsByDateMap.get(sdf.format(new Date()));
		if (byDateList == null) {
			byDateList = new ArrayList<String>();
		}
		byDateList.add(toRemember);
		itemsByDateMap.put(sdf.format(new Date()), byDateList);
	}

	public List<String> getAllItemsRemembered() {
		return items;
	}

	public List<String> getItemsRememberedFor(String noun) {
		if (itemsByNounMap.containsKey(noun))
			return itemsByNounMap.get(noun.toLowerCase());
		else
			return new ArrayList<String>();
	}

	public List<String> getItemsRememberedOn(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
		String formattedDate = sdf.format(date);
		if (itemsByDateMap.containsKey(formattedDate))
			return itemsByDateMap.get(formattedDate);
		else
			return new ArrayList<String>();
	}

	@Override
	public String toString() {
		Gson gsonObj = new GsonBuilder().setPrettyPrinting().create();
		String toReturn = gsonObj.toJson(this);
		return toReturn;
	}
}
