# Tên Dự Án

## Giới Thiệu
Dự án này là một ứng dụng web giúp người dùng mua sắm trang sức trực tuyến.

## Cài Đặt

### Yêu Cầu
- Java 11+
- Maven 4.0.0
- MySQL 8+

### Các Bước Cài Đặt
1. Clone repository:
   ```bash
   git clone https://github.com/nguyenbinh09/JewelryShopBE.git
   cd JewelryShopBE
   OR
   Giải nén file .zip đã tải từ repository.
2. Cấu hình cơ sở dữ liệu trong src/main/resources/application.properties:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/yourdatabase
   spring.datasource.username=root
   spring.datasource.password=yourpassword
3. Cài đặt các dependency và build dự án:
   ```bash
   mvn clean install
4. Chạy ứng dụng:
   Run bằng IDE or dùng câu lệnh:
   ```bash
   mvn spring-boot:run
5. Sử dụng đường dẫn:
  [http://localhost:8080/swagger-ui ](http://localhost:8080/swagger-ui) để truy cập giao diện swagger của ứng dụng.
