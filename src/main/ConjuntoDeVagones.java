package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.*;
import javax.swing.JOptionPane;


public class ConjuntoDeVagones {
	
	private Tren tren;
	private ArrayList<Vagon> vagonesDeSubida;
	private ArrayList<Vagon> vagonesDeBajada;
	private ArrayList<Animal> animalesAuxDeSubida;
	private ArrayList<Animal> animalesAuxDeBajada;
	
	public ConjuntoDeVagones()
	{
		File archIn;
		Scanner sc;
		try
		{
			archIn=new File("in.txt");  // archIn=new File(JOptionPane.showInputDialog("Ingrese el path completo del archivo de entrada")); //
			sc=new Scanner(archIn);
			int cantDeEspecies=sc.nextInt();
			tren=new Tren(cantDeEspecies,sc.nextInt());
			animalesAuxDeSubida=new ArrayList<Animal>(cantDeEspecies);
			animalesAuxDeBajada=new ArrayList<Animal>(cantDeEspecies);
			vagonesDeSubida=new ArrayList<Vagon>();
			vagonesDeBajada=new ArrayList<Vagon>();
			for(int i=0; i<cantDeEspecies; i++)
			{
				sc.nextLine(); // Para limpiar el buffer por venir de un nextInt e ir hacia un nextLine. //
				animalesAuxDeSubida.add(new Animal(sc.next(),sc.nextInt()));
				animalesAuxDeBajada.add(animalesAuxDeSubida.get(i));
				sc.nextInt(); // Este es el dato de la cantidad de animales que hay de la especie. Como no sirve para nada, no lo guardo. //
			}
			
			Collections.sort(animalesAuxDeSubida); // Ordeno el ArrayList de menor a mayor por agresividad de especie. //
			Collections.sort(animalesAuxDeBajada,new ComparatorParaOrdenarDeFormaDescendiente()); // Ordeno el ArrayList de mayor a menos por agresividad de especie. //
			
			try
			{
				sc.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.exit(1);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	
	public void resolver()
	{
		int menorActual=0;
		int mayorActual=tren.getLimiteDeAgresividad();
		boolean crearVagonNuevo=true;
		int vagonActualALlenar=0;
		int indexMasChicoActualParaAnimalEnElVagon=0;
		int indexMasAltoActualParaAnimalEnElVagon;
		int aVer=0;
		
		for(int i=0; i<animalesAuxDeSubida.size(); i++) // Lo de dentro de este for es para llenar los vagones siendo que el ordenamiento del ArrayList de animales fue en forma ascendente por agresividad. //
		{
			if(crearVagonNuevo==true)
			{
				if(i==0 || vagonesDeSubida.get(vagonesDeSubida.size()-1).getAnimales().size()!=0)
				{
					vagonesDeSubida.add(new Vagon(0));
					indexMasChicoActualParaAnimalEnElVagon=i;
				}
			}
			
			if(animalesAuxDeSubida.get(i).getAgresividad()>=menorActual && animalesAuxDeSubida.get(i).getAgresividad()<=mayorActual) // Si el animal que estamos analizando ahora, esta dentro de las cotas, entonces lo meto adentro del vagon. //
			{
				crearVagonNuevo=false;
				vagonesDeSubida.get(vagonActualALlenar).agregarAnimalAlVagon(animalesAuxDeSubida.get(i));
				aVer++;
				
			}
			else
			{
				crearVagonNuevo=true;
				
				//vagonActualALlenar++;
				menorActual=mayorActual+1;				
				mayorActual+=tren.getLimiteDeAgresividad();
				if(aVer>1) // Los index ayudan en el proceso de idenfiticar qué animales del vagon son los que participan en la obtencion de la agresividad total en el vagon. Entonces, esto que hago aca es para que los index apunten a los animales adecuados. //
				{
					indexMasAltoActualParaAnimalEnElVagon=i-1;
				}
				else
				{
					indexMasAltoActualParaAnimalEnElVagon=indexMasChicoActualParaAnimalEnElVagon;
				}
				
				vagonesDeSubida.get(vagonActualALlenar).obtenerAgresividadEnElVagon(animalesAuxDeSubida.get(indexMasChicoActualParaAnimalEnElVagon).getAgresividad(),animalesAuxDeSubida.get(indexMasAltoActualParaAnimalEnElVagon).getAgresividad()); // Seteo la agresividad de este ultimo vagon. //
				
				int sumaDeAgresividadesEnVagonesActualesMenosElUltimo=0;
				
				for(int j=0; j<vagonActualALlenar; j++) // Obtengo la suma de agresividades de los vagones que ya tienen los animales adentro, menos este vagon. //
				{
					sumaDeAgresividadesEnVagonesActualesMenosElUltimo+=vagonesDeSubida.get(j).getAgresividadEnVagon();
				}
				
				
				
				if(vagonesDeSubida.get(vagonActualALlenar).getAgresividadEnVagon() > tren.getLimiteDeAgresividad()-sumaDeAgresividadesEnVagonesActualesMenosElUltimo) // Me fijo si la agresividad de este ultimo vagon es menor o igual al resto de agresividad que resta en el tren. //
				{
					vagonesDeSubida.remove(vagonesDeSubida.size()-1);
					
					for(int z=indexMasChicoActualParaAnimalEnElVagon; z<animalesAuxDeSubida.size(); z++)
					{
						Vagon aux = new Vagon(0);
						aux.agregarAnimalAlVagon(animalesAuxDeSubida.get(z));
						vagonesDeSubida.add(aux);
					}					

					i=animalesAuxDeSubida.size()+100; // Para cortar el for principal. //
				}
				
				
				if(vagonesDeSubida.get(vagonActualALlenar).getAnimales().size()>0) // En el caso de que este vagon tenga al menos un animal, aumento el indice del vagon actual. //
				{
					vagonActualALlenar++;
				}
				
				i--;
				aVer=0;
			}
		}
		
		
		
		
		
		mayorActual=animalesAuxDeBajada.get(0).getAgresividad();
		menorActual=mayorActual - tren.getLimiteDeAgresividad();
		crearVagonNuevo=true;
		vagonActualALlenar=0;
		indexMasChicoActualParaAnimalEnElVagon=0;
		aVer=0;
		
		for(int i=0; i<animalesAuxDeBajada.size(); i++) // Lo de dentro de este for es para llenar los vagones siendo que el ordenamiento del ArrayList de animales fue en forma descendente por agresividad. //
		{
			if(crearVagonNuevo==true)
			{
				if(i==0 || vagonesDeBajada.get(vagonesDeBajada.size()-1).getAnimales().size()!=0)
				{
					vagonesDeBajada.add(new Vagon(0));
					indexMasChicoActualParaAnimalEnElVagon=i;
				}
			}
			
			if(animalesAuxDeBajada.get(i).getAgresividad()>=menorActual && animalesAuxDeBajada.get(i).getAgresividad()<=mayorActual) // Si el animal que estamos analizando ahora, esta dentro de las cotas, entonces lo meto adentro del vagon. //
			{
				crearVagonNuevo=false;
				vagonesDeBajada.get(vagonActualALlenar).agregarAnimalAlVagon(animalesAuxDeBajada.get(i));
				aVer++;
			}
			else
			{
				crearVagonNuevo=true;
				//vagonActualALlenar++;
				mayorActual=menorActual-1;				
				menorActual-=tren.getLimiteDeAgresividad();

				if(aVer>1) // Los index ayudan en el proceso de idenfiticar qué animales del vagon son los que participan en la obtencion de la agresividad total en el vagon. Entonces, esto que hago aca es para que los index apunten a los animales adecuados. //
				{
					indexMasAltoActualParaAnimalEnElVagon=i-1;
				}
				else
				{
					indexMasAltoActualParaAnimalEnElVagon=indexMasChicoActualParaAnimalEnElVagon;
				}
				
				
				vagonesDeBajada.get(vagonActualALlenar).obtenerAgresividadEnElVagon(animalesAuxDeBajada.get(indexMasChicoActualParaAnimalEnElVagon).getAgresividad(),animalesAuxDeBajada.get(indexMasAltoActualParaAnimalEnElVagon).getAgresividad()); // Seteo la agresividad de este ultimo vagon. //
				
				int sumaDeAgresividadesEnVagonesActualesMenosElUltimo=0;
				
				for(int j=0; j<vagonActualALlenar; j++) // Obtengo la suma de agresividades de los vagones que ya tienen los animales adentro, menos este vagon. //
				{
					sumaDeAgresividadesEnVagonesActualesMenosElUltimo+=vagonesDeBajada.get(j).getAgresividadEnVagon();
				}
				
				
				
				if(vagonesDeBajada.get(vagonActualALlenar).getAgresividadEnVagon() > tren.getLimiteDeAgresividad()-sumaDeAgresividadesEnVagonesActualesMenosElUltimo) // Me fijo si la agresividad de este ultimo vagon es menor o igual al resto de agresividad que resta en el tren. //
				{
					vagonesDeBajada.remove(vagonesDeBajada.size()-1);
					
					for(int z=indexMasChicoActualParaAnimalEnElVagon; z<animalesAuxDeSubida.size(); z++)
					{
						Vagon aux = new Vagon(0);
						aux.agregarAnimalAlVagon(animalesAuxDeBajada.get(z));
						vagonesDeBajada.add(aux);
					}					

					i=animalesAuxDeSubida.size()+100; // Para cortar el for principal. //
				}
				

				if(vagonesDeBajada.get(vagonActualALlenar).getAnimales().size()>0) // En el caso de que este vagon tenga al menos un animal, aumento el indice del vagon actual. //
				{
					vagonActualALlenar++;
				}
				
				i--;
				aVer=0;
			}
		}
		
		
		
		
		
		
		
		if(vagonesDeSubida.size()>=vagonesDeBajada.size()) // Decido cual de las dos formaciones de vagones tiene menos vagones. //
		{
			tren.setVagones(vagonesDeSubida);
		}
		else
		{
			tren.setVagones(vagonesDeBajada);
		}
		
		printearSalida(sacarLaAgresividadTotal(tren)); // Printeo la salida en el archivo. //
		
	}
	
	
	
	
	private int sacarLaAgresividadTotal(Tren tren)
	{
		int agresividadAcumulada=0;
		for(int i=0; i<tren.getVagones().size(); i++)
		{
			agresividadAcumulada+=tren.getVagones().get(i).getAgresividadEnVagon();
		}
		return agresividadAcumulada;
	}
	
	
	private void printearSalida(int agresividadTotalEnElTren)
	{
		File archSal;
		FileWriter fw;
		PrintWriter pw;
		
		try
		{
			archSal=new File("out.txt");
			fw=new FileWriter(archSal);
			pw=new PrintWriter(fw);
			pw.println(tren.getVagones().size()+" "+agresividadTotalEnElTren);
			try
			{
				fw.close();
				pw.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.exit(1);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
}
