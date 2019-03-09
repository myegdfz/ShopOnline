package which.me.Bean;

public class slideBean {

	private int id;
	private String name;
	private String proPic;
	private String url;
	public slideBean(String name, String proPic, String url) {
		this.name = name;
		this.proPic = proPic;
		this.url = url;
	}
	public slideBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "slideBean [id=" + id + ", name=" + name + ", proPic=" + proPic + ", url=" + url + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProPic() {
		return proPic;
	}
	public void setProPic(String proPic) {
		this.proPic = proPic;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
