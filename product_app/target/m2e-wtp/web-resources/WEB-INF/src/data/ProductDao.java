package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

//商品データのデータベース操作
public class ProductDao {
//	接続用の情報をフィールドに定数として定義
	private static final String URL = "jdbc:mariadb://olxl65dqfuqr6s4y.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/nzcq7ggaflmn6m2n";
	private static final String USER_NAME = "lknkulydrcaxekou";
	private static final String PASSWORD = "tzx5fgue61y9ikpb";
	
//	商品データの作成
	public int create(ProductDto data) {
		int rowCnt = 0;	//更新レコード数
		
//		INSERT文のフォーマット
		String sql = "INSERT INTO products(" +
				"  product_code, product_name, price, stock_quantity, vendor_code" +
				") VALUES(?, ?, ?, ?, ?);";
		
//		データベース接続＆SQL文の送信準備
		try (Connection con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
				PreparedStatement statement = con.prepareStatement(sql)){
//			SQL文の？を更新するデータで置き換える
			statement.setInt(1, data.getProductCode());
			statement.setString(2, data.getProductName());
			statement.setInt(3, data.getPrice());
			statement.setInt(4, data.getStockQuantity());
			statement.setInt(5, data.getVendorCode());
			
//			SQL文を実行
			rowCnt = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		}
		
		return rowCnt;
	}
	
//	商品データの読み取り
	public ArrayList<ProductDto> read(int id, String order, String keyword)
		throws SQLException {
//		SELECT文のフォーマット
		String sql = "SELECT * FROM products";
		
//		取得したデータを格納するためのリスト
		ArrayList<ProductDto> dataList = new ArrayList<ProductDto>();
		
//		文字列のNULL対策（データがなければ空文字に置き換え）
		order = Objects.toString(order, "");
		keyword = Objects.toString(keyword, "");
		
//		データべース接続
		try (Connection con = DriverManager.getConnection(URL,USER_NAME, PASSWORD)){
//			引数に併せてSELECT文に条件を付加
			if(id > 0) {
//				IDが指定されている場合
				sql += " WHERE id = ?;";
			} else {
//				検索ワードが存在する場合
				if(!keyword.isEmpty()) {
					sql += " WHERE product_name LIKE ?";
				}
				
//				並び替え方向に合わせてORDER BY句を付加
				if("asc".equals(order)) {
					sql += " ORDER BY updated_at ASC";
				} else if("desc".equals(order)) {
					sql += " ORDER BY updated_at DESC";
				}
//				文末にセミコロン付加
				sql += ";";
			}
			
//			SQL文の送信準備
			try(PreparedStatement statement = con.prepareStatement(sql)){
//				?があれば置き換え
				if(id > 0) {
					statement.setInt(1, id);
				} else if(!keyword.isEmpty()) {
//					検索ワードに置き換え
					statement.setString(1, "%" + keyword + "%");
				}
				
//				SQL文を実行
				ResultSet result = statement.executeQuery();
				
//				SQLクエリの実行結果を抽出
				while(result.next()){
//					DTOのインスタンスに各カラムのデータをセット
					ProductDto productData = new ProductDto();
					productData.setId(result.getInt("id"));
					productData.setProductCode(result.getInt("product_code"));
                    productData.setProductName(result.getString("product_name"));
                    productData.setPrice(result.getInt("price"));
                    productData.setStockQuantity(result.getInt("stock_quantity"));
                    productData.setVendorCode(result.getInt("vendor_code"));
                    
//                  リストにデータを追加
                    dataList.add(productData);
				}
			}
		}
		
		return dataList;
	}
	
//	商品データの更新
	public int update(ProductDto data) {
		int rowCnt = 0;
		
//		UPDATE文のフォーマット
		String sql = "UPDATE products " +
				"SET product_code = ?, " +
				"	product_name = ?, " +
				"	price = ?, " +
				"	stock_quantity = ?, " +
				"	vendor_code = ? " +
				"WHERE id = ?;";
		
//		データベース接続＆SQL文送信準備
		try (Connection con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
				PreparedStatement statement = con.prepareStatement(sql)){
//			SQL文の？を更新するデータで置き換える
			statement.setInt(1, data.getProductCode());
			statement.setString(2, data.getProductName());
			statement.setInt(3, data.getPrice());
            statement.setInt(4, data.getStockQuantity());
            statement.setInt(5, data.getVendorCode());
            statement.setInt(6, data.getId());
            
//          SQL文を実行
            rowCnt = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("エラー発生:" + e.getMessage());
		}
		
		return rowCnt;
	}
	
//	商品データの削除
	public int delete(int id) {
		int rowCnt = 0;
		
//		DELETE文のフォーマット
		String sql = "DELETE FROM products WHERE id = ?;";
		
//		データベース接続＆SQL文の送信準備
		try(Connection con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
				PreparedStatement statement = con.prepareStatement(sql)){
//			SQL文の？を更新するデータで置き換える
			statement.setInt(1, id);
			
//			SQL文を実行
			rowCnt = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		}
		
		return rowCnt;
	}
}
