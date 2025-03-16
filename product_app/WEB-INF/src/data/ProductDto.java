package data;

//商品データの受け渡し
public class ProductDto {
	private int id = 0;					//ID
	private int productCode = 0;		//商品コード
	private String productName = "";	//商品名
	private int price = 0;				//単価
	private int stockQuantity = 0;		//在庫数
	private int vendorCode = 0;			//仕入れ先コード
	
//	各カラムのゲッターとセッター
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getProductCode() {
		return this.productCode;
	}
	public void setProductCode(int productCode) {
		this.productCode = productCode;
	}

    public String getProductName() {
        return this.productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return this.price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return this.stockQuantity;
    }
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getVendorCode() {
        return this.vendorCode;
    }
    public void setVendorCode(int vendorCode) {
        this.vendorCode = vendorCode;
    }
}
