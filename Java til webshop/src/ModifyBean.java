import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.sun.xml.internal.txw2.Document;

@ManagedBean
@SessionScoped
public class ModifyBean {
	private Document Createitem;
	
	public Document getItem() {
		return Createitem;
	}

	public void setItem(Document item) {
		this.item = Createitem;
	}

}
