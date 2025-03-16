package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.ProductDao;
import data.ProductDto;
import data.VendorDao;
import data.VendorDto;

//商品編集ページの表示
public class DispEditServlet extends HttpServlet {
	// データ取得成功時の遷移先（商品編集ページ）
    private static final String SUCCESS_PAGE = "/WEB-INF/jsp/editPage.jsp";
    // データ取得失敗時の遷移先（元の商品一覧ページ）
    private static final String FAILURE_PAGE = "/WEB-INF/jsp/listPage.jsp";

    // GETメソッドのリクエスト受信時に実行されるメソッド
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // リクエスト・レスポンスの設定
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        // JSPからのリクエストデータ取得
        String id = request.getParameter("id");
        id = Objects.toString(id, ""); // NULLは空文字に置き換え

        // IDをString型→Int型に変換
        int iId;
        try {
            iId = Integer.parseInt(id);
        } catch (NumberFormatException e) { // 数値変換失敗時
            request.setAttribute("failureMessage", "内部エラーが発生しました。システム管理者に確認してください。");
            request.getRequestDispatcher(FAILURE_PAGE).forward(request, response);
            return;
        }

        // 商品データリスト・仕入先データリストのインスタンスを生成
        ArrayList<ProductDto> productList = new ArrayList<ProductDto>();
        ArrayList<VendorDto> vendorList = new ArrayList<VendorDto>();

        // 商品データ・仕入先データ操作用DAOクラスのインスタンスを生成
        ProductDao product = new ProductDao();
        VendorDao vendor = new VendorDao();

        try {
            // 商品データの一覧を取得（ID指定あり）
            productList = product.read(iId, "", "");

            // 仕入先データの一覧を取得
            vendorList = vendor.read();

        } catch (SQLException e) { // データベース処理の例外発生時
            request.setAttribute("failureMessage", "データベース処理エラーが発生しました。システム管理者に確認してください。");
            request.getRequestDispatcher(FAILURE_PAGE).forward(request, response);
            return;
        }

        // JSPに送信するデータの設定
        if (productList.isEmpty()) {
            // 商品データリストが空だった場合はメッセージを送る
            request.setAttribute("failureMessage", "該当する商品データが見つかりませんでした。");
        } else {
            // 取得した商品データリストを送る
            request.setAttribute("productList", productList);
        }

        if (vendorList.isEmpty()) {
            // 仕入先データリストが空だった場合はメッセージを送る
            request.setAttribute("failureMessage", "仕入先コードが1件も登録されていません。");
        } else {
            // 取得した仕入先データリストを送る
            request.setAttribute("vendorList", vendorList);
        }

        // フォワードによる画面遷移
        request.getRequestDispatcher(SUCCESS_PAGE).forward(request, response);
    }
}
