import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.io.*;
import java.nio.file.*;
import java.text.*;
import java.util.*;

public class QuanLyTaiKhoan {
    private static final String CSV_FILE = "data/bank_accounts.csv";
    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        createDataDirectory();

        while (true) {
            displayMenu();
            int choice = Integer.parseInt(scanner.nextLine().trim());

            switch (choice) {
                case 1:
                    themMoiTaiKhoan();
                    break;
                case 2:
                    xoaTaiKhoan();
                    break;
                case 3:
                    hienThiDanhSach();
                    break;
                case 4:
                    timKiemTaiKhoan();
                    break;
                case 5:
                    System.out.println("Thoát chương trình.");
                    System.exit(0);
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        }
    }

    private static void createDataDirectory() {
        try {
            Files.createDirectories(Paths.get("data"));
        } catch (IOException e) {
            System.out.println("Không thể tạo thư mục data: " + e.getMessage());
        }
    }

    private static void displayMenu() {
        System.out.println("\n===== QUẢN LÝ TÀI KHOẢN NGÂN HÀNG =====");
        System.out.println("1. Thêm mới tài khoản");
        System.out.println("2. Xóa tài khoản");
        System.out.println("3. Xem danh sách tài khoản");
        System.out.println("4. Tìm kiếm tài khoản");
        System.out.println("5. Thoát");
    }

    private static void themMoiTaiKhoan() {
        System.out.println("\n===== THÊM MỚI TÀI KHOẢN =====");

        try {
            System.out.print("Nhập mã tài khoản: ");
            String maTaiKhoan = scanner.nextLine().trim();
            while (maTaiKhoan.isEmpty()) {
                System.out.println("Trường này là bắt buộc.");
                System.out.print("Nhập lại mã tài khoản: ");
                maTaiKhoan = scanner.nextLine().trim();
            }

            System.out.print("Nhập tên chủ tài khoản: ");
            String tenChuTaiKhoan = scanner.nextLine().trim();
            while (tenChuTaiKhoan.isEmpty()) {
                System.out.println("Trường này là bắt buộc.");
                System.out.print("Nhập lại tên chủ tài khoản: ");
                tenChuTaiKhoan = scanner.nextLine().trim();
            }

            String ngayTao;
            while (true) {
                System.out.print("Nhập ngày tạo tài khoản (dd/MM/yyyy): ");
                ngayTao = scanner.nextLine().trim();
                try {
                    dateFormat.parse(ngayTao);
                    break;
                } catch (ParseException e) {
                    System.out.println("Ngày không hợp lệ. Vui lòng nhập đúng định dạng.");
                }
            }

            System.out.println("Loại tài khoản:");
            System.out.println("1. Tiết kiệm");
            System.out.println("2. Thanh toán");

            System.out.print("Chọn loại (1-2): ");
            int loai = Integer.parseInt(scanner.nextLine().trim());
            TaiKhoanNganHang account;
            int newId = getNextId();

            if (loai == 1) {
                System.out.print("Nhập số tiền gửi tiết kiệm: ");
                double soTienGui = Double.parseDouble(scanner.nextLine().trim());

                String ngayGui;
                while (true) {
                    System.out.print("Nhập ngày gửi tiết kiệm (dd/MM/yyyy): ");
                    ngayGui = scanner.nextLine().trim();
                    try {
                        dateFormat.parse(ngayGui);
                        break;
                    } catch (ParseException e) {
                        System.out.println("Ngày không hợp lệ. Vui lòng nhập đúng định dạng.");
                    }
                }

                System.out.print("Nhập lãi suất (%): ");
                double laiSuat = Double.parseDouble(scanner.nextLine().trim());

                System.out.print("Nhập kỳ hạn (tháng): ");
                int kyHan = Integer.parseInt(scanner.nextLine().trim());

                account = new TaiKhoanTietKiem(newId, maTaiKhoan, tenChuTaiKhoan, ngayTao,
                        soTienGui, ngayGui, laiSuat, kyHan);
            } else if (loai == 2) {
                System.out.print("Nhập số thẻ: ");
                String soThe = scanner.nextLine().trim();
                while (soThe.isEmpty()) {
                    System.out.println("Trường này là bắt buộc.");
                    System.out.print("Nhập lại số thẻ: ");
                    soThe = scanner.nextLine().trim();
                }

                System.out.print("Nhập số tiền trong tài khoản: ");
                double soTien = Double.parseDouble(scanner.nextLine().trim());

                account = new TaiKhoanThanhToan(newId, maTaiKhoan, tenChuTaiKhoan, ngayTao,
                        soThe, soTien);
            } else {
                System.out.println("Loại tài khoản không hợp lệ.");
                return;
            }

            saveAccountToCSV(account);
            System.out.println("Thêm mới tài khoản thành công!");
        } catch (Exception e) {
            System.out.println("Lỗi khi thêm mới: " + e.getMessage());
        }
    }

    private static int getNextId() {
        try {
            Path csvPath = Paths.get(CSV_FILE);
            if (!Files.exists(csvPath)) return 1;
            List<String> lines = Files.readAllLines(csvPath);
            if (lines.isEmpty()) return 1;
            String[] parts = lines.get(lines.size() - 1).split(",");
            return Integer.parseInt(parts[0]) + 1;
        } catch (IOException e) {
            return 1;
        }
    }

    private static void saveAccountToCSV(TaiKhoanNganHang account) throws IOException {
        Path csvPath = Paths.get(CSV_FILE);
        try (BufferedWriter writer = Files.newBufferedWriter(csvPath,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(account.toCSVString());
            writer.newLine();
        }
    }

    private static void xoaTaiKhoan() {
        System.out.println("\n===== XÓA TÀI KHOẢN =====");
        System.out.print("Nhập mã tài khoản cần xóa: ");
        String maTaiKhoan = scanner.nextLine().trim();

        try {
            List<String> lines = Files.readAllLines(Paths.get(CSV_FILE));
            boolean found = false;
            List<String> newLines = new ArrayList<>();

            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts[1].equals(maTaiKhoan)) {
                    found = true;
                    TaiKhoanNganHang account = createAccountFromCSV(line);
                    System.out.println("Tìm thấy tài khoản:");
                    System.out.println(account);

                    System.out.print("Bạn có chắc chắn muốn xóa? (y/n): ");
                    String confirm = scanner.nextLine().trim();
                    if (confirm.equalsIgnoreCase("y")) {
                        System.out.println("Đã xóa tài khoản mã " + maTaiKhoan);
                        continue;
                    } else {
                        System.out.println("Hủy xóa tài khoản.");
                    }
                }
                newLines.add(line);
            }

            if (!found) {
                throw new NotFoundBankAccountException("Tài khoản không tồn tại.");
            }

            Files.write(Paths.get(CSV_FILE), newLines);
        } catch (NotFoundBankAccountException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Lỗi khi xóa tài khoản: " + e.getMessage());
        }
    }

    private static void hienThiDanhSach() {
        System.out.println("\n===== DANH SÁCH TÀI KHOẢN =====");

        try {
            List<String> lines = Files.readAllLines(Paths.get(CSV_FILE));
            if (lines.isEmpty()) {
                System.out.println("Không có tài khoản nào trong danh sách.");
                return;
            }

            for (String line : lines) {
                TaiKhoanNganHang account = createAccountFromCSV(line);
                System.out.println(account);

            }
        } catch (Exception e) {
            System.out.println("Lỗi khi đọc danh sách: " + e.getMessage());
        }
    }

    private static void timKiemTaiKhoan() {
        System.out.println("\n===== TÌM KIẾM TÀI KHOẢN =====");
        System.out.print("Nhập từ khóa tìm kiếm: ");
        String keyword = scanner.nextLine().trim();

        try {
            List<String> lines = Files.readAllLines(Paths.get(CSV_FILE));
            boolean found = false;

            for (String line : lines) {
                TaiKhoanNganHang account = createAccountFromCSV(line);
                if (account.getMaTaiKhoan().contains(keyword) ||
                        account.getTenChuTaiKhoan().toLowerCase().contains(keyword.toLowerCase())) {
                    if (!found) {
                        System.out.println("Kết quả tìm kiếm:");
                        found = true;
                    }
                    System.out.println(account);
                }
            }

            if (!found) {
                System.out.println("Không tìm thấy tài khoản phù hợp.");
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi tìm kiếm: " + e.getMessage());
        }
    }

    private static TaiKhoanNganHang createAccountFromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        int id = Integer.parseInt(parts[0]);
        String maTaiKhoan = parts[1];
        String tenChuTaiKhoan = parts[2];
        String ngayTao = parts[3];

        if (parts.length == 8) {
            double soTienGui = Double.parseDouble(parts[4]);
            String ngayGui = parts[5];
            double laiSuat = Double.parseDouble(parts[6]);
            int kyHan = Integer.parseInt(parts[7]);
            return new TaiKhoanTietKiem(id, maTaiKhoan, tenChuTaiKhoan, ngayTao,
                    soTienGui, ngayGui, laiSuat, kyHan);
        } else {
            String soThe = parts[4];
            double soTien = Double.parseDouble(parts[5]);
            return new TaiKhoanThanhToan(id, maTaiKhoan, tenChuTaiKhoan, ngayTao,
                    soThe, soTien);
        }
    }
}
