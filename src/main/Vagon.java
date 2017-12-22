package main;

import java.util.ArrayList;

public class Vagon {
	
	private ArrayList<Animal> animales;
	private Integer agresividadEnVagon;
	
	public Vagon(Integer agresividadEnVagon)
	{
		animales=new ArrayList<Animal>();
		this.agresividadEnVagon=agresividadEnVagon;
	}

	public ArrayList<Animal> getAnimales() {
		return animales;
	}

	public void setAnimales(ArrayList<Animal> animales) {
		this.animales = animales;
	}

	public Integer getAgresividadEnVagon() {
		return agresividadEnVagon;
	}

	public void setAgresividadEnVagon(Integer agresividadEnVagon) {
		this.agresividadEnVagon = agresividadEnVagon;
	}
	
	public void agregarAnimalAlVagon(Animal animal)
	{
		animales.add(animal);
	}
	
	public void obtenerAgresividadEnElVagon(int agresionMinima, int agresionMaxima)
	{
		agresividadEnVagon=agresionMaxima-agresionMinima;
	}
}
