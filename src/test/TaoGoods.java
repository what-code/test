package test;

import java.io.Serializable;
import java.sql.Date;


public class TaoGoods implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 12L;

	
	private Integer id;
	
	private String sourceUrl;
	
	private String name;
	
	//private String img;

	public Integer getId() {
		return id;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public String getName() {
		return name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/*public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}*/

	public String toString(){
		return this.id + "_" + this.name + "_" + this.sourceUrl;
	}
}
