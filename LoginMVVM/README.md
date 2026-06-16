# Login & Register MVVM App

Aplikasi Android berbasis Kotlin yang mengimplementasikan fitur autentikasi dasar (Pendaftaran Pengguna Baru/Register dan Masuk Akun/Login) menggunakan arsitektur Model-View-ViewModel (MVVM) dengan Repository Pattern. Antarmuka aplikasi dibangun menggunakan Jetpack Compose dengan desain modern (gradasi latar belakang dan kartu semi-transparan), serta penyimpanan lokal menggunakan Room Database.

## Deskripsi Projek

Projek ini dikembangkan untuk mendemonstrasikan praktik terbaik (best practices) dalam pengembangan aplikasi Android modern. Pemisahan tanggung jawab kode dilakukan secara konsisten melalui arsitektur MVVM dan Repository Pattern, memastikan kode mudah diuji (testable) dan dipelihara (maintainable).

## Fitur Utama

- **Pendaftaran Pengguna Baru (Register)**
  - Validasi kolom input (username dan password tidak boleh kosong).
  - Validasi panjang kata sandi (minimal harus 4 karakter).
  - Validasi keunikan username (memeriksa database lokal untuk menghindari duplikasi).
  - Penyimpanan data kredensial secara aman ke database lokal.

- **Masuk Akun (Login)**
  - Validasi kolom input (username dan password tidak boleh kosong).
  - Pencocokan kredensial dengan memeriksa username dan password di database lokal.
  - Tampilan umpan balik interaktif (Toast untuk sukses dan banner kesalahan dinamis untuk kegagalan).

- **Antarmuka Pengguna (UI) Modern**
  - Desain premium dengan gradasi warna latar belakang dan kartu semi-transparan (glassmorphism).
  - Animasi transisi yang halus menggunakan AnimatedVisibility saat menampilkan/menyembunyikan pesan kesalahan.
  - Indikator pemuatan (CircularProgressIndicator) selama proses asinkron berjalan.
  - Manajemen status UI yang sinkron menggunakan StateFlow.

## Teknologi dan Library

- **Kotlin**: Bahasa pemrograman utama untuk logika aplikasi.
- **Jetpack Compose (BOM 2023.10.01)**: Toolkit modern untuk UI deklaratif menggunakan komponen Material Design 3.
- **Room Database (v2.6.1)**: Abstraksi SQLite untuk penyimpanan data lokal yang aman.
- **Kotlin Coroutines & Flow (v1.7.3)**: Pengelolaan tugas asinkron di latar belakang tanpa memblokir UI thread.
- **ViewModel (Compose v2.7.0)**: Pengelola status UI yang konsisten terhadap siklus hidup aplikasi.
- **Kotlin Symbol Processing (KSP)**: Pemrosesan anotasi performa tinggi untuk Room Compiler.

## Arsitektur Aplikasi

Aplikasi ini menggunakan pola arsitektur MVVM yang terbagi menjadi:

1. **Model (Data Layer)**
   - `User.kt`: Kelas data (Entity) pendefinisi tabel `users` di database.
   - `UserDao.kt`: Data Access Object untuk interaksi kueri SQLite (Insert, Select).
   - `AppDatabase.kt`: Inisialisasi database Room dengan pola Singleton.
   - `UserRepository.kt`: Repositori sebagai penengah akses data antara ViewModel dan DAO.

2. **ViewModel Layer**
   - `LoginViewModel.kt`: Mengelola status formulir, memproses validasi input, dan memanggil fungsi repositori secara asinkron menggunakan Coroutines. Status UI diekspos melalui `StateFlow<LoginUiState>`.

3. **View Layer (UI Layer)**
   - `MainActivity.kt`: Titik masuk utama aplikasi yang menginisialisasi database dan merender tampilan Compose.
   - `LoginScreen.kt`: Kode UI deklaratif yang mengamati status dari ViewModel untuk merender formulir, tombol, indikator pemuatan, dan pesan kesalahan.

## Struktur Direktori Projek

```text
app/src/main/java/com/example/loginmvvm/
│
├── data/
│   ├── local/
│   │   ├── AppDatabase.kt (Inisialisasi Database Room)
│   │   ├── User.kt (Entitas Data Pengguna)
│   │   └── UserDao.kt (Kueri CRUD Database)
│   │
│   └── repository/
│       └── UserRepository.kt (Penyedia Data ke ViewModel)
│
├── ui/
│   └── login/
│       ├── LoginScreen.kt (Tampilan UI dengan Jetpack Compose)
│       └── LoginViewModel.kt (Pengatur Logika Bisnis & UI State)
│
├── LoginApplication.kt (Inisialisasi Database Level Aplikasi)
└── MainActivity.kt (Titik Masuk Utama & Pembuat ViewModel)
```

## Skema Database

Penyimpanan lokal menggunakan Room Database dengan satu tabel:

### Tabel: `users`

| Nama Kolom | Tipe Data | Keterangan |
| --- | --- | --- |
| `id` | INTEGER (PrimaryKey) | Dibuat otomatis (Auto-increment) |
| `username` | TEXT | Nama pengguna unik untuk proses login |
| `password` | TEXT | Kata sandi untuk autentikasi |

## Cara Menjalankan Projek

Pastikan perangkat Android atau emulator Anda sudah terhubung via ADB sebelum menjalankan perintah berikut.

1. **Membangun dan Menginstal Aplikasi**
   Jalankan perintah berikut pada terminal di root direktori projek:
   ```bash
   .\gradlew.bat installDebug
   ```

2. **Menjalankan Aplikasi di Perangkat**
   Setelah proses instalasi selesai, jalankan perintah berikut untuk meluncurkan aplikasi:
   ```bash
   adb shell am start -n com.example.loginmvvm/com.example.loginmvvm.MainActivity
   ```
