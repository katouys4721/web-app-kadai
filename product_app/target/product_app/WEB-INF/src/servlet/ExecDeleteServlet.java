package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.ProductDao;

//データ削除処理の実行
public class ExecDeleteServlet extends HttpServlet {
	// GETメソッドのリクエスト受信時に実行されるメソッド
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // リクエスト・レスポンスの設定
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        // フォームから送られたデータを取得
        String id = request.getParameter("id");

        // IDをString型→Int型に変換
        int iId;
        try {
            iId = Integer.parseInt(id);
        } catch (NumberFormatException e) { // 数値変換失敗時
            request.setAttribute("failureMessage", "内部エラーが発生しました。システム管理者に確認してください。");
            request.getRequestDispatcher("/list").forward(request, response);
            return;
        }

        // ここからデータ削除処理
        // 商品データ操作用DAOクラスのインスタンスを生成
        ProductDao product = new ProductDao();

        // 商品データを削除
        int rowCnt = product.delete(iId);

        // 結果に応じてメッセージを送信
        if (rowCnt > 0) { // 削除成功時
            request.setAttribute("successMessage", "商品を" + rowCnt + "件削除しました。");
        } else { // 削除失敗時
            request.setAttribute("failureMessage", "データベース処理エラーが発生しました。システム管理者に確認してください。");
        }

        // フォワードによる画面遷移
        request.getRequestDispatcher("/list").forward(request, response);
    }
}
