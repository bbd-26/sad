import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AuthenticityJudgment {
    private File brand; //品牌方報告
    private File management; //管理方報告
    String[] brandComponent=new String[6];
    String[] goodComponent=new String[6];
    float logo;
    int brandLabel;
    int goodLabel;

    public AuthenticityJudgment(File brand,File management) throws FileNotFoundException {
        this.management = management;
        this.brand = brand;
        //取品牌商資料
        Scanner brandSc = new Scanner(brand);
        for (int i=0;i<6; i++) {
        	String component=brandSc.next();
            if (!component.equals("none")) {
            	this.brandComponent[i] = component;
            } else {
            	this.brandComponent[i] = "";
            }
        }
        this.brandLabel=brandSc.nextInt();
        brandSc.close();
        //取商品檢測資料
        Scanner goodSc = new Scanner(management);
        for (int i=0;i<6; i++) {
        	String component=goodSc.next();
            if (!component.equals("none")) {
            	this.goodComponent[i] = component;
            } else {
            	this.goodComponent[i] = "";
            }
        }
        this.goodLabel=goodSc.nextInt();
        this.logo=goodSc.nextInt();
        goodSc.close();
    }
    
    // 计算成分分数
    public int calcComponent() {

		int score = 0;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (goodComponent[2 * i].equals(brandComponent[2 * j])) {
					if (!goodComponent[2 * i].equals("")) {
						int target = Integer.parseInt(brandComponent[2 * j + 1]);
						int good = Integer.parseInt(goodComponent[2 * i + 1]);

						if (good >= target - 3 && good <= target + 3) {
							score = score + 10;
						} else if (good >= target - 5 && good <= target + 5) {
							score = score + 7;
						}
					} else {
						score += 10;
					}
				}
			}
		}
		return score;
	}

    // 计算Logo分数
	public int calcLogo() {
    	if(logo>=95) {
			return 20;
		}else if(logo>=90) {
			return 15;
		}else if(logo>=85) {
			return 10;
		}else {
			return 0;
		} 
    }

    // 计算防伪标签分数
    public int calcuAntiForgery() {
    	if(brandLabel==goodLabel) {
			return 50;
		}else {
			return 0;
		} 
    }

    // 计算总分
    public float calcTotal() {
        return calcComponent()+calcLogo()+calcuAntiForgery(); 
    }

    // 计算结果
    public String calcResult() {
    	
    	if(calcTotal()>=92) {
    		return "真";
    	}else {
    		return "假";
    	}
        
    }

    public ArrayList<String> returnFront() {
        // arraylist依序要回傳:0.成分分數 1.logo分數 2.防偽標籤分數 3.總分 4.結果
    	ArrayList<String> result = new ArrayList<String>();
    	result.add(calcComponent()+"");
    	result.add(calcLogo()+"");
    	result.add(calcuAntiForgery()+"");
    	result.add(calcTotal()+"");
    	result.add(calcResult()+"");
    	return result;
        
    }

    
}
