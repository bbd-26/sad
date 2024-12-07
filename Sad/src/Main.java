import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    private static int counter = 0;

    public static void main(String[] args) throws IOException {
//        HttpServer server = HttpServer.create(new InetSocketAddress(9001), 0);
//		
//        // 映射靜態檔案目錄
//        server.createContext("/", new StaticFileHandler());
//        
//        // 設置伺服器處理程序
//        server.createContext("/login", new LoginHandler());
//        server.createContext("/brand", new BrandHandler());
//        server.createContext("/management", new ManagementHandler());
//
//        server.setExecutor(null);
//        server.start();
    	FrontendTools ft = new FrontendTools();
    	ft.initialize();
//    	AccountManager am = new AccountManager();
//    	am.createDirec("gu");
    	 File inputFile1 = new File("brand.txt");
         File inputFile2 = new File("management.txt");
         AuthenticityJudgment aj = new AuthenticityJudgment(inputFile1,inputFile2);
         String result = "";
         for (String value : aj.returnFront()) {
         	result += value;
             System.out.println(value);
    	   
        // 示例用法
         }
    }

 // 修改 StaticFileHandler 中的路徑處理邏輯
//    static class StaticFileHandler implements HttpHandler {
//        @Override
//        public void handle(HttpExchange exchange) throws IOException {
//            // 獲取請求的路徑
//            String path = exchange.getRequestURI().getPath();
//            System.out.println("Request received for path: " + path);
//            // 假設靜態檔案和 Java 程式碼在同一目錄中的 src 目錄下
//            File file = new File("sad.html");
////           
//            if (file.exists() && file.isFile()) {
//                byte[] fileBytes = Files.readAllBytes(file.toPath());
//                exchange.sendResponseHeaders(200, fileBytes.length);
//                try (OutputStream os = exchange.getResponseBody()) {
//                    os.write(fileBytes);
//                }
//            } else {
//                // 如果檔案不存在，返回 404 Not Found
//                exchange.sendResponseHeaders(404, 0);
//                exchange.getResponseBody().close();
//            }
//        }
//    }
//
//
//    static class GetCounterHandler implements HttpHandler {
//        @Override
//        public void handle(HttpExchange exchange) throws IOException {
//            String response = String.valueOf(counter);
//            sendResponse(exchange, response);
//        }
//    }
//
//    static class IncrementCounterHandler implements HttpHandler {
//        @Override
//        public void handle(HttpExchange exchange) throws IOException {
//            counter++;
//            String response = String.valueOf(counter);
//            sendResponse(exchange, response);
//        }
//    }
//
//    private static void sendResponse(HttpExchange exchange, String response) throws IOException {
//        exchange.sendResponseHeaders(200, response.length());
//        try (OutputStream os = exchange.getResponseBody()) {
//            os.write(response.getBytes());
//        }
//    }
//    
// // 登入處理程序
//    static class LoginHandler implements HttpHandler {
//        @Override
//        public void handle(HttpExchange exchange) throws IOException {
//            if ("POST".equals(exchange.getRequestMethod())) {
//                // 讀取 POST 資料
//                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
//                BufferedReader br = new BufferedReader(isr);
//                StringBuilder data = new StringBuilder();
//                String line;
//                while ((line = br.readLine()) != null) {
//                    data.append(line);
//                }
//
//                // 在後端印出登入資訊
//                System.out.println("Login success!");
//                System.out.println("收到的資料：" + data.toString());
//
//                // 回傳 JSON 回應
//                String response = "{\"success\":true,\"account\":\"" + extractValue(data.toString(), "account") +
//                        "\",\"password\":\"" + extractValue(data.toString(), "password") + "\"}";
//                exchange.sendResponseHeaders(200, response.length());
//                try (OutputStream os = exchange.getResponseBody()) {
//                    os.write(response.getBytes());
//                }
//            } else {
//                exchange.sendResponseHeaders(405, 0); // Method Not Allowed
//            }
//        }
//    }
//
//    // 提取 JSON 字串中特定欄位的值
//    private static String extractValue(String json, String key) {
//        String[] parts = json.split("\"" + key + "\":");
//        if (parts.length > 1) {
//            String[] valueParts = parts[1].split(",");
//            if (valueParts.length > 0) {
//                return valueParts[0].replace("\"", "").trim();
//            }
//        }
//        return "";
//    }
//
//    // 其他處理程序
//    static class BrandHandler implements HttpHandler {
//        @Override
//        public void handle(HttpExchange exchange) throws IOException {
//            // 執行品牌相關的邏輯
//        }
//    }
//
//    static class ManagementHandler implements HttpHandler {
//        @Override
//        public void handle(HttpExchange exchange) throws IOException {
//            // 執行管理相關的邏輯
//        }
//    }
}


