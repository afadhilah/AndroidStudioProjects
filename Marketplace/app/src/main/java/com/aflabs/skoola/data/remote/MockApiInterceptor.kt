package com.aflabs.skoola.data.remote

import com.aflabs.skoola.domain.model.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class MockApiInterceptor : Interceptor {
    private val json = Json { ignoreUnknownKeys = true; prettyPrint = true }

    // Mock Database
    private val currentUser = User(
        uid = "user_001",
        name = "Ahmad Fadhilah",
        email = "fadhil@student.its.ac.id",
        profileImage = "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiFsllC-fo6lTa1W70zhlhy4BbVRWVZPKkuq7Nno0KvrBaK_qgChoAhlNGH8vaxVT5lPHQTDb2iPuLMBcbippJw94geypbbnC0nNlpJQs3SdKPOKuvcywnxUnEFe72nGUu_LMtpZILvLy9f9BcOWujqTNqxjj6TSKD6r0O4bC7xyBVsa9Y/s1600/IMG_9342-2.jpg",
        phone = "082190375087",
        school = "ITS Surabaya",
        address = "Perumdos ITS R-06, Surabaya",
        isVerified = true,
        rating = 4.8f,
        totalSales = 47,
        studentId = "5025221195"
    )

    private val categories = listOf(
        Category("cat_buku", "Buku", "Book"),
        Category("cat_tulis", "Alat Tulis", "Edit"),
        Category("cat_elek", "Elektronik", "Laptop"),
        Category("cat_seragam", "Seragam", "Checkroom"),
        Category("cat_les", "Jasa Les", "School"),
        Category("cat_desain", "Desain", "Palette"),
        Category("cat_prog", "Programming", "Code"),
        Category("cat_lain", "Lainnya", "MoreHoriz")
    )

    private val banners = listOf(
        Banner("b_1", "Flash Sale Skoola!", "Diskon s/d 70% untuk alat tulis", "https://images.unsplash.com/photo-1513542789411-b6a5d4f31634?w=800&auto=format&fit=crop&q=80", "0xFF111827"),
        Banner("b_2", "Gratis Ongkir Kampus", "Untuk pembelian min. Rp30.000", "https://images.unsplash.com/photo-1522071820081-009f0129c71c?w=800&auto=format&fit=crop&q=80", "0xFF2563EB"),
        Banner("b_3", "Cari Jasa Belajar?", "Temukan les pemrograman & desain", "https://images.unsplash.com/photo-1516321318423-f06f85e504b3?w=800&auto=format&fit=crop&q=80", "0xFF16A34A")
    )

    private val products = mutableListOf(
        Product("p_1", "MacBook Air M1 2020 Second", "MacBook Air M1 2020 kondisi sangat baik, masih bergaransi sampai akhir tahun. Baterai 95%, tidak ada goresan berarti. Cocok untuk mahasiswa kebutuhan kuliah dan desain. Sudah include charger original.", 8500000L, "https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=500&auto=format&fit=crop&q=80", listOf("https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=500&auto=format&fit=crop&q=80"), "cat_elek", "seller_001", "Rizki Pratama", "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=150&auto=format&fit=crop&q=80", "UGM", 1, 4.9f, 28, "Bekas - Baik", "Yogyakarta", 0, true),
        Product("p_2", "Kalkulator Casio FX-991EX Scientific", "Kalkulator scientific Casio FX-991EX baru, masih dalam dus lengkap dengan buku panduan. Wajib untuk mahasiswa Teknik dan MIPA. Bisa untuk matrix, statistik, dan banyak fungsi lainnya.", 195000L, "https://images.unsplash.com/photo-1587145820266-a5951ee6f620?w=500&auto=format&fit=crop&q=80", listOf("https://images.unsplash.com/photo-1587145820266-a5951ee6f620?w=500&auto=format&fit=crop&q=80"), "cat_tulis", "seller_002", "Siti Nurhaliza", "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=150&auto=format&fit=crop&q=80", "ITB", 5, 4.8f, 45, "Baru", "Bandung", 120, true),
        Product("p_3", "Buku Algoritma & Pemrograman C++", "Buku Algoritma dan Pemrograman C++ karangan Rinaldi Munir edisi terbaru. Kondisi 90% masih bagus, ada beberapa highlight pena di beberapa bagian. Sangat berguna untuk mahasiswa Informatika.", 75000L, "https://images.unsplash.com/photo-1610116306796-6fea9f4fae38?w=500&auto=format&fit=crop&q=80", listOf("https://images.unsplash.com/photo-1610116306796-6fea9f4fae38?w=500&auto=format&fit=crop&q=80"), "cat_buku", "seller_003", "Budi Santoso", "https://images.unsplash.com/photo-1570295999919-56ceb5ecca61?w=150&auto=format&fit=crop&q=80", "UI", 2, 4.5f, 12, "Bekas - Baik", "Depok", 8, false),
        Product("p_4", "Kemeja Flanel Kotak-kotak Unisex", "Kemeja flanel premium kotak-kotak, bahan tebal dan nyaman. Cocok untuk gaya kasual kampus.", 85000L, "https://images.unsplash.com/photo-1598033129183-c4f50c736f10?w=500&auto=format&fit=crop&q=80", listOf("https://images.unsplash.com/photo-1598033129183-c4f50c736f10?w=500&auto=format&fit=crop&q=80"), "cat_seragam", "seller_004", "Dewi Rahayu", "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?w=150&auto=format&fit=crop&q=80", "UNPAD", 15, 4.7f, 33, "Baru", "Bandung", 89, true),
        Product("p_5", "Jasa Les Pemrograman Web (React/Node)", "Jasa pembuatan tugas dan project pemrograman web. React.js, Vue.js, Node.js. Pengerjaan cepat 1-3 hari, revision unlimited, harga bersahabat mahasiswa.", 150000L, "https://images.unsplash.com/photo-1517694712202-14dd9538aa97?w=500&auto=format&fit=crop&q=80", listOf("https://images.unsplash.com/photo-1517694712202-14dd9538aa97?w=500&auto=format&fit=crop&q=80"), "cat_prog", "seller_005", "Faris Ramadhan", "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=150&auto=format&fit=crop&q=80", "ITS", 10, 5.0f, 67, "Jasa", "Surabaya", 234, false),
        Product("p_6", "PS4 Slim 1TB + 2 Controller", "PS4 Slim 1TB, 2 stik original, 5 game terinstall. Kondisi mulus, dijual karena lulus kuliah.", 3200000L, "https://images.unsplash.com/photo-1606144042614-b2417e99c4e3?w=500&auto=format&fit=crop&q=80", listOf("https://images.unsplash.com/photo-1606144042614-b2417e99c4e3?w=500&auto=format&fit=crop&q=80"), "cat_elek", "seller_001", "Rizki Pratama", "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=150&auto=format&fit=crop&q=80", "UGM", 1, 4.8f, 19, "Bekas - Baik", "Yogyakarta", 0, false),
        Product("p_7", "Tas Ransel Laptop 15 inch Waterproof", "Tas ransel kualitas premium, waterproof, muat laptop 15 inch. Banyak kantong terorganisir untuk buku, charger, dan aksesoris.", 245000L, "https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=500&auto=format&fit=crop&q=80", listOf("https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=500&auto=format&fit=crop&q=80"), "cat_tulis", "seller_002", "Siti Nurhaliza", "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=150&auto=format&fit=crop&q=80", "ITB", 20, 4.6f, 88, "Baru", "Bandung", 312, true),
        Product("p_8", "Keyboard Mechanical Gaming RK61 Wireless", "Keyboard mechanical 60% wireless RK61, switch red. RGB backlight. Kondisi sangat baik, pakai 6 bulan.", 480000L, "https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=500&auto=format&fit=crop&q=80", listOf("https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=500&auto=format&fit=crop&q=80"), "cat_elek", "seller_003", "Budi Santoso", "https://images.unsplash.com/photo-1570295999919-56ceb5ecca61?w=150&auto=format&fit=crop&q=80", "UI", 1, 4.7f, 22, "Seperti Baru", "Depok", 0, false),
        Product("p_9", "Jasa Desain Poster/PPT Kelompok", "Jasa desain powerpoint presentasi kelompok atau poster ilmiah. Menarik, modern, layout bersih.", 50000L, "https://images.unsplash.com/photo-1551434678-e076c223a692?w=500&auto=format&fit=crop&q=80", listOf("https://images.unsplash.com/photo-1551434678-e076c223a692?w=500&auto=format&fit=crop&q=80"), "cat_desain", "seller_004", "Dewi Rahayu", "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?w=150&auto=format&fit=crop&q=80", "UNPAD", 5, 4.8f, 93, "Jasa", "Bandung", 180, false)
    )

    private val reviews = mutableListOf(
        Review("rev_1", "p_1", "user_002", "Bagas Fikri", "https://picsum.photos/seed/u2/200/200", 5f, "Barang sesuai deskripsi, cepat sampai, seller ramah!"),
        Review("rev_2", "p_1", "user_003", "Laila Sari", "https://picsum.photos/seed/u3/200/200", 5f, "MacBook kondisi mantap banget, baterai masih kenceng. Worth it!"),
        Review("rev_3", "p_1", "user_004", "Hendra Wijaya", "https://picsum.photos/seed/u4/200/200", 4f, "Oke, sesuai ekspektasi. Packaging aman.")
    )

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val path = request.url.encodedPath
        val method = request.method

        var responseString = ""
        var responseCode = 200

        when {
            // AUTH LOGIN
            path.endsWith("auth/login") && method == "POST" -> {
                responseString = json.encodeToString(currentUser)
            }
            // AUTH REGISTER
            path.endsWith("auth/register") && method == "POST" -> {
                responseString = json.encodeToString(currentUser)
            }
            // AUTH VERIFY OTP
            path.endsWith("auth/verify-otp") && method == "POST" -> {
                responseString = json.encodeToString(currentUser)
            }
            // FORGOT PASSWORD
            path.endsWith("auth/forgot-password") && method == "POST" -> {
                responseString = "{}"
            }
            // BANNERS
            path.endsWith("banners") && method == "GET" -> {
                responseString = json.encodeToString(banners)
            }
            // CATEGORIES
            path.endsWith("categories") && method == "GET" -> {
                responseString = json.encodeToString(categories)
            }
            // PRODUCTS
            path.endsWith("products") && method == "GET" -> {
                responseString = json.encodeToString(products)
            }
            // PRODUCT REVIEWS
            path.contains("products/") && path.endsWith("/reviews") && method == "GET" -> {
                val prodId = path.substringAfter("products/").substringBefore("/reviews")
                val filtered = reviews.filter { it.productId == prodId }
                responseString = json.encodeToString(filtered)
            }
            // ADD REVIEW
            path.contains("products/") && path.endsWith("/reviews") && method == "POST" -> {
                val prodId = path.substringAfter("products/").substringBefore("/reviews")
                val newReview = Review("rev_${System.currentTimeMillis()}", prodId, currentUser.uid, currentUser.name, currentUser.profileImage, 5f, "Review otomatis sangat bagus!")
                reviews.add(newReview)
                responseString = json.encodeToString(newReview)
            }
            // PLACE ORDER
            path.endsWith("orders") && method == "POST" -> {
                val mockOrder = Order(
                    id = "order_${System.currentTimeMillis()}",
                    buyerId = currentUser.uid,
                    buyerName = currentUser.name,
                    sellerId = "seller_001",
                    items = emptyList(),
                    totalPrice = 500000L,
                    shippingFee = 15000L,
                    shippingAddress = currentUser.address,
                    paymentMethod = "QRIS",
                    status = OrderStatus.PENDING
                )
                responseString = json.encodeToString(mockOrder)
            }
            // SELLER STATS
            path.contains("seller/") && path.endsWith("/stats") && method == "GET" -> {
                val stats = SalesStats(12500000L, 12, 18, 450)
                responseString = json.encodeToString(stats)
            }
            else -> {
                responseCode = 404
                responseString = "{\"error\": \"Not Found\"}"
            }
        }

        return Response.Builder()
            .code(responseCode)
            .message("OK")
            .protocol(Protocol.HTTP_2)
            .request(request)
            .header("content-type", "application/json")
            .body(responseString.toResponseBody("application/json".toMediaTypeOrNull()))
            .build()
    }
}
