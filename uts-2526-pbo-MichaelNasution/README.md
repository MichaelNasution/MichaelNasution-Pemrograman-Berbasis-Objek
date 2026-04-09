# M01 Fintech Wallet Simulator

Pada tugas kali ini anda diminta untuk mengembangkan sebuah **simulator
dompet digital (digital wallet)**. Sistem ini memungkinkan pengguna
untuk membuat akun serta melakukan berbagai jenis transaksi keuangan.

Setiap akun memiliki: - nama pemilik - username - saldo

Akun dapat menerima berbagai jenis transaksi seperti: - **deposit** -
**withdraw** - **transfer**

Setiap transaksi memiliki informasi: - transaction id - waktu
transaksi - deskripsi

Untuk mendukung pengembangan sistem yang lebih fleksibel, transaksi
harus direpresentasikan menggunakan **konsep pewarisan (inheritance)**.

Semua kelas model harus ditempatkan pada package:

    fintech.model

Driver class harus ditempatkan pada package:

    fintech.driver

------------------------------------------------------------------------

# Task 01: Manage Multiple Accounts (20 pts)

Driver yang dikembangkan:

    fintech.driver.Driver1

Format masukan:

    create-account#<name>#<username>

Program membaca masukan hingga:

    ---

Output:

    username|name|balance

Saldo awal:

    0.0

### Contoh Input

create-account#Wiro Sableng#wirsab
create-account#Jaka Sembung#jaksem
---

### Output

    wirsab|Wiro Sableng|0.0
    jaksem|Jaka Sembung|0.0

------------------------------------------------------------------------

# Task 02: Support Deposit and Withdraw (25 pts)

Tambahkan kelas abstrak:

    fintech.model.Transaction

Properti minimal:

    id
    username
    amount
    timestamp
    description

Subclass:

    DepositTransaction
    WithdrawTransaction

Driver:

    fintech.driver.Driver2

Format input:

Deposit

    deposit#<username>#<amount>#<timestamp>#<description>

Withdraw

    withdraw#<username>#<amount>#<timestamp>#<description>

Withdraw tidak boleh menyebabkan saldo negatif.

### Contoh Input

create-account#Jaka Sembung#jaksem
deposit#jaksem#100.0#2023/02/14 10:10:10#Salary
withdraw#jaksem#30.0#2023/02/15 11:00:00#Food
---

### Output

    jaksem|Jaka Sembung|70.0

------------------------------------------------------------------------

# Task 03: Transfer Between Accounts (25 pts)

Subclass baru:

    TransferTransaction

Format input:

    transfer#<sender>#<receiver>#<amount>#<timestamp>#<description>

Aturan:

1.  saldo pengirim tidak boleh negatif
2.  saldo penerima bertambah
3.  transaksi tercatat

Driver:

    fintech.driver.Driver3

### Contoh Input

create-account#Naruto Uzumaki#naruto
create-account#Sasuke Uchiha#sasuke
deposit#naruto#100.0#2023/02/14 10:10:10#Mission reward
transfer#naruto#sasuke#40.0#2023/02/15 12:00:00#Team fund
---

### Output

    naruto|Naruto Uzumaki|60.0
    sasuke|Sasuke Uchiha|40.0

------------------------------------------------------------------------

# Task 04: Transaction History and Exception (30 pts)

Driver:

    fintech.driver.Driver4

Perintah tambahan:

    show-account#<username>

Format akun:

    username|name|balance

Format transaksi:

    transaction_id|type|amount|timestamp|description

Transaksi harus diurutkan berdasarkan **timestamp ascending**.

## Custom Exception

Buat class:

    NegativeBalanceException

Exception dibangkitkan ketika withdraw atau transfer menyebabkan saldo
negatif.

Program **tidak boleh berhenti**.

### Contoh Input

    create-account#Wiro Sableng#wirsab
    deposit#wirsab#50.0#2023/02/14 10:10:10#Income
    withdraw#wirsab#20.0#2023/02/15 11:00:00#Food
    withdraw#wirsab#40.0#2023/02/16 09:00:00#Shopping
    show-account#wirsab
    ---

### Output

    wirsab|Wiro Sableng|30.0
    1|deposit|50.0|2023/02/14 10:10:10|Income
    2|withdraw|-20.0|2023/02/15 11:00:00|Food

------------------------------------------------------------------------

# How to Compile and Run

Pastikan posisi berada pada direktori:

    bin

Compile:

    cd bin
    javac ..\\src\\fintech\\driver\\*.java ..\\src\\fintech\\model\\*.java -d .

Run:

    java fintech.driver.Driver4

------------------------------------------------------------------------

# Reporting

Lakukan diskusi dengan pair anda dan rekam pertemuan tersebut.

Durasi minimal **30 menit**.

Pada presentasi:

1.  Jelaskan desain kelas
2.  Jelaskan penggunaan inheritance
3.  Jelaskan polymorphism
4.  Jelaskan mekanisme exception
5.  Demonstrasikan program
6.  Tunjukkan hasil GitHub Classroom

------------------------------------------------------------------------

# Kriteria Video

-   Fullscreen
-   Suara jelas
-   Upload ke YouTube
-   Visibility **Unlisted**
-   Set **Not For Kids**

------------------------------------------------------------------------

# Submission

    src/fintech/model/*.java
    src/fintech/driver/Driver1.java
    src/fintech/driver/Driver2.java
    src/fintech/driver/Driver3.java
    src/fintech/driver/Driver4.java
    changelog.txt
