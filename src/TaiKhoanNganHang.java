abstract class TaiKhoanNganHang {
    protected int id;
    protected String maTaiKhoan;
    protected String tenChuTaiKhoan;
    protected String ngayTao;

    public TaiKhoanNganHang(int id, String maTaiKhoan, String tenChuTaiKhoan, String ngayTao) {
        this.id = id;
        this.maTaiKhoan = maTaiKhoan;
        this.tenChuTaiKhoan = tenChuTaiKhoan;
        this.ngayTao = ngayTao;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getMaTaiKhoan() {
        return maTaiKhoan;
    }
    public void setMaTaiKhoan(String maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }
    public String getTenChuTaiKhoan() {
        return tenChuTaiKhoan;
    }
    public void setTenChuTaiKhoan(String tenChuTaiKhoan) {
        this.tenChuTaiKhoan = tenChuTaiKhoan;
    }

    public String getNgayTao() {
        return ngayTao;
    }
    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }
    public abstract String toCSVString();
    @Override
    public abstract String toString();
}
