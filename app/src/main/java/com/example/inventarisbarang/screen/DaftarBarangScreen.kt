/*
package com.example.inventarisbarang.screen

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.inventarisbarang.data.BarangRepository
import com.example.inventarisbarang.model.Barang
import java.text.NumberFormat
import java.util.Locale

*/
/**
 * DaftarBarangScreen menampilkan daftar semua barang dalam bentuk card yang bisa di-scroll.
 *
 * Analogi: Seperti etalase toko online — semua barang ditampilkan dalam
 * kotak-kotak (card) yang bisa di-scroll vertikal. Setiap card menampilkan
 * gambar thumbnail, nama, kategori, harga, dan badge stok.
 *
 * Komponen utama:
 * ┌─────────────────────────────────────────────┐
 * │  TopAppBar: "Inventaris Barang" + [🔍]      │ ← Search toggle
 * ├─────────────────────────────────────────────┤
 * │                                             │
 * │  ┌─────┐  Tas Ransel Premium                │
 * │  │ IMG │  Aksesoris                         │ ← Card item
 * │  │     │  Rp245.000  [Stok: 24]        ›    │
 * │  └─────┘                                    │
 * │                                             │
 * │  ┌─────┐  Smartphone X200                   │
 * │  │ IMG │  Elektronik                        │ ← Card item
 * │  │     │  Rp3.500.000  [Stok: 5]       ›    │
 * │  └─────┘                                    │
 * │                                             │
 * │                                    ┌───┐    │
 * │                                    │ + │    │ ← FAB
 * │                                    └───┘    │
 * │  ┌──────────────────────────────────────┐   │
 * │  │  Snackbar: pesan notifikasi          │   │ ← Snackbar
 * │  └──────────────────────────────────────┘   │
 * └─────────────────────────────────────────────┘
 *
 * @param navController Controller navigasi untuk berpindah screen
 *//*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaftarBarangScreen(navController: NavHostController) {
    // Context Android dibutuhkan untuk mengakses SharedPreferences
    val context = LocalContext.current

    // Inisialisasi repository (remember agar tidak dibuat ulang saat recomposition)
    val repository = remember { BarangRepository(context) }

    // === STATES ===
    // mutableStateOf = state yang jika berubah, otomatis me-trigger recomposition

    // Daftar barang yang ditampilkan
    var daftarBarang by remember { mutableStateOf(repository.getSemuaBarang()) }

    // Teks pencarian yang diketik user
    var searchQuery by remember { mutableStateOf("") }

    // Apakah search bar sedang aktif/terbuka
    var isSearchActive by remember { mutableStateOf(false) }

    // SnackbarHostState untuk menampilkan pesan notifikasi di bawah
    val snackbarHostState = remember { SnackbarHostState() }

    // Filter daftar barang berdasarkan query pencarian
    // contains(ignoreCase = true) = pencarian tidak case-sensitive
    val filteredBarang = if (searchQuery.isEmpty()) {
        daftarBarang
    } else {
        daftarBarang.filter {
            it.nama.contains(searchQuery, ignoreCase = true) ||
            it.kategori.contains(searchQuery, ignoreCase = true)
        }
    }

    */
/**
     * LaunchedEffect(navController.currentBackStackEntry) akan dijalankan
     * setiap kali user kembali ke screen ini dari screen lain.
     *
     * Ini memastikan daftar barang selalu fresh setelah:
     * - Menambah barang baru (dari TambahBarangScreen)
     * - Mengedit barang (dari TambahBarangScreen mode edit)
     * - Menghapus barang (dari DetailBarangScreen)
     *//*

    LaunchedEffect(navController.currentBackStackEntry) {
        daftarBarang = repository.getSemuaBarang()
    }

    // === SCAFFOLD ===
    // Scaffold adalah kerangka layout Material3 yang menyediakan
    // slot untuk TopBar, Content, FAB, Snackbar, dan BottomBar
    Scaffold(
        // === TOP APP BAR ===
        topBar = {
            TopAppBar(
                title = {
                    if (isSearchActive) {
                        // Mode pencarian: tampilkan TextField untuk mengetik query
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = {
                                Text(
                                    "Cari nama atau kategori...",
                                    fontSize = 14.sp
                                )
                            },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline
                            )
                        )
                    } else {
                        // Mode normal: tampilkan judul aplikasi
                        Text("Inventaris Barang")
                    }
                },
                actions = {
                    // Tombol toggle search di kanan atas TopAppBar
                    IconButton(onClick = {
                        isSearchActive = !isSearchActive
                        // Reset query saat search ditutup
                        if (!isSearchActive) searchQuery = ""
                    }) {
                        Icon(
                            // Ganti ikon sesuai state: search ↔ close
                            imageVector = if (isSearchActive) Icons.Default.Close
                                          else Icons.Default.Search,
                            contentDescription = if (isSearchActive) "Tutup Pencarian"
                                                 else "Cari Barang"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },

        // === FLOATING ACTION BUTTON ===
        // Tombol melayang di kanan bawah untuk menambah barang baru
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Navigasi ke screen Tambah Barang
                    navController.navigate("tambah")
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Tambah Barang",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },

        // === SNACKBAR HOST ===
        // Tempat di mana Snackbar akan muncul (bagian bawah layar)
        snackbarHost = { SnackbarHost(snackbarHostState) }

    ) { paddingValues ->
        // paddingValues berisi padding dari Scaffold agar konten
        // tidak tertutup TopAppBar di atas

        if (filteredBarang.isEmpty()) {
            // === STATE KOSONG ===
            // Tampilkan pesan ketika tidak ada barang
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (searchQuery.isEmpty()) "Belum ada barang"
                               else "Barang \"$searchQuery\" tidak ditemukan",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (searchQuery.isEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Tap tombol + untuk menambah barang baru",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        } else {
            // === DAFTAR BARANG ===
            // LazyColumn = versi Compose dari RecyclerView
            // HANYA me-render item yang terlihat di layar (sangat efisien memori)
            // Jika ada 1000 barang, hanya 5-10 yang di-render sekaligus
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),       // Padding di tepi list
                verticalArrangement = Arrangement.spacedBy(12.dp)  // Jarak antar card
            ) {
                items(
                    items = filteredBarang,
                    key = { it.id }   // Key unik agar Compose bisa melacak perubahan
                ) { barang ->
                    // Render satu card barang
                    BarangCard(
                        barang = barang,
                        onClick = {
                            // Klik card → navigasi ke Detail Barang
                            navController.navigate("detail/${barang.id}")
                        }
                    )
                }
            }
        }
    }
}

// =====================================================
// KOMPONEN: BarangCard
// =====================================================

*/
/**
 * BarangCard adalah komponen card untuk menampilkan satu item barang di daftar.
 *
 * Analogi: Seperti satu "kartu produk" di Tokopedia/Shopee —
 * menampilkan thumbnail gambar, nama, kategori, harga, dan stok.
 *
 * Layout:
 * ┌──────────────────────────────────────────────┐
 * │ ┌──────┐  Nama Barang                        │
 * │ │      │  Kategori                           │
 * │ │ IMG  │  Rp245.000  [Stok: 24]         ›    │
 * │ └──────┘                                     │
 * └──────────────────────────────────────────────┘
 *
 * @param barang Data barang yang ditampilkan
 * @param onClick Callback saat card diklik (navigasi ke detail)
 *//*

@Composable
fun BarangCard(barang: Barang, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),   // Seluruh card area bisa diklik
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // === GAMBAR / PLACEHOLDER ===
            if (barang.gambarUri.isNotEmpty()) {
                // Ada gambar: load dari URI menggunakan Coil (AsyncImage)
                // Coil bekerja secara asinkron — tidak memblokir UI saat loading
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(Uri.parse(barang.gambarUri))   // Parse String URI ke Uri object
                        .crossfade(true)                      // Efek transisi halus
                        .build(),
                    contentDescription = barang.nama,
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(8.dp)),     // Rounded corners pada gambar
                    contentScale = ContentScale.Crop           // Crop agar gambar pas di kotak
                )
            } else {
                // Tidak ada gambar: tampilkan placeholder dengan inisial nama
                Surface(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = barang.nama.take(1).uppercase(),  // Huruf pertama, kapital
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // === INFORMASI BARANG ===
            Column(modifier = Modifier.weight(1f)) {
                // Nama barang (max 1 baris, overflow = "...")
                Text(
                    text = barang.nama,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                // Kategori barang
                Text(
                    text = barang.kategori,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Harga + Badge stok
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Harga diformat ke Rupiah Indonesia
                    Text(
                        text = formatRupiah(barang.harga),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary
                    )

                    // Badge stok — warna merah jika stok ≤ 5 (rendah)
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = if (barang.stok <= 5)
                            MaterialTheme.colorScheme.errorContainer   // Merah muda
                        else
                            MaterialTheme.colorScheme.secondaryContainer // Ungu muda
                    ) {
                        Text(
                            text = "Stok: ${barang.stok}",
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            color = if (barang.stok <= 5)
                                MaterialTheme.colorScheme.onErrorContainer
                            else
                                MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }

            // Panah kanan sebagai affordance (indikator "bisa diklik")
            Text(
                text = "›",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// =====================================================
// HELPER FUNCTION
// =====================================================

*/
/**
 * Memformat angka Double ke format mata uang Rupiah Indonesia.
 *
 * Contoh:
 * - 245000.0   → "Rp245.000"
 * - 3500000.0  → "Rp3.500.000"
 * - 0.0        → "Rp0"
 *
 * NumberFormat.getCurrencyInstance(Locale("id", "ID")) menggunakan
 * aturan format mata uang Indonesia:
 * - Simbol: "Rp"
 * - Pemisah ribuan: titik (.)
 * - Pemisah desimal: koma (,)
 *
 * @param amount Jumlah uang dalam Double
 * @return String format Rupiah tanpa desimal
 *//*

fun formatRupiah(amount: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    format.maximumFractionDigits = 0  // Tanpa angka di belakang koma
    return format.format(amount)
}

// =====================================================
// PREVIEW
// =====================================================

@Preview(showBackground = true, name = "Daftar Barang - Light")
@Composable
fun PreviewDaftarBarangScreenLight() {
    MaterialTheme {
        DaftarBarangScreen(navController = rememberNavController())
    }
}
*/
package com.example.inventarisbarang.screen

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.inventarisbarang.data.BarangRepository
import com.example.inventarisbarang.model.Barang
import java.text.NumberFormat
import java.util.Locale
import kotlinx.coroutines.launch

/**
 * DaftarBarangScreen menampilkan daftar semua barang dalam bentuk card yang bisa di-scroll.
 *
 * Analogi: Seperti etalase toko online — semua barang ditampilkan dalam
 * kotak-kotak (card) yang bisa di-scroll vertikal. Setiap card menampilkan
 * gambar thumbnail, nama, kategori, harga, dan badge stok.
 *
 * Komponen utama:
 * ┌─────────────────────────────────────────────┐
 * │  TopAppBar: "Inventaris Barang" + [🔍]      │ ← Search toggle
 * ├─────────────────────────────────────────────┤
 * │                                             │
 * │  ┌─────┐  Tas Ransel Premium                │
 * │  │ IMG │  Aksesoris                         │ ← Card item
 * │  │     │  Rp245.000  [Stok: 24]        ›    │
 * │  └─────┘                                    │
 * │                                             │
 * │  ┌─────┐  Smartphone X200                   │
 * │  │ IMG │  Elektronik                        │ ← Card item
 * │  │     │  Rp3.500.000  [Stok: 5]       ›    │
 * │  └─────┘                                    │
 * │                                             │
 * │                                    ┌───┐    │
 * │                                    │ + │    │ ← FAB
 * │                                    └───┘    │
 * │  ┌──────────────────────────────────────┐   │
 * │  │  Snackbar: pesan notifikasi          │   │ ← Snackbar
 * │  └──────────────────────────────────────┘   │
 * └─────────────────────────────────────────────┘
 *
 * @param navController Controller navigasi untuk berpindah screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaftarBarangScreen(navController: NavHostController) {
    // Context Android dibutuhkan untuk mengakses SharedPreferences
    val context = LocalContext.current

    // Inisialisasi repository (remember agar tidak dibuat ulang saat recomposition)
    val repository = remember { BarangRepository(context) }

    // === STATES ===
    // mutableStateOf = state yang jika berubah, otomatis me-trigger recomposition

    // Daftar barang yang ditampilkan
    var daftarBarang by remember { mutableStateOf(repository.getSemuaBarang()) }

    // Teks pencarian yang diketik user
    var searchQuery by remember { mutableStateOf("") }

    // Apakah search bar sedang aktif/terbuka
    var isSearchActive by remember { mutableStateOf(false) }

    // SnackbarHostState untuk menampilkan pesan notifikasi di bawah
    val snackbarHostState = remember { SnackbarHostState() }

    // State untuk tab yang aktif di BottomNavigationBar
    // 0 = Beranda, 1 = Kategori, 2 = Profil
    var selectedTab by remember { mutableIntStateOf(0) }

    // CoroutineScope untuk menampilkan Snackbar
    val scope = rememberCoroutineScope()

    // Filter daftar barang berdasarkan query pencarian
    // contains(ignoreCase = true) = pencarian tidak case-sensitive
    val filteredBarang = if (searchQuery.isEmpty()) {
        daftarBarang
    } else {
        daftarBarang.filter {
            it.nama.contains(searchQuery, ignoreCase = true) ||
                    it.kategori.contains(searchQuery, ignoreCase = true)
        }
    }

    /**
     * LaunchedEffect(navController.currentBackStackEntry) akan dijalankan
     * setiap kali user kembali ke screen ini dari screen lain.
     *
     * Ini memastikan daftar barang selalu fresh setelah:
     * - Menambah barang baru (dari TambahBarangScreen)
     * - Mengedit barang (dari TambahBarangScreen mode edit)
     * - Menghapus barang (dari DetailBarangScreen)
     */
    LaunchedEffect(navController.currentBackStackEntry) {
        daftarBarang = repository.getSemuaBarang()
    }

    // === SCAFFOLD ===
    // Scaffold adalah kerangka layout Material3 yang menyediakan
    // slot untuk TopBar, Content, FAB, Snackbar, dan BottomBar
    Scaffold(
        // === TOP APP BAR ===
        topBar = {
            TopAppBar(
                title = {
                    if (isSearchActive) {
                        // Mode pencarian: tampilkan TextField untuk mengetik query
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = {
                                Text(
                                    "Cari nama atau kategori...",
                                    fontSize = 14.sp
                                )
                            },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline
                            )
                        )
                    } else {
                        // Mode normal: tampilkan judul aplikasi
                        Text("Inventaris Barang")
                    }
                },
                actions = {
                    // Tombol toggle search di kanan atas TopAppBar
                    IconButton(onClick = {
                        isSearchActive = !isSearchActive
                        // Reset query saat search ditutup
                        if (!isSearchActive) searchQuery = ""
                    }) {
                        Icon(
                            // Ganti ikon sesuai state: search ↔ close
                            imageVector = if (isSearchActive) Icons.Default.Close
                            else Icons.Default.Search,
                            contentDescription = if (isSearchActive) "Tutup Pencarian"
                            else "Cari Barang"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },

        // === FLOATING ACTION BUTTON ===
        // Tombol melayang di kanan bawah untuk menambah barang baru
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Navigasi ke screen Tambah Barang
                    navController.navigate("tambah")
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Tambah Barang",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },

        // === BOTTOM NAVIGATION BAR ===
        // Bar navigasi di bagian bawah layar dengan 3 menu utama
        // Analogi: Seperti menu tab di bawah app Instagram/Tokopedia —
        // user bisa berpindah antar halaman utama dengan satu tap.
        //
        // NavigationBar = komponen Material3 untuk bottom navigation
        // NavigationBarItem = satu item/tab di dalam NavigationBar
        bottomBar = {
            NavigationBar {
                // --- Tab 1: Beranda ---
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = {
                        Icon(
                            // Ikon berubah antara filled (aktif) dan outlined (non-aktif)
                            imageVector = if (selectedTab == 0) Icons.Filled.Home
                            else Icons.Outlined.Home,
                            contentDescription = "Beranda"
                        )
                    },
                    label = { Text("Beranda") }
                )

                // --- Tab 2: Kategori ---
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = {
                        selectedTab = 1
                        // Tampilkan Snackbar informasi (fitur belum tersedia)
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Fitur Kategori akan segera hadir",
                                duration = SnackbarDuration.Short
                            )
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Kategori"
                        )
                    },
                    label = { Text("Kategori") }
                )

                // --- Tab 3: Profil ---
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = {
                        selectedTab = 2
                        // Tampilkan Snackbar informasi (fitur belum tersedia)
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Fitur Profil akan segera hadir",
                                duration = SnackbarDuration.Short
                            )
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == 2) Icons.Filled.Person
                            else Icons.Outlined.Person,
                            contentDescription = "Profil"
                        )
                    },
                    label = { Text("Profil") }
                )
            }
        },

        // === SNACKBAR HOST ===
        // Tempat di mana Snackbar akan muncul (di atas BottomBar)
        snackbarHost = { SnackbarHost(snackbarHostState) }

    ) { paddingValues ->
        // paddingValues berisi padding dari Scaffold agar konten
        // tidak tertutup TopAppBar di atas

        if (filteredBarang.isEmpty()) {
            // === STATE KOSONG ===
            // Tampilkan pesan ketika tidak ada barang
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (searchQuery.isEmpty()) "Belum ada barang"
                        else "Barang \"$searchQuery\" tidak ditemukan",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (searchQuery.isEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Tap tombol + untuk menambah barang baru",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        } else {
            // === DAFTAR BARANG ===
            // LazyColumn = versi Compose dari RecyclerView
            // HANYA me-render item yang terlihat di layar (sangat efisien memori)
            // Jika ada 1000 barang, hanya 5-10 yang di-render sekaligus
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),       // Padding di tepi list
                verticalArrangement = Arrangement.spacedBy(12.dp)  // Jarak antar card
            ) {
                items(
                    items = filteredBarang,
                    key = { it.id }   // Key unik agar Compose bisa melacak perubahan
                ) { barang ->
                    // Render satu card barang
                    BarangCard(
                        barang = barang,
                        onClick = {
                            // Klik card → navigasi ke Detail Barang
                            navController.navigate("detail/${barang.id}")
                        }
                    )
                }
            }
        }
    }
}

// =====================================================
// KOMPONEN: BarangCard
// =====================================================

/**
 * BarangCard adalah komponen card untuk menampilkan satu item barang di daftar.
 *
 * Analogi: Seperti satu "kartu produk" di Tokopedia/Shopee —
 * menampilkan thumbnail gambar, nama, kategori, harga, dan stok.
 *
 * Layout:
 * ┌──────────────────────────────────────────────┐
 * │ ┌──────┐  Nama Barang                        │
 * │ │      │  Kategori                           │
 * │ │ IMG  │  Rp245.000  [Stok: 24]         ›    │
 * │ └──────┘                                     │
 * └──────────────────────────────────────────────┘
 *
 * @param barang Data barang yang ditampilkan
 * @param onClick Callback saat card diklik (navigasi ke detail)
 */
@Composable
fun BarangCard(barang: Barang, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),   // Seluruh card area bisa diklik
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // === GAMBAR / PLACEHOLDER ===
            if (barang.gambarUri.isNotEmpty()) {
                // Ada gambar: load dari URI menggunakan Coil (AsyncImage)
                // Coil bekerja secara asinkron — tidak memblokir UI saat loading
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(Uri.parse(barang.gambarUri))   // Parse String URI ke Uri object
                        .crossfade(true)                      // Efek transisi halus
                        .build(),
                    contentDescription = barang.nama,
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(8.dp)),     // Rounded corners pada gambar
                    contentScale = ContentScale.Crop           // Crop agar gambar pas di kotak
                )
            } else {
                // Tidak ada gambar: tampilkan placeholder dengan inisial nama
                Surface(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = barang.nama.take(1).uppercase(),  // Huruf pertama, kapital
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // === INFORMASI BARANG ===
            Column(modifier = Modifier.weight(1f)) {
                // Nama barang (max 1 baris, overflow = "...")
                Text(
                    text = barang.nama,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                // Kategori barang
                Text(
                    text = barang.kategori,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Harga + Badge stok
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Harga diformat ke Rupiah Indonesia
                    Text(
                        text = formatRupiah(barang.harga),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary
                    )

                    // Badge stok — warna merah jika stok ≤ 5 (rendah)
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = if (barang.stok <= 5)
                            MaterialTheme.colorScheme.errorContainer   // Merah muda
                        else
                            MaterialTheme.colorScheme.secondaryContainer // Ungu muda
                    ) {
                        Text(
                            text = "Stok: ${barang.stok}",
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            color = if (barang.stok <= 5)
                                MaterialTheme.colorScheme.onErrorContainer
                            else
                                MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }

            // Panah kanan sebagai affordance (indikator "bisa diklik")
            Text(
                text = "›",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// =====================================================
// HELPER FUNCTION
// =====================================================

/**
 * Memformat angka Double ke format mata uang Rupiah Indonesia.
 *
 * Contoh:
 * - 245000.0   → "Rp245.000"
 * - 3500000.0  → "Rp3.500.000"
 * - 0.0        → "Rp0"
 *
 * NumberFormat.getCurrencyInstance(Locale("id", "ID")) menggunakan
 * aturan format mata uang Indonesia:
 * - Simbol: "Rp"
 * - Pemisah ribuan: titik (.)
 * - Pemisah desimal: koma (,)
 *
 * @param amount Jumlah uang dalam Double
 * @return String format Rupiah tanpa desimal
 */
fun formatRupiah(amount: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    format.maximumFractionDigits = 0  // Tanpa angka di belakang koma
    return format.format(amount)
}

// =====================================================
// PREVIEW
// =====================================================

@Preview(showBackground = true, name = "Daftar Barang - Light")
@Composable
fun PreviewDaftarBarangScreenLight() {
    MaterialTheme {
        DaftarBarangScreen(navController = rememberNavController())
    }
}
