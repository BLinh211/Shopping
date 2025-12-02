public class DiscoverNewTools {
//    1. Serenity BDD

//    Mục đích: Giúp viết test tự động cho web/app, nhưng vẫn dễ đọc, giống như viết hướng dẫn bằng tiếng Anh.
//    Tích hợp luôn báo cáo đẹp, quản lý test case rõ ràng.

//    Cách dùng:Cài đặt Maven (Java) → tạo project → viết test scenario bằng Cucumber (Given/When/Then) → viết code step definitions để test tự động.
//    Có thể kết hợp Screenplay pattern để tổ chức code gọn, dễ maintain.

//    Ưu điểm:Test dễ đọc, báo cáo đẹp.Hỗ trợ test web (Selenium), API (RestAssured).Có cộng đồng, nhiều ví dụ.

//    Nhược điểm: Chỉ dùng được với Java. Ban đầu setup hơi nhiều bước với Maven.
//
//    Ứng dụng: Khi muốn viết test rõ ràng cho team, test vừa chạy tự động, vừa tạo tài liệu cho stakeholder.
//    Phù hợp dự án lớn cần maintain lâu dài.



//    2. Cucumber
//    Mục đích: Viết test bằng ngôn ngữ gần người (Given/When/Then), để tester và dev hiểu được test cases mà không cần đọc code nhiều.

//    Cách dùng: Viết file .feature → định nghĩa scenario → mapping với code (step definitions).
//    Chạy test sẽ thực hiện các bước tự động.

//    Ưu điểm: Rất dễ đọc, dễ hiểu cho người không biết code. Phù hợp với BDD (Behavior Driven Development).

//    Nhược điểm: Cần viết thêm code để map scenario → hành động thật sự. Dễ dài dòng nếu scenario nhiều.

//    Ứng dụng: Dùng để test web, API, hoặc bất cứ tính năng nào cần stakeholder hiểu được.



//    3.Playwright
//
//    Mục đích: Tự động test web app, mô phỏng người dùng thật (click, điền form, navigate…).
//
//    Cách dùng: Cài Playwright (JS/TS hoặc Python/Java). Viết script test → chạy trên nhiều browser (Chrome, Firefox, Safari).
//    Có thể chạy headless (không hiển thị trình duyệt) hoặc có GUI để debug.
//
//    Ưu điểm: Chạy nhanh, hỗ trợ nhiều browser.
//    Có tính năng auto-wait → giảm lỗi flaky tests.
//    Hỗ trợ mock, network interception, test SPA (React/Vue/Angular…).
//
//    Nhược điểm: Chỉ test web/app frontend. Cần học JS/TS (hoặc ngôn ngữ tương thích).
//
//    Ứng dụng: Test chức năng web app, UI, multi-browser.
//    Tự động hóa kiểm tra web app nhanh, thay Selenium nếu muốn hiện đại.
}
