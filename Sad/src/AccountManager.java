import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

//管理帳號密碼
public class AccountManager {
    private File dataFile = new File("password.txt");

    //檢查帳號密碼是否輸入正確
    public boolean checkAccount(String account, String password, String identity) {
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 拆分每一行，預期格式為 "account password"
                String[] parts = line.split(" ");
                if (parts.length == 3 && parts[0].equals(account) && parts[1].equals(password) && parts[2].equalsIgnoreCase(identity)) {
                    // 找到符合的帳號和密碼
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 找不到符合的帳號和密碼
        return false;
    }
    
    public void printFirstLine() {
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String firstLine = reader.readLine();
            if (firstLine != null) {
                System.out.println("First Line: " + firstLine);
            } else {
                System.out.println("The file is empty.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void createDirec(String brand) {
    	// 設定父資料夾的路徑
        String parentDirectoryPath = System.getProperty("user.dir"); // 獲取當前工作目錄

        // 設定 brand 資料夾的名稱
        String brandDirectoryName = "brands";

        // 設定要創建的子資料夾的名稱
        String subdirectoryName = brand;

        // 創建 File 物件表示 brand 資料夾的路徑
        File brandDirectory = new File(parentDirectoryPath, brandDirectoryName);
        System.err.println(brandDirectory.getPath());
        // 確保 brand 資料夾存在
        if (!brandDirectory.exists() || !brandDirectory.isDirectory()) {
            System.err.println("brand 資料夾不存在。");
            return;
        }

        // 創建 File 物件表示新的子資料夾的路徑
        File newSubdirectory = new File(brandDirectory, subdirectoryName);

        // 使用 mkdir 方法創建新的子資料夾
        boolean success = newSubdirectory.mkdir();

        if (success) {
            System.out.println("新的子資料夾創建成功。");
        } else {
            System.err.println("新的子資料夾創建失敗。");
        }
    }
}
