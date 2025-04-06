import java.text.NumberFormat;

class TaiKhoanTietKiem extends TaiKhoanNganHang {
    private double soTienGui;
    private String ngayGui;
    private double laiSuat;
    private int kyHan;

    public TaiKhoanTietKiem(int id, String maTaiKhoan, String tenChuTaiKhoan, String ngayTao,
                            double soTienGui, String ngayGui, double laiSuat, int kyHan) {
        super(id, maTaiKhoan, tenChuTaiKhoan, ngayTao);
        this.soTienGui = soTienGui;
        this.ngayGui = ngayGui;
        this.laiSuat = laiSuat;
        this.kyHan = kyHan;
    }

    @Override
    public String toCSVString() {
        return String.format("%d,%s,%s,%s,%.0f,%s,%.1f,%d",
                id, maTaiKhoan, tenChuTaiKhoan, ngayTao, soTienGui, ngayGui, laiSuat, kyHan);
    }

    @Override
    public String toString() {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        return String.format("%d,%s,%s,%s,%s,%s,%.1f%%,%d",
                id, maTaiKhoan, tenChuTaiKhoan, ngayTao,
                currencyFormat.format(soTienGui), ngayGui, laiSuat, kyHan);
    }
}
