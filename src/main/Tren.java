package main;

import java.util.ArrayList;

public class Tren {

	private ArrayList<Vagon> vagones;
	private Integer limiteDeAgresividad;
	
	public Tren(Integer cantDeEspecies,Integer limiteDeAgresividad)
	{
		this.vagones=new ArrayList<Vagon>(cantDeEspecies);
		this.limiteDeAgresividad=limiteDeAgresividad;
	}

	public ArrayList<Vagon> getVagones() {
		return vagones;
	}

	public void setVagones(ArrayList<Vagon> vagones) {
		this.vagones = vagones;
	}

	public Integer getLimiteDeAgresividad() {
		return limiteDeAgresividad;
	}

	public void setLimiteDeAgresividad(Integer limiteDeAgresividad) {
		this.limiteDeAgresividad = limiteDeAgresividad;
	}	
	
	public void ingresarVagonAlTren(Vagon vagon)
	{
		vagones.add(vagon);
	}
}
