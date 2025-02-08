package p1;

import java.util.Date;
/**
 * Clasa pentru definirea obiectelor ce eprezinta cererile de adoptie pentru animale
 */
public class Adoptie {
	private int id;					//Stocheza un id unic
	private String numePersoana;	//Stocheaza numele persoanei ce a depus cererea
	private int NrTel;				//Stocheaza numarul de telefon al persoanei ce a depus cererea
	private String adresa;			//Stocheaza adresa persoanei ce a depus cererea
	private int idAnimal;			//Stocheaza unul dintre id-urile corespunzatoare obiectelor 'Animal'
	private String numeAnimal;		//Stocheaza numele animalului adoptat
	private Date dataAdoptie;		//Stocheaza data la care cererea a fost completata
	boolean aprobare;				//Stocheaza daca cererea respectiva a fost sau nu aprobata
	
	public Adoptie() {}

	public Adoptie(int id, String numePersoana, int nrTel, String adresa, int idAnimal, String numeAnimal,
			Date dataAdoptie, boolean aprobare) {
		super();
		this.id = id;
		this.numePersoana = numePersoana;
		NrTel = nrTel;
		this.adresa = adresa;
		this.idAnimal = idAnimal;
		this.numeAnimal = numeAnimal;
		this.dataAdoptie = dataAdoptie;
		this.aprobare = aprobare;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumePersoana() {
		return numePersoana;
	}
	public void setNumePersoana(String numePersoana) {
		this.numePersoana = numePersoana;
	}
	public int getNrTel() {
		return NrTel;
	}
	public void setNrTel(int nrTel) {
		NrTel = nrTel;
	}
	public String getAdresa() {
		return adresa;
	}
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	public int getIdAnimal() {
		return idAnimal;
	}
	public void setIdAnimal(int idAnimal) {
		this.idAnimal = idAnimal;
	}
	public String getNumeAnimal() {
		return numeAnimal;
	}
	public void setNumeAnimal(String numeAnimal) {
		this.numeAnimal = numeAnimal;
	}
	public Date getDataAdoptie() {
		return dataAdoptie;
	}
	public void setDataAdoptie(Date dataAdoptie) {
		this.dataAdoptie = dataAdoptie;
	}
	public boolean isAprobare() {
		return aprobare;
	}
	public void setAprobare(boolean aprobare) {
		this.aprobare = aprobare;
	}	
}
