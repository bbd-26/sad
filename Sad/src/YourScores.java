
public class YourScores {
    private int component;
    private int logo;
    private int antiForgeryLabel;
    private int total;
    
    public YourScores(int component,int logo,int antiForgeryLabel) {
    	this.component = component;
    	this.logo = logo;
    	this.antiForgeryLabel = antiForgeryLabel;
    	this.total = component + logo + antiForgeryLabel;
    	
    			
    }
    
	public int getComponent() {
		return component;
	}
	public void setComponent(int component) {
		this.component = component;
	}
	public int getLogo() {
		return logo;
	}
	public void setLogo(int logo) {
		this.logo = logo;
	}
	public int getAntiForgeryLabel() {
		return antiForgeryLabel;
	}
	public void setAntiForgeryLabel(int antiForgeryLabel) {
		this.antiForgeryLabel = antiForgeryLabel;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

    // 省略 getter 和 setter 方法
}