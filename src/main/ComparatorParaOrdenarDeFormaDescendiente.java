package main;

import java.util.Comparator;

public class ComparatorParaOrdenarDeFormaDescendiente implements Comparator<Animal>{

	@Override
	public int compare(Animal a1, Animal a2) {

		// Esto hace que al usar el metodo Sort de Collections y pasandole esta clase Comparator, me ordene a esa coleccion de forma descendiente de acuerdo a la agresividad de los animales dentro. //
		
		/*
		String agre2=String.valueOf(a2.getAgresividad());
		String agre1=String.valueOf(a1.getAgresividad());
		
		return agre2.compareTo(agre1);
		*/
		
		if(a2.getAgresividad()>a1.getAgresividad())
		{
			return 1;
		}
		if(a2.getAgresividad()<a1.getAgresividad())
		{
			return -1;
		}
		return 0;
	}

}
