import java.text.NumberFormat;

class TaiKhoanThanhToan extends TaiKhoanNganHang {
    private String soThe;
    private double soTien;

    public TaiKhoanThanhToan(int id, String maTaiKhoan, String tenChuTaiKhoan, String ngayTao, String soThe, double soTien) {
        super(id, maTaiKhoan, tenChuTaiKhoan, ngayTao);
        this.soThe = soThe;
        this.soTien = soTien;
    }

    @Override
    public String toCSVString() {
        return String.format("%d,%s,%s,%s,%s,%.0f",
                id, maTaiKhoan, tenChuTaiKhoan, ngayTao, soThe, soTien);
    }

    @Override
    public String toString() {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        return String.format("%d,%s,%s,%s,%s,%s",
                id, maTaiKhoan, tenChuTaiKhoan, ngayTao, soThe, currencyFormat.format(soTien));
    }
}