package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.ProductDao;
import data.ProductDto;

//データ更新処理の実行
public class ExecUpdateServlet extends HttpServlet {
	// 更新成功時の遷移先（商品一覧ページ）
    private static final String SUCCESS_PAGE = "/list";
    // 更新失敗時の遷移先（元の商品編集ページ）
    private static final String FAILURE_PAGE = "/WEB-INF/jsp/editPage.jsp";

    // POSTメソッドのリクエスト受信時に実行されるメソッド
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // リクエスト・レスポンスの設定
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        // フォームから送られたデータを取得
        String id = request.getParameter("id");
        String productCode = request.getParameter("product_code");
        String productName = request.getParameter("product_name");
        String price = request.getParameter("price");
        String stockQuantity = request.getParameter("stock_quantity");
        String vendorCode = request.getParameter("vendor_code");

        // 数値型カラムのデータはString型→Int型に変換
        int iId, iProductCode, iPrice, iStockQuantity, iVendorCode;
        try {
            iId = Integer.parseInt(id); // ID
            iProductCode = Integer.parseInt(productCode); // 商品コード
            iPrice = Integer.parseInt(price); // 単価
            iStockQuantity = Integer.parseInt(stockQuantity); // 在庫数
            iVendorCode = Integer.parseInt(vendorCode); // 仕入先コード
        } catch (NumberFormatException e) { // 数値変換失敗時
            forwardFailure("商品名以外の項目には、有効な数値を入力してください。", request, response);
            return;
        }

        // 商品名が正しく取得できている（NULLや空文字でない）かチェック
        if (productName == null || productName.isEmpty()) {
            forwardFailure("有効な商品名を入力してください。", request, response);
            return;
        }

        // ここからデータ更新処理
        // DTOのインスタンスを生成し、各カラムのデータをセット
        ProductDto data = new ProductDto();
        data.setId(iId);
        data.setProductCode(iProductCode);
        data.setProductName(productName);
        data.setPrice(iPrice);
        data.setStockQuantity(iStockQuantity);
        data.setVendorCode(iVendorCode);

        // 商品データ操作用DAOクラスのインスタンスを生成
        ProductDao product = new ProductDao();

        // 商品データを更新
        int rowCnt = product.update(data);

        // 結果に応じてメッセージを送信
        if (rowCnt > 0) { // 更新成功時
            forwardSuccess("商品を" + rowCnt + "件編集しました。", request, response);
        } else { // 更新失敗時
            forwardFailure("データベース処理エラーが発生しました。システム管理者に確認してください。", request, response);
        }
    }

    // 成功時の画面遷移（フォワード）
    private void forwardSuccess(String message, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 成功メッセージをリクエストスコープに保存して画面遷移
        request.setAttribute("successMessage", message);
        request.getRequestDispatcher(SUCCESS_PAGE).forward(request, response);
    }

    // 失敗時の画面遷移（フォワード）
    private void forwardFailure(String message, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 失敗メッセージをリクエストスコープに保存して画面遷移
        request.setAttribute("failureMessage", message);
        // 画面遷移
        request.getRequestDispatcher(FAILURE_PAGE).forward(request, response);
    }
}
