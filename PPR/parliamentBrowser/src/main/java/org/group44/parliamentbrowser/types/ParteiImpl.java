package org.group44.parliamentbrowser.types;

import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.List;

/**
 * Informationen zu einer Partei
 * 
 * @author Cora
 */
public class ParteiImpl implements Partei {

	private List<String> mitgliederIds = new ArrayList<>();
	private String name;

	public ParteiImpl(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<String> getMitgliederIds() {
		return mitgliederIds;
	}

	@Override
	public void addMitgliedId(String abgeordneterId) {
		this.mitgliederIds.add(abgeordneterId);
	}

	@Override
	public Object getID() {
		return name;
	}

	@Override
	public String getLabel() {
		return name;
	}

	@Override
	public int compareTo(BundestagObject o) {
    	if (o == null) {
    		return -1;
    	}
		return this.getID().toString().compareTo(o.getID().toString());
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Partei) {
			return compareTo((Partei) o) == 0;
		}
		return false;
	}

	@Override
	public String toString() {
		return "ParteiImpl{" + 
				"name=" + name + 
				", mitglieder=[" + mitgliederIds.stream().collect(joining(",")) + "]" + 
				'}';
	}
}
