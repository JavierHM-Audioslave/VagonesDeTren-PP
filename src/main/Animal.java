package main;

public class Animal implements Comparable<Animal>{
	
	private String especie;
	private Integer agresividad;
	
	public Animal(String especie, Integer agresividad)
	{
		this.especie=especie;
		this.agresividad=agresividad;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public Integer getAgresividad() {
		return agresividad;
	}

	public void setAgresividad(Integer agresividad) {
		this.agresividad = agresividad;
	}

	@Override
	public int compareTo(Animal o) {
		return this.agresividad.compareTo(o.agresividad);
	}
	
	

}
