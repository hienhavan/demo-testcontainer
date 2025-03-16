# Demo CRUD Spring Boot với Testcontainers và PostgreSQL

##  Giới thiệu
Dự án minh họa cách sử dụng **Testcontainers** để kiểm thử ứng dụng **Spring Boot** với **PostgreSQL** mà không cần thiết lập cơ sở dữ liệu cục bộ.

##  Công nghệ sử dụng
- **Spring Boot 3.4.3**
- **Java 23**

##  Cài đặt

### 3.2. Cấu hình project
- Thêm các dependencies cần thiết vào `build.gradle`.

###  Cấu hình Testcontainers
- Tạo **BaseTestContainer.java** để quản lý PostgreSQL container.
- Sử dụng **@DynamicPropertySource** để inject thông tin kết nối PostgreSQL vào Spring Boot.
- Đảm bảo container PostgreSQL khởi động trước khi chạy test.

##  Viết Test Controller

###  Cấu trúc API
- `GET /users` - Lấy danh sách người dùng.
- `GET /users/{id}` - Lấy thông tin người dùng theo ID.
- `POST /users` - Tạo người dùng mới.
- `PUT /users/{id}` - Cập nhật người dùng.
- `DELETE /users/{id}` - Xóa người dùng.

###  Viết test
- **UserControllerTest.java** kiểm thử các API trên với **MockMvc**.
- Chạy test CRUD trên database PostgreSQL container.

##  Chạy kiểm thử
```sh
gradlew test
```

## Điểm khác biệt giữa hai cách cấu hình Testcontainers

### Cách 1: Dùng `jdbc:tc:postgresql:15-alpine://`
```properties
spring.datasource.url=jdbc:tc:postgresql:15-alpine:///
spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```
 **Ưu điểm**: Không cần viết `BaseTestContainer.java`, Spring Boot tự động nhận diện container PostgreSQL.
 **Nhược điểm**: Không kiểm soát được lifecycle của Testcontainers.

### Cách 2: Dùng `BaseTestContainer.java`
 **Ưu điểm**:
- Kiểm soát được thời gian start/stop của container.
- Dùng chung cho nhiều test class.
- Tuỳ chỉnh được thông số của PostgreSQL.
 **Nhược điểm**: Cần thêm code setup.

 **Khuyến nghị**: Dùng `BaseTestContainer.java` nếu cần kiểm soát test tốt hơn!


