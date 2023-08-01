# MinIO

![minio](./img/minio.png)

## MinIO Nedir ?

MinIO, yüksek performanslı, açık kaynak kodlu, güçlü, ensek bir nesne depolama sistemidir. Her türlü uygulama için büyük miktarda verinin depolanması için idealdir.

MinIO, Amazon S3 destekli bir depolama hizmeti sağlar. Bu sayede verilerim yüksek ölçeklenebilirlik, dayanıklı ve erişilebilir şekilde saklanıp yönetilmesi sağlanır.

## MinIO Özellikleri

- **Ölçeklenebilirlik:** Büyük miktarda veriyi işleyip depolayabilir.
- **Dayanıklılık:** Veriler birden fazla disk üzerinde dağıtarak veri kaybını önler.
- **Güvenlik:** MinIO sağladığı şifreleme yöntemleri ile verilerin güvenli bir şekilde saklanmasını sağlar.
- **Veri Yedekleme:** MinIO, farklı coğrafi konumlarda veri çoğaltma özelliği ile yedekleme ve veri kaybı durumda verileri kurtarma amacıyla kullanılabilir.

## MinIO Yapısı

- Nesne depolama kavramı standart bir dosya sistemine benzer. Ancak dizinler ve dosyalar yerine, Bucket ve object bulunur. Bucket'lar aynı dizinler gibi iç içe yerleştirebilir.
- Nesneler bayt koleksiyonu olarak düşünülebilir. Bu koleksiyon bir resim, pdf yada video olabilir.

## MinIO Kurulumu

- Spring Boot projemize minio bağımlılığını eklemek için

```xml
<dependency>
    <groupId>io.minio</groupId>
    <artifactId>minio</artifactId>
    <version>8.4.3</version>
</dependency>
```

- Docker üzerinden MinIO çalıştırmak için

```docker
docker-compose up
```
