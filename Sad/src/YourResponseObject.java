public class YourResponseObject {
    private boolean success;
    private YourScores scores;
    private String result;
    
    
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public YourScores getScores() {
		return scores;
	}
	public void setScores(YourScores scores) {
		this.scores = scores;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

    // 省略 getter 和 setter 方法
}