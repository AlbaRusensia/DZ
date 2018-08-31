package Model;

public class Model {

	protected String manufacturer;
	protected String numberModel;
	protected String price;

	public Model(String manufacturer, String numberModel, String price) {
		this.manufacturer = manufacturer;
		this.numberModel = numberModel;
		this.price = price;
	}

	public Model() { }
	
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public void setNumberModel(String numberModel) {
		this.numberModel = numberModel;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public String getNumberModel() {
		return numberModel;
	}

	public String getPrice() {
		return price;
	}

}
