package clasificadores;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.plaf.DesktopPaneUI;

import datos.Datos;
import redNeuronal.RedNeuronal;

public class ClasificadorRetropropagacion extends Clasificador{

	
	private RedNeuronal red;
	private int epocas;
	private int epoca;
	private ArrayList<Integer> capas;
	private double tasa;
	private ArrayList<Double> res;
	private double ecm;
	private double ecmAnt;
	public ClasificadorRetropropagacion(ArrayList<Integer> capas, double tasa, int epocas) {
		this.capas=capas;
		this.tasa=tasa;
		this.epocas=epocas;
		this.epoca=0;
		this.ecm=0;

		red=new RedNeuronal(capas, tasa);
		red.iniRed();
	}

	public boolean paradaEpocas(){
		if((epoca%10==0&&epoca<1000) ||epoca%100==0){
			//System.out.println("epoca "+epoca+" de "+epocas+" ecm:"+this.ecm);
			//System.out.println("inc:"+Math.abs(ecm-ecmAnt));
		}
		if(epoca>0&&Math.abs(ecm-ecmAnt)<0.00000001){
			return false;
		}
		if(epoca==epocas)
			return false;
		epoca++;
		return true;
	}
	@Override
	public void entrenamiento(Datos datosTrain) {

		this.epoca=0;
		/*ArrayList<Integer> resultado= new ArrayList<>();
		resultado.add(0);
		resultado.add(0);
		for(int i=0; i<datosTrain.getNumDatos();i++){
			double indice=datosTrain.getDato(i, datosTrain.getSizeCountAtributos()-1);
			int element= resultado.get(datosTrain.getClasesValue(indice))+1;
			resultado.set(datosTrain.getClasesValue(indice), element);
		}
		
		System.out.println("datos de entrada para train");
		System.out.println(resultado.get(0));
		System.out.println(resultado.get(1));*/
		while(paradaEpocas()){
			double ecm=0.0;
			for(int i=0; i<datosTrain.getNumDatos();i++){
				ArrayList<Double> atributos= new ArrayList<>();
				for(int j=0; j<datosTrain.getSizeCountAtributos()-1; j++){
					atributos.add(datosTrain.getDato(i, j));
				}
				ArrayList<Double> clases= new ArrayList<>();
				for(int j=0; j<datosTrain.getClases().size(); j++){
					clases.add(-1.0);
				}
				double indice=datosTrain.getDato(i, datosTrain.getSizeCountAtributos()-1);
				clases.set(datosTrain.getClases().get(indice), 1.0);
				red.actualizaSalidaRed(atributos);
				/*if(i==0){
					System.out.println(red.neuronaActivada());
				}*/
				red.actualizaDeltas(clases);
				red.actualizaPesos();
				for(int k=0;k< clases.size(); k++){
					int n=red.neuronasAnteriores(capas.size()-1)+k;
					//System.out.println(n);
					double predicc=red.getNeuronas().get(n).getSalida();
					ecm+=Math.pow(clases.get(k)-predicc, 2);
				}

				ecm/=(clases.size());
			}
			ecm/=(datosTrain.getNumDatos());
			this.ecm=ecm;
		}
		
	}

	@Override
	public ArrayList<Double> clasifica(Datos datosTest) {
		ArrayList<Double> res= new ArrayList<>();

		/*ArrayList<Integer> resultado= new ArrayList<>();
		resultado.add(0);
		resultado.add(0);
		for(int i=0; i<datosTest.getNumDatos();i++){
			double indice=datosTest.getDato(i, datosTest.getSizeCountAtributos()-1);
			int element= resultado.get(datosTest.getClasesValue(indice))+1;
			resultado.set(datosTest.getClasesValue(indice), element);
		}
		System.out.println("datos de entrada para test");
		System.out.println(resultado.get(0));
		System.out.println(resultado.get(1));*/

		for(int i=0; i<datosTest.getNumDatos();i++){
			//System.out.println("dato:"+i);
			ArrayList<Double> atributos= new ArrayList<>();
			for(int j=0; j<datosTest.getSizeCountAtributos()-1; j++){
				atributos.add(datosTest.getDato(i, j));
			}
			if(atributos==null)
				System.out.println("atributos null");

			if(red==null)
				System.out.println("red null");
			red.actualizaSalidaRed(atributos);
			int clase=red.neuronaActivada();
			double prediccion=0;
			Enumeration<Double> keys= datosTest.getClases().keys();
			while(keys.hasMoreElements()){
				Double key= keys.nextElement();
				if(datosTest.getClases().get(key).equals(clase)){
					prediccion=key;
					break;
				}
			}
			//System.out.println("prediccion:"+prediccion);
			res.add(prediccion);
 		}
		this.res=res;
		//red.printPesos();
		return res;
	}

	@Override
	public ArrayList<Double> getResultado() {
		return res;
	}

	
	public RedNeuronal getRed(){
		return this.red;
	}
}
