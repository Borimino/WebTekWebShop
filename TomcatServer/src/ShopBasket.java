import org.jdom2.Namespace;



public class ShopBasket {

	private CloudHandler cloudHandler;
	private Namespace n;
	
	
	public ShopBasket() {

		this(Namespace.getNamespace("http://www.cs.au.dk/dWebTek/2014"));
	
	}
	
	public ShopBasket(Namespace n) {

		
		this.n = n;
		cloudHandler = new CloudHandler(n);
	
	}
	
	
	
	
	
	
	
}
