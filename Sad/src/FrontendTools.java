import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

//import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class FrontendTools {
	static private String account;
	static private String password;
	static private String identity;

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public String getIdentity() {
        return identity;
    }
	
	public void initialize() throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(9009), 0);

        // 映射靜態檔案目錄
        server.createContext("/", new StaticFileHandler());
        
        // 設置伺服器處理程序
        server.createContext("/login", new LoginHandler());
        server.createContext("/brand", new BrandHandler());
        server.createContext("/management", new ManagementHandler());
        server.createContext("/logout", new LogoutHandler());
        server.createContext("/result", new ResultHandler());

        server.setExecutor(null);
        server.start();


	    System.out.println("Server is running");
	}
	

	 // 修改 StaticFileHandler 中的路徑處理邏輯
	    static class StaticFileHandler implements HttpHandler {
	        @Override
	        public void handle(HttpExchange exchange) throws IOException {
	            // 獲取請求的路徑
	            String path = exchange.getRequestURI().getPath();
	            System.out.println("Request received for path: " + path);
	            // 假設靜態檔案和 Java 程式碼在同一目錄中的 src 目錄下
	            File file = new File("sad.html");
//	           
	            if (file.exists() && file.isFile()) {
	                byte[] fileBytes = Files.readAllBytes(file.toPath());
	                exchange.sendResponseHeaders(200, fileBytes.length);
	                try (OutputStream os = exchange.getResponseBody()) {
	                    os.write(fileBytes);
	                }
	            } else {
	                // 如果檔案不存在，返回 404 Not Found
	                exchange.sendResponseHeaders(404, 0);
	                exchange.getResponseBody().close();
	            }
	        }
	    }
    
	 // 登入處理程序
	    static class LoginHandler implements HttpHandler {
	        @Override
	        public void handle(HttpExchange exchange) throws IOException {
	            AccountManager am = new AccountManager();
	            if ("POST".equals(exchange.getRequestMethod())) {
	                // 讀取 POST 資料
	                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
	                BufferedReader br = new BufferedReader(isr);
	                StringBuilder data = new StringBuilder();
	                String line;
	                while ((line = br.readLine()) != null) {
	                    data.append(line);
	                }
	                System.out.println(data);
	                // 解析 JSON 資料，假設資料格式為 {"identity":"yourIdentity","account":"yourAccount","password":"yourPassword"}
	                account = extractValue(data.toString(), "account");
	                password = extractValue(data.toString(), "password");
	                identity = extractValue(data.toString(), "identity");

	                // 使用 AccountManager 檢查帳號密碼是否存在
	                boolean accountExists = am.checkAccount(account, password, identity);

	                // 根據檢查結果回傳結果給前端
	                String response = "{\"success\":" + accountExists + "}";
	                exchange.sendResponseHeaders(200, response.length());
	                exchange.getResponseBody().write(response.getBytes());
	                
	                // 如果登入成功，設定屬性值
	                if (accountExists) {
	                    System.out.println("Login success! Identity: " + identity + ", Account: " + account + ", Password: " + password);
	                }
	            } else {
	                exchange.sendResponseHeaders(405, 0); // Method Not Allowed

	            }
	        }

	        // 提取 JSON 字串中特定欄位的值
	        private String extractValue(String json, String key) {
	            // 使用 key + ":\" 作為分隔符號，尋找欄位對應的值
	            String[] parts = json.split("\"" + key + "\":");

	            // 確保至少有一個分隔符號，並且找到了欄位
	            if (parts.length > 1) {
	                // 使用逗號分隔找到的值，以取得真正的數值部分
	                String[] valueParts = parts[1].split(",");

	                // 如果找到了數值部分，去掉雙引號、大括號和任何空白，並返回
	                if (valueParts.length > 0) {
	                    return valueParts[0].replaceAll("[\"\\{\\}\\s]", "").trim();
	                }
	            }

	            // 如果找不到，返回空字串
	            return "";
	        }
	    }
//	    // 提取 JSON 字串中特定欄位的值
//	    private static String extractValue(String json, String key) {
//	        String[] parts = json.split("\"" + key + "\":");
//	        if (parts.length > 1) {
//	            String[] valueParts = parts[1].split(",");
//	            if (valueParts.length > 0) {
//	                return valueParts[0].replace("\"", "").trim();
//	            }
//	        }
//	        return "";
//	    }

	    // 其他處理程序

	    
	    static class LogoutHandler implements HttpHandler {
	        @Override
	        public void handle(HttpExchange exchange) throws IOException {
	            // 清空 account、password、identity 的值
	            account = null;
	            password = null;
	            identity = null;

	            // 重新初始化
	            FrontendTools frontendTools = new FrontendTools();
	            frontendTools.initialize();

	            // 返回成功的回應
	            String response = "{\"success\":true}";
	            exchange.sendResponseHeaders(200, response.length());
	            exchange.getResponseBody().write(response.getBytes());
	        }
	    }

	    static class BrandHandler implements HttpHandler {
	        // 假設這些是用於存儲的靜態屬性
	        private static String goodsNumber;
	        private static String goodsInfo;  // 修改這裡，將檔案內容存儲為字串

	        @Override
	        public void handle(HttpExchange exchange) throws IOException {
	            if ("POST".equals(exchange.getRequestMethod())) {
	                // 獲取 POST 資料
	                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
	                BufferedReader br = new BufferedReader(isr);
	                StringBuilder data = new StringBuilder();
	                String line;
	                while ((line = br.readLine()) != null) {
	                    data.append(line);
	                }

	                // 解析 JSON 資料，假設資料格式為 {"goodsNumber":"yourGoodsNumber","goodsInfo":"yourGoodsInfo"}
	                String goodsNumber = extractValue(data.toString(), "goodsNumber");
	                String goodsInfo = extractValue(data.toString(), "goodsInfo");

	                // 檢查是否填寫完整
	                if (goodsNumber.isEmpty() || goodsInfo.isEmpty()) {
	                    sendResponse(exchange, "{\"success\":false}");
	                    System.out.println(goodsNumber);
	                    System.out.println(goodsInfo);
	                    System.out.println("empty");
	                    return;
	                }

	                
	                // 將資料存儲為靜態屬性
	                BrandHandler.goodsNumber = goodsNumber;
	                BrandHandler.goodsInfo = goodsInfo;

	                // 回傳成功的回應
	                sendResponse(exchange, "{\"success\":true}");
	                writeToFile(goodsInfo);
	                System.out.println(goodsInfo);
	            } else {
	                exchange.sendResponseHeaders(405, 0); // Method Not Allowed
	            }
	        }
	        
	        // ... 其他代碼
	        private void writeToFile(String goodsInfo) throws IOException {
	            // 設定檔案路徑，這裡假設檔案名稱為 brand.txt
	            Path filePath = Path.of("brand.txt");

	            // 如果檔案存在，先刪除舊檔案
	            if (Files.exists(filePath)) {
	                Files.delete(filePath);
	            }

	            // 去除 goodsInfo 結尾的大括號
	            goodsInfo = goodsInfo.trim();
	            if (goodsInfo.endsWith("}")) {
	                goodsInfo = goodsInfo.substring(0, goodsInfo.length() - 1);
	            }

	            // 將資料寫入檔案
	            String data = goodsInfo;
	            Files.write(filePath, data.getBytes(), StandardOpenOption.CREATE);
	        }

	        // 提取 JSON 字串中特定欄位的值
	        private String extractValue(String json, String key) {
	            String[] parts = json.split("\"" + key + "\":");
	            if (parts.length > 1) {
	                String[] valueParts = parts[1].split(",");
	                if (valueParts.length > 0) {
	                    return valueParts[0].replace("\"", "").trim();
	                }
	            }
	            return "";
	        }

	        // 發送回應
	        private void sendResponse(HttpExchange exchange, String response) throws IOException {
	            exchange.getResponseHeaders().set("Content-Type", "application/json");
	            exchange.sendResponseHeaders(200, response.length());
	            try (OutputStream os = exchange.getResponseBody()) {
	                os.write(response.getBytes(StandardCharsets.UTF_8));
	            }
	        }
	    }

	    public class ManagementHandler implements HttpHandler {

	        @Override
	        public void handle(HttpExchange exchange) throws IOException {
	            if ("POST".equals(exchange.getRequestMethod())) {
	                // 獲取 POST 資料
	                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
	                BufferedReader br = new BufferedReader(isr);
	                StringBuilder data = new StringBuilder();
	                String line;
	                while ((line = br.readLine()) != null) {
	                    data.append(line);
	                }

	                // 解析 JSON 資料，假設資料格式為 {"supplier":"yourSupplier","goodsNumber":"yourGoodsNumber","goodsInfo":"yourGoodsInfo"}
	                String supplier = extractValue(data.toString(), "supplier");
	                String goodsNumber = extractValue(data.toString(), "goodsNumber");
	                String goodsInfo = extractValue(data.toString(), "goodsInfo");

	                // 檢查是否填寫完整
	                if (supplier.isEmpty() || goodsNumber.isEmpty() || goodsInfo.isEmpty()) {
	                    sendResponse(exchange, "{\"success\":false}");
	                    System.out.println(supplier);
	                    System.out.println(goodsNumber);
	                    System.out.println(goodsInfo);
	                    System.out.println("empty");
	                    return;
	                }

	                
	                
	                // 將資料存儲為靜態屬性或進行其他相應的處理
	                // ...

	                // 回傳成功的回應
	                sendResponse(exchange, "{\"success\":true}");
	                writeToFile(goodsInfo);
	                System.out.println(goodsInfo);
	                
	                
	                
	                 // 解析結果，提取分數和檢測結果
//	                    Map<String, String> scores = parseResult(res);
//
////	                 // 將分數和檢測結果包裝成 YourResponseObject 對象
////	                    YourResponseObject responseObject = new YourResponseObject();
////	                    responseObject.setSuccess(true);
////
////	                    YourScores yourScores = new YourScores(aj.calcComponent(),aj.calcLogo(),aj.calcuAntiForgery());
////	                    yourScores.setComponent(Integer.parseInt(scores.get("component")));
////	                    yourScores.setLogo(Integer.parseInt(scores.get("logo")));
////	                    yourScores.setAntiForgeryLabel(Integer.parseInt(scores.get("antiForgeryLabel")));
////	                    yourScores.setTotal(Integer.parseInt(scores.get("total")));
////
////	                    responseObject.setScores(yourScores);
////	                    responseObject.setResult(scores.get("result"));
////
////	                    // 轉換 YourResponseObject 對象為 JSON 字串
////	                    String responseJson = new ObjectMapper().writeValueAsString(responseObject);
//
////	                    // 將回應傳送到前端
////	                    sendResponse(exchange, responseJson);
//	                    
//	                    // 將分數和檢測結果包裝成 JSON 格式的字串
//	                    String scoresJson = "{\"component\":" + scores.get("component") + ","
//	                            + "\"logo\":" + scores.get("logo") + ","
//	                            + "\"antiForgeryLabel\":" + scores.get("antiForgeryLabel") + ","
//	                            + "\"total\":" + scores.get("total") + "}";
//	                    String resultJson = "\"result\":\"" + scores.get("result") + "\"";
//
//	                    // 合併分數和檢測結果的 JSON 字串
//	                    String responseJson = "{\"success\":true,\"scores\":{" + scoresJson + "}," + resultJson + "}";
//
//	                    // 將回應傳送到前端
//	                    sendResponse(exchange, responseJson);
//	                    System.out.print(responseJson);
	                
	            } else {
	                exchange.sendResponseHeaders(405, 0); // Method Not Allowed
	            }
	        }

	        // ... 其他代碼

	        // 提取 JSON 字串中特定欄位的值
	        private String extractValue(String json, String key) {
	            String[] parts = json.split("\"" + key + "\":");
	            if (parts.length > 1) {
	                String[] valueParts = parts[1].split(",");
	                if (valueParts.length > 0) {
	                    return valueParts[0].replace("\"", "").trim();
	                }
	            }
	            return "";
	        }

	        // 與 BrandHandler 中的 writeToFile 方法相同
	        private void writeToFile(String goodsInfo) throws IOException {
	            // 設定檔案路徑，這裡假設檔案名稱為 management.txt
	            Path filePath = Path.of("management.txt");

	            // 如果檔案存在，先刪除舊檔案
	            if (Files.exists(filePath)) {
	                Files.delete(filePath);
	            }
	            
	            goodsInfo = goodsInfo.trim();
	            if (goodsInfo.endsWith("}")) {
	                goodsInfo = goodsInfo.substring(0, goodsInfo.length() - 1);
	            }

	            // 將資料寫入檔案
	            String data = goodsInfo;
	            Files.write(filePath, data.getBytes(), StandardOpenOption.CREATE);
	        }

	        // 與 BrandHandler 中的 sendResponse 方法相同
	        private void sendResponse(HttpExchange exchange, String response) throws IOException {
	            exchange.getResponseHeaders().set("Content-Type", "application/json");
	            exchange.sendResponseHeaders(200, response.length());
	            try (OutputStream os = exchange.getResponseBody()) {
	                os.write(response.getBytes(StandardCharsets.UTF_8));
	            }
	        }
	        
	     // 解析結果字串，提取分數和檢測結果
	        private Map<String, String> parseResult(String result) {
	            Map<String, String> scores = new HashMap<>();
	            String[] lines = result.split("\n");
	            for (String line : lines) {
	                if (line.contains("分數")) {
	                    String[] parts = line.split(":");
	                    if (parts.length == 2) {
	                        String key = parts[0].trim();
	                        String value = parts[1].trim();
	                        scores.put(key, value);
	                    }
	                } else if (line.contains("檢測結果")) {
	                    String[] parts = line.split(":");
	                    if (parts.length == 2) {
	                        String key = "result";
	                        String value =  parts[1].trim();
	                        scores.put(key, value);
	                    }
	                }
	            }
	            return scores;
	        }
	    }
	    
	    public class ResultHandler implements HttpHandler {
	        @Override
	        public void handle(HttpExchange exchange) throws IOException {
	            File inputFile1 = new File("brand.txt");
	            File inputFile2 = new File("management.txt");
	            AuthenticityJudgment aj = new AuthenticityJudgment(inputFile1, inputFile2);

	            // 準備純文字內容
	            StringBuilder resultText = new StringBuilder();
	            for (String value : aj.returnFront()) {
	                resultText.append(value).append(" ");
	            }

	            // 移除最後一個空格
	            if (resultText.length() > 0) {
	                resultText.setLength(resultText.length() - 1);
	            }

	            // 設置回應標頭
	            exchange.getResponseHeaders().set("Content-Type", "text/plain");

	            // 發送純文字回應，指定 Content-Length
	            byte[] responseBytes = resultText.toString().getBytes(StandardCharsets.UTF_8);
	            exchange.sendResponseHeaders(200, responseBytes.length);

	            // 獲取 OutputStream 並一次性寫入純文字內容
	            try (OutputStream os = exchange.getResponseBody()) {
	                os.write(responseBytes);
	                os.flush();
	            } catch (Exception e) {
	                System.out.print(e);
	            }
	        }
	    }

}
