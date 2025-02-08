package p1;

import java.util.Date;
/**
 * Clasa pentru definirea obiectelor de tip Animal
 */
public class Animal {
	private int id;						//Stocheaza id-ul unic al animalului
    private String nume;				//Numele animalului
    private String specie;				//Specia animalului
    private String rasa;				//Rasa animalului
	private String sex;					//Sexul animalului F/M
    private Date dataSosire;			//Data la care animalul a sosit la adapost
    private String stareSanatate;		//Starea de sanatate a animalului
    private String descriereFizica;		//Stocheaza descrierea fizica a animalului
    boolean stareAdoptie;				//Stocheaza daca animalul respectiv a fost sau nu adoptat de o persoana
    private String picture;				//Stocheaza reprezentarea unei poze in base64

    public Animal() {}
    
    public Animal(int id, String nume, String specie, String rasa, String sex, Date dataSosire,
                  String stareSanatate, String descriereFizica, boolean stareAdoptie, String picture) {
        this.id = id;
        this.nume = nume;
        this.specie = specie;
        this.rasa = rasa;
        this.sex = sex;
        this.dataSosire = dataSosire;
        this.stareSanatate = stareSanatate;
        this.descriereFizica = descriereFizica;
        this.stareAdoptie = stareAdoptie;
        this.picture = picture;
    }

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNume() {
		return nume;
	}
	public void setNume(String nume) {
		this.nume = nume;
	}
	public String getSpecie() {
		return specie;
	}
	public void setSpecie(String specie) {
		this.specie = specie;
	}
	public String getRasa() {
		return rasa;
	}
	public void setRasa(String rasa) {
		this.rasa = rasa;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getDataSosire() {
		return dataSosire;
	}
	public void setDataSosire(Date dataSosire) {
		this.dataSosire = dataSosire;
	}
	public String getStareSanatate() {
		return stareSanatate;
	}
	public void setStareSanatate(String stareSanatate) {
		this.stareSanatate = stareSanatate;
	}
	public String getDescriereFizica() {
		return descriereFizica;
	}
	public void setDescriereFizica(String descriereFizica) {
		this.descriereFizica = descriereFizica;
	}
	public boolean getStareAdoptie() {
		return stareAdoptie;
	}
	public void setStareAdoptie(boolean stareAdoptie) {
		this.stareAdoptie = stareAdoptie;
	}
	public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }

	@Override
	public String toString() {
		return id + "," + nume + "," + specie + "," + rasa + "," + sex
				+ "," + dataSosire + "," + stareSanatate + ","
				+ descriereFizica + "," + stareAdoptie;
	}
}
